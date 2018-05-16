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
import java.util.List;

/**
 * Logik für die Startseite
 */
public class HomePage extends FosPage {

    private static final String SAVEVEHICLE = "saveVehicle";


    /**
     * Logic für die Startseite
     *
     * @param request servlet request
     */
    public HomePage(HttpServletRequest request) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
                if (command.startsWith(SAVEVEHICLE)) {
                    request.getSession().setAttribute("vehicle", request.getParameter("tripVehicle"));
                }
        }
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

    @Override
    public String getJspPath() {
        return "/WEB-INF/jsp/home.jsp";
    }
}
