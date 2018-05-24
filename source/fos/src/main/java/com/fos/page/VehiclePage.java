package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Vehicle;
import com.fos.tools.Helper;
import com.fos.tools.Logging;
import com.fos.tools.MissingPermissionException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Vehicleseite
 */
public class VehiclePage extends FosPage {
    private static final String REMOVEVEHICLETAG = "removeVehicle:";
    private static final String EDITVEHICLETAG = "editVehicle";
    private static final String ADDVEHICLETAG = "addVehicle";
    private String jspFile = "/WEB-INF/jsp/vehicle.jsp";


    /**
     * Logic für die Vehicleseite mit einer anderen jsp Seite
     *
     * @param request  servlet request
     * @param jspFile  eine andere Seite welche geladen wird.
     */
    public VehiclePage(HttpServletRequest request, String jspFile) {
        this(request);
        this.jspFile = jspFile;
    }


    /**
     * Logic für die Vehicleseite
     *
     * @param request  servlet request
     */
    public VehiclePage(HttpServletRequest request) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
            if (command.startsWith(REMOVEVEHICLETAG)) {
                removeItem(Integer.valueOf(command.substring(REMOVEVEHICLETAG.length())));
            } else if (command.equals(ADDVEHICLETAG)) {
                addNewItem(request.getParameter("serialnumber"), request.getParameter("brand")
                        , request.getParameter("type"), Integer.valueOf(request.getParameter("buildYear")), Vehicle.VehicleFuelType.valueOf(request.getParameter("fuelType")));
            } else if (command.startsWith(EDITVEHICLETAG)) {
                updateItem(Integer.valueOf(request.getParameter("vehicleID")), request.getParameter("serialnumber"), request.getParameter("brand")
                        , request.getParameter("type"), Integer.valueOf(request.getParameter("buildYear")), Vehicle.VehicleFuelType.valueOf(request.getParameter("fuelType")));
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
                Logging.logConnectionNotCloseable(e);
            }
        }
        return new ArrayList<>();
    }

    public void removeItem(Integer vehicleID) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if (getUser().getIsAdmin()) {
                Vehicle.removeVehicle(vehicleID, conn);
            } else {
                throw new MissingPermissionException();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
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
        try {
            if (getUser().getIsAdmin()) {
                return jspFile;
            }
        } catch (NotLoadedException notLoadedExeption) {
            Logging.logDatabaseException(request, notLoadedExeption);
        }
        return "/WEB-INF/jsp/vehicle.jsp";
    }

    public void addNewItem(String serialnumber, String brand, String type, Integer buildYear, Vehicle.VehicleFuelType fuelType) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if (getUser().getIsAdmin()) {
                Vehicle.addNewVehicle(serialnumber, brand, type, buildYear, fuelType, conn);
            } else {
                throw new MissingPermissionException();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
    }

    public void updateItem(Integer vehicleID, String serialnumber, String brand, String type, Integer buildYear, Vehicle.VehicleFuelType fuelType) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if (getUser().getIsAdmin()) {
                Vehicle.updateVehicle(vehicleID, serialnumber, brand, type, buildYear, fuelType, conn);
            } else {
                throw new MissingPermissionException();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
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
                Logging.logConnectionNotCloseable(e);
            }
        }
        return result;
    }
}
