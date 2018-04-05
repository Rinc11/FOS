package com.fos;

import com.fos.database.Vehicle;
import com.fos.database.Person;
import com.fos.tools.FosUserPage;
import com.fos.tools.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
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
        try {
            return Vehicle.getAllVehicles(conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }
        return new ArrayList<>();
    }

    public void removeItem(Integer vehicleID) {
        try {
            Vehicle.removeVehicle(vehicleID, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }

    }

    @Override
    public String getJspPath() {
        return jspFile;
    }

    public void addNewItem(String serialnumber, String brand, String type, Integer buildYear, String fuelType) {
        try {
            Vehicle.addNewVehicle(serialnumber, brand, type, buildYear, fuelType, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }
    }

    public void updateItem(Integer vehicleID, String serialnumber, String brand, String type, Integer buildYear, String fuelType) {
        try {
            Vehicle.updateVehicle(vehicleID, serialnumber, brand, type, buildYear, fuelType, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }
    }

    public Vehicle getRequestVehicle() {
        Integer vehicleID = Integer.valueOf(request.getParameter("vehicleID"));
        Vehicle result = null;
        if (vehicleID != null) {
            try {
                result = Vehicle.getVehicle(vehicleID, conn);
            } catch (SQLException e) {
                addError("Datenbank Fehler", e);
            }
        }
        return result;
    }
}
