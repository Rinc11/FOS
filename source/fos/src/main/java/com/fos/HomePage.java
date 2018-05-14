package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Trip;
import com.fos.database.Vehicle;
import com.fos.tools.FosPage;
import com.fos.tools.Helper;
import com.fos.tools.Logging;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Logik für die Startseite
 */
public class HomePage extends FosPage {

    private static final String ADDSTARTTRIP = "startTrip";
    private static final String ADDSTOPTRIP = "stopTrip";

    /**
     * Logic für die Startseite
     *
     * @param request servlet request
     */
    public HomePage(HttpServletRequest request) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
            try {
                if (command.startsWith(ADDSTARTTRIP)) {
                    startTrip(Integer.valueOf(request.getParameter("tripVehicle")), new Date(), request.getParameter("placeStart"), Integer.valueOf(request.getParameter("startKM")), Trip.TripType.valueOf(request.getParameter("type")), getUser().getUserName());
                } else if (command.equals(ADDSTOPTRIP)) {
                    stopTrip(request.getParameter("place"), Integer.valueOf(request.getParameter("kmEnd")));
                }
            } catch (NotLoadedException e) {
                Logging.logDatabaseException(request, e);
            }
        }
    }

    public Trip getOpenTrip() {
        Connection conn = null;
        Trip trip = null;
        try {
            conn = Helper.getConnection();
            trip = Trip.getOpenTripByUsername(getUser().getUserName(), conn);
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return trip;
    }


    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public Boolean getHasOpenTrip() {

        if (getOpenTrip() != null) {
            return true;
        }

        return false;
    }

    public Trip getLastTripByVehicle(int id){

        Connection conn = null;
        Trip trip = null;
        try {
            conn = Helper.getConnection();
            trip = Trip.getLastTripByVehicle(id, conn);

        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return trip;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getPersonalKmBusiness() {
        int result = 0;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            List<Trip> filteredTrips = Trip.getFilteredTrips(conn, null, getUser().getUserName(), null, null, Trip.TripType.GESCHÄFTLICH);
            result = StatisticPage.getFilteredKm(filteredTrips, request);
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getPersonalKmPrivate() {
        int result = 0;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            List<Trip> filteredTrips = Trip.getFilteredTrips(conn, null, getUser().getUserName(), null, null, Trip.TripType.PRIVAT);
            result = StatisticPage.getFilteredKm(filteredTrips, request);
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getCompanyKmBusiness() {
        int result = 0;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            List<Trip> filteredTrips = Trip.getFilteredTrips(conn, null, null, null, null, Trip.TripType.GESCHÄFTLICH);
            result = StatisticPage.getFilteredKm(filteredTrips, request);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getCompanyKmPrivate() {
        int result = 0;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            List<Trip> filteredTrips = Trip.getFilteredTrips(conn, null, null, null, null, Trip.TripType.PRIVAT);
            result = StatisticPage.getFilteredKm(filteredTrips, request);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }

    public Integer getLockedUserCount() {
        Connection conn = null;

        try {
            conn = Helper.getConnection();
            return (int) Person.getAllPersons(conn).stream().filter(f -> {
                try {
                    return f.getLocked();
                } catch (NotLoadedException notLoadedExeption) {
                    Logging.logServerError(request, notLoadedExeption);
                }
                return false;
            }).count();
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesToChoose() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Vehicle.getAllVehicles(conn);
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

    public void startTrip(int vehicleID, Date startTime, String placeStart, int startKM, Trip.TripType type, String username) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            Trip.startNewTrip(vehicleID, startTime, placeStart, startKM, type, username, conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
    }

    public void stopTrip(String placeEnd, int kmEnd) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            Trip openTrip = Trip.getOpenTripByUsername(getUser().getUserName(), conn);
            Trip.updateTrip(openTrip.getTripID(), openTrip.getVehicleID(), openTrip.getStartTime(), new Date(), openTrip.getPlaceStart(), placeEnd, openTrip.getStartKM(), kmEnd, openTrip.getType(), openTrip.getUsername(), conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (NotLoadedException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
    }

    @Override
    public String getJspPath() {
        return "/WEB-INF/jsp/home.jsp";
    }
}
