package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Trip;
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

public class StatisticPage extends FosPageExport {
    private String jspFile = "/WEB-INF/jsp/statistic.jsp";
    private List<Trip> filteredTrips = new ArrayList<>();

    /**
     * Erstellt eine neue Statistic Seite
     *
     * @param request der request vom jsp
     */
    public StatisticPage(HttpServletRequest request) {
        super(request, false);
        Connection conn = null;
        if(getUser() != null) {
            try {
                String tripVehicleParameter = request.getParameter("tripVehicle");
                Integer tripVehicleId = null;
                if (tripVehicleParameter != null && !tripVehicleParameter.equals("")) {
                    tripVehicleId = Integer.parseInt(tripVehicleParameter);
                }


                String tripPersonUserName = request.getParameter("tripPerson");
                if (tripPersonUserName != null && tripPersonUserName.equals("")) {
                    tripPersonUserName = null;
                }

                if (!getUser().getIsAdmin()) {
                    tripPersonUserName = getUser().getUserName();
                }


                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateFromParameter = request.getParameter("dateFrom");
                Date dateFrom = null;
                Date dateTo = null;
                try {

                    if (dateFromParameter != null && !dateFromParameter.equals("")) {
                        dateFrom = dateFormat.parse(dateFromParameter);
                    }

                    String dateToParameter = request.getParameter("dateTo");
                    if (dateToParameter != null && !dateToParameter.equals("")) {
                        dateTo = dateFormat.parse(dateToParameter);
                    }
                } catch (ParseException e) {
                    Logging.logErrorVisibleToUser(request, "Datum konnte nicht gelesen werden", e, Level.ERROR);
                }

                String tripTypeParameter = request.getParameter("tripType");
                Trip.TripType tripType = null;
                if (tripTypeParameter != null && !tripTypeParameter.equals("")) {
                    if (tripTypeParameter.equals("g")) {
                        tripType = Trip.TripType.GESCHÄFTLICH;
                    } else if (tripTypeParameter.equals("p")) {
                        tripType = Trip.TripType.PRIVAT;
                    }
                }
                conn = Helper.getConnection();
                filteredTrips = Trip.getFilteredTrips(conn, tripVehicleId, tripPersonUserName, dateFrom, dateTo, tripType);
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
                if(type == null || trip.getType() == type){
                sumOfKm += trip.getEndKM() - trip.getStartKM();}
            } catch (NotLoadedException e) {
                Logging.logDatabaseException(request, e);
            }
        }
        return sumOfKm;
    }

    public static int getFilteredKm(List<Trip> filteredTrips, HttpServletRequest request) {
        return  getFilteredKm(filteredTrips, request, null);
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
                sb.append((trip.getVehicle().getBrand()+" " + trip.getVehicle().getType()).replace(';', ':') + ";");
                sb.append(trip.getPlaceStart().replace(';', ':') + ";");
                sb.append(trip.getPlaceEnd().replace(';', ':') + ";");
                sb.append(trip.getEndKM()- trip.getStartKM() + ";");
                sb.append(trip.getType() + "\n");
            }
        } catch (NotLoadedException e) {
            Logging.logDatabaseException(request,e);
        }
        return sb.toString();
    }
}
