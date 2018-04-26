package com.fos;

import com.fos.database.NotLoadedException;
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

public class TripPage extends FosPage {

    private String jspFile = "/WEB-INF/jsp/trip.jsp";
    private static final String EDITTRIPTAG = "editTrip";

    public TripPage(HttpServletRequest request, String jspFile) {
        this(request);
        this.jspFile = jspFile;
    }

    public TripPage(HttpServletRequest request) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
           if (command.startsWith(EDITTRIPTAG)) {
                updateItem(Integer.valueOf(request.getParameter("tripID")), Integer.valueOf(request.getParameter("tripVehicle")), null, null, request.getParameter("placeStart"), request.getParameter("placeEnd")
                        , Integer.valueOf(request.getParameter("startKM")), Integer.valueOf(request.getParameter("endKM")), Trip.TripType.valueOf(request.getParameter("type")), null);
            }
        }

    }

    public List<Trip> getItems() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Trip.getAllTrips(conn);
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
        try {
            if (getUser().getIsAdmin()) {
                return jspFile;
            }
        } catch (NotLoadedException notLoadedExeption) {
            Logging.logDatabaseException(request, notLoadedExeption);
        }
        return "/WEB-INF/jsp/trip.jsp";
    }

    public void updateItem(Integer tripID, Integer vehicleID, Date startTime, Date endTime, String placeStart, String placeEnd, Integer startKM, Integer endKM, Trip.TripType type, String username) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
                Trip trip = Trip.getTrip(tripID, conn);
                startTime = trip.getStartTime();
                endTime = trip.getEndTime();
                username = trip.getUsername();

                Trip.updateTrip(tripID, vehicleID, startTime, endTime, placeStart, placeEnd, startKM, endKM, type, username, conn);

        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
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

    public Trip getRequestTrip() {
        Integer tripID = Integer.valueOf(request.getParameter("tripID"));
        Trip result = null;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            result = Trip.getTrip(tripID, conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }
}
