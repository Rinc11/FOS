package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Vehicle;
import com.fos.tools.FosUserPage;
import com.fos.tools.Helper;
import com.fos.tools.Logging;
import com.fos.tools.MissingPermissionException;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Vehicleseite
 */
public class VehiclePage extends FosUserPage {
    private static final String REMOVEVEHICLETAG = "removeVehicle:";
    private static final String EDITVEHICLETAG = "editVehicle";
    private String jspFile = "/WEB-INF/jsp/vehicle.jsp";


    /**
     * Logic für die Vehicleseite mit einer anderen jsp Seite
     *
     * @param request  servlet request
     * @param response servlet response
     * @param jspFile  eine andere Seite welche geladen wird.
     */
    public VehiclePage(HttpServletRequest request, HttpServletResponse response, String jspFile) {
        this(request, response);
        this.jspFile = jspFile;
    }


    /**
     * Logic für die Vehicleseite
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public VehiclePage(HttpServletRequest request, HttpServletResponse response) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
            if (command.startsWith(REMOVEVEHICLETAG)) {
                removeItem(Integer.valueOf(command.substring(REMOVEVEHICLETAG.length())));
            } else if (command.equals("addVehicle")) {
                addNewItem(request.getParameter("serialnumber"), request.getParameter("brand")
                        , request.getParameter("type"), Integer.valueOf(request.getParameter("buildYear")), request.getParameter("fuelType"));
            } else if (command.startsWith(EDITVEHICLETAG)) {
                updateItem(Integer.valueOf(request.getParameter("vehicleID")), request.getParameter("serialnumber"), request.getParameter("brand")
                        , request.getParameter("type"), Integer.valueOf(request.getParameter("buildYear")), request.getParameter("fuelType"));
            }
        }
    }

    /**
     * zum Test und zur Demonstration eine Liste von Config Einträgen
     *
     * @return
     */
    public List<Vehicle> getItems() {
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
                Logging.logConnectionNotCloseable();
            }
        }
        return new ArrayList<>();
    }

    public void removeItem(Integer vehicleID) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            Vehicle.removeVehicle(vehicleID, conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }

    }

    @Override
    public String getJspPath() {
        return jspFile;
    }

    public void addNewItem(String serialnumber, String brand, String type, Integer buildYear, String fuelType) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if (getUser().getIsAdmin()) {
                Vehicle.addNewVehicle(serialnumber, brand, type, buildYear, fuelType, conn);
            } else {
                throw new MissingPermissionException();
            }
        } catch (SQLException e) {
            Logging.logServerError(request, e);
        } catch (NotLoadedException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
    }

    public void updateItem(Integer vehicleID, String serialnumber, String brand, String type, Integer buildYear, String fuelType) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            Vehicle.updateVehicle(vehicleID, serialnumber, brand, type, buildYear, fuelType, conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
    }

    public Vehicle getRequestVehicle() {
        Integer vehicleID = Integer.valueOf(request.getParameter("vehicleID"));
        Vehicle result = null;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            result = Vehicle.getVehicle(vehicleID, conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
        return result;
    }
}
