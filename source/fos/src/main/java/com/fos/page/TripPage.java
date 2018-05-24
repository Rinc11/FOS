package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Trip;
import com.fos.database.Vehicle;
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
    private static final String ADDSTARTTRIP = "startTrip";
    private static final String ADDSTOPTRIP = "stopTrip";
    private static final String SAVEVEHICLE = "saveVehicle";
    private static final String DELETEVEHICLE = "deleteVehicle";


    public TripPage(HttpServletRequest request, String jspFile) {
        this(request);
        this.jspFile = jspFile;
    }

    public TripPage(HttpServletRequest request) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
            try {
                if (command.startsWith(EDITTRIPTAG)) {
                    updateItem(Integer.valueOf(request.getParameter("tripID")), Integer.valueOf(request.getParameter("tripVehicle")), null, null, request.getParameter("placeStart"), request.getParameter("placeEnd")
                            , Integer.valueOf(request.getParameter("startKM")), Integer.valueOf(request.getParameter("endKM")), Trip.TripType.valueOf(request.getParameter("type")), null);
                } else if (command.startsWith(ADDSTARTTRIP)) {
                    startTrip(Integer.valueOf(request.getSession().getAttribute("vehicle").toString()), new Date(), request.getParameter("placeStart"), Integer.valueOf(request.getParameter("startKM")), Trip.TripType.valueOf(request.getParameter("type")), getUser().getUserName());
                } else if (command.equals(ADDSTOPTRIP)) {
                    stopTrip(request.getParameter("place"), Integer.valueOf(request.getParameter("kmEnd")));
                } else if (command.startsWith(SAVEVEHICLE)) {
                    request.getSession().setAttribute("vehicle", request.getParameter("tripVehicle"));
                } else if (command.startsWith(DELETEVEHICLE)) {
                    request.getSession().setAttribute("vehicle", null);
                }
            } catch (NotLoadedException e) {
                Logging.logDatabaseException(request, e);
            }
        }

    }

    /**
     * Zwischen Methode für getHasOpenTrip, liest die letzte Fahrt eines Benuzters
     *
     * @return letzte offene Fahrt
     */
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
     * Teilt mit ob eine offene Fahrt existiert
     *
     * @return gibt einen Boolean zurück ob die angemeldete Person eine offene Fahrt hat
     */
    public Boolean getHasOpenTrip() {

        if (getOpenTrip() != null) {
            return true;
        }

        return false;
    }

    public Trip getLastTripByVehicle(int id) {

        Connection conn = null;
        Trip trip = null;
        try {
            conn = Helper.getConnection();
            trip = Trip.getLastTripByVehicle(id, conn);

        } catch (SQLException e) {
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
            Trip.updateTrip(openTrip.getTripID(), openTrip.getVehicleID(), openTrip.getStartTime(),
                    new Date(), openTrip.getPlaceStart(), placeEnd, openTrip.getStartKM(), kmEnd,
                    openTrip.getType(), openTrip.getUsername(), conn);
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

    public Vehicle getVehicle() {

        Connection conn = null;
        Vehicle vehicle = null;
        try {
            conn = Helper.getConnection();
            vehicle = Vehicle.getVehicle(Integer.valueOf(request.getSession().getAttribute("vehicle").toString()), conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return vehicle;
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

    public void updateItem(Integer tripID, Integer vehicleID, Date startTime, Date endTime, String placeStart,
                           String placeEnd, Integer startKM, Integer endKM, Trip.TripType type, String username) {
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
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
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
