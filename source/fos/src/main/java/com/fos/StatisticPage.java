package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Trip;
import com.fos.tools.FosPage;
import com.fos.tools.FosPageExport;
import com.fos.tools.Helper;
import com.fos.tools.Logging;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticPage extends FosPage implements FosPageExport {
    private String jspFile = "/WEB-INF/jsp/statistic.jsp";
    List<Trip> filteredTrips = new ArrayList<>();

    /**
     * Erstellt eine neue Statistic Seite
     *
     * @param request der request vom jsp
     */
    public StatisticPage(HttpServletRequest request) {
        super(request, false);
        loadFilteredTrips();
    }

    private void loadFilteredTrips() {
        if (getUser() == null) {
            return;
        }

        Connection conn = null;
        try {
            conn = Helper.getConnection();
            filteredTrips = Trip.getFilteredTrips(conn,
                    getVehicleIdFromRequest(),
                    getTripPersonFromRequest(getUser()),
                    getFromDateFromRequest(),
                    getToDateFromRequest(),
                    getTripTypeFromRequest());
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
    }

    private Integer getVehicleIdFromRequest(){
        String tripVehicleParameter = request.getParameter("tripVehicle");
        if (tripVehicleParameter != null && !tripVehicleParameter.equals("")) {
            return Integer.parseInt(tripVehicleParameter);
        }
        return null;
    }

    private String getTripPersonFromRequest(Person userLoggedIn) throws NotLoadedException {
        String tripPersonUserName = request.getParameter("tripPerson");
        if (tripPersonUserName != null && tripPersonUserName.equals("")) {
            tripPersonUserName = null;
        }

        if (!userLoggedIn.getIsAdmin()) {
            tripPersonUserName = userLoggedIn.getUserName();
        }
        return tripPersonUserName;
    }

    private Date getFromDateFromRequest(){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String dateFromParameter = request.getParameter("dateFrom");
                if (dateFromParameter != null && !dateFromParameter.equals("")) {
                    return dateFormat.parse(dateFromParameter);
                }
            } catch (ParseException e) {
                Logging.logErrorVisibleToUser(request, "Datum konnte nicht gelesen werden", e, Level.ERROR);
            }
        return null;
    }


    private Trip.TripType getTripTypeFromRequest(){
        String tripTypeParameter = request.getParameter("tripType");
        if (tripTypeParameter != null && !tripTypeParameter.equals("")) {
            if (tripTypeParameter.equals("g")) {
                return Trip.TripType.GESCHÄFTLICH;
            } else if (tripTypeParameter.equals("p")) {
                return Trip.TripType.PRIVAT;
            }
        }
        return null;
    }


    private Date getToDateFromRequest(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String dateToParameter = request.getParameter("dateTo");
            if (dateToParameter != null && !dateToParameter.equals("")) {
                return dateFormat.parse(dateToParameter);
            }
        } catch (ParseException e) {
            Logging.logErrorVisibleToUser(request, "Datum konnte nicht gelesen werden", e, Level.ERROR);
        }
        return null;
    }
    @Override
    public String getJspPath() {
        return jspFile;
    }

    public List<Trip> getFilteredTrips() {
        return filteredTrips;
    }

    public int getFilteredListCount() {
        return filteredTrips.size();
    }

    public int getFilteredKm() {
        return getFilteredKm(filteredTrips, request, Trip.TripType.GESCHÄFTLICH) + getFilteredKm(filteredTrips, request, Trip.TripType.PRIVAT);
    }

    public int getFilteredKmPrivat() {
        return getFilteredKm(filteredTrips, request, Trip.TripType.PRIVAT);
    }

    public int getFilteredKmBusiness() {
        return getFilteredKm(filteredTrips, request, Trip.TripType.GESCHÄFTLICH);
    }

    public static int getFilteredKm(List<Trip> filteredTrips, HttpServletRequest request, Trip.TripType type) {
        List<Trip> filterdTripsWithKm = filteredTrips.stream().filter(f -> {
            try {
                return f.getEndKM() != null;
            } catch (NotLoadedException e) {
                Logging.logDatabaseException(request, e);
                return false;
            }
        }).collect(Collectors.toList());
        int sumOfKm = 0;
        for (Trip trip : filterdTripsWithKm) {
            try {
                if (type == null || trip.getType() == type) {
                    sumOfKm += trip.getEndKM() - trip.getStartKM();
                }
            } catch (NotLoadedException e) {
                Logging.logDatabaseException(request, e);
            }
        }
        return sumOfKm;
    }

    public static int getFilteredKm(List<Trip> filteredTrips, HttpServletRequest request) {
        return getFilteredKm(filteredTrips, request, null);
    }


    public List<Person> getPersonToChoose() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Person.getAllPersons(conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getExport() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fahrer;Auto;Fahrt Start;Fahrt Ziel;Kilometer;Fahrt Typ\n");
        try {
            for (Trip trip : filteredTrips) {
                sb.append(trip.getUsername().replace(';', ':') + ";");
                sb.append((trip.getVehicle().getBrand() + " " + trip.getVehicle().getType()).replace(';', ':') + ";");
                sb.append(trip.getPlaceStart().replace(';', ':') + ";");
                sb.append(trip.getPlaceEnd().replace(';', ':') + ";");
                sb.append(trip.getEndKM() - trip.getStartKM() + ";");
                sb.append(trip.getType() + "\n");
            }
        } catch (NotLoadedException e) {
            Logging.logDatabaseException(request, e);
        }
        return sb.toString();
    }
}
