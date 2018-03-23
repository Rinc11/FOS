package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Stellte Funktionen für den Zugriff auf die Fahrzeugdaten aus der Datenbank bereit,
 * Sowie den Typ Vehicle auf welchen programmintern zugegriffen werden kann
 */
public class Vehicle {

    /**
     * gibt den Datensatz eines Fahrzeugs zurück
     *
     * @param vehicleID angabe des Fahrzeugs
     * @param conn      Verbindung zur Datenbank
     * @return Vehicle Datensatz des angefragten Fahrzeuges aus der übergebenen Datenbank
     */
    public static Vehicle getVehicle(int vehicleID, Connection conn) throws SQLException {
        Vehicle result = null;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"VehicleID\", \"Serialnumber\", \"Brand\", \"Type\", \"BuildYear\", \"FuelType\" " +
                        " FROM fos.\"Vehicles\" WHERE \"VehicleID\" = '" + vehicleID + "';"
        );
        if (resultSet.next()) {
            Vehicle vehicle = new Vehicle(resultSet.getInt("VehicleID"));
            vehicle.serialnumber.setValue(resultSet.getString("Serialnumber"));
            vehicle.brand.setValue(resultSet.getString("Brand"));
            vehicle.type.setValue(resultSet.getString("Type"));
            vehicle.buildYear.setValue(resultSet.getInt("BuildYear"));
            vehicle.fuelType.setValue(VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase()));
            result = vehicle;
        }
        return result;
    }

    /**
     * gibt alle Fahrzeuge zurück in einer Liste
     *
     * @param conn die Verbindung zur Datenbank
     * @return eine Liste mit allen Fahrzeugen aus der Datenbank zu der die Verbindung übergeben wurde
     */
    public static List<Vehicle> getAllVehicles(Connection conn) throws SQLException {
        List<Vehicle> result = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"VehicleID\", \"Serialnumber\", \"Brand\", \"Type\", \"BuildYear\", \"FuelType\" " +
                        " FROM fos.\"Vehicles\";"
        );
        while (resultSet.next()) {
            Vehicle vehicle = new Vehicle(resultSet.getInt("VehicleID"));
            vehicle.serialnumber.setValue(resultSet.getString("Serialnumber"));
            vehicle.brand.setValue(resultSet.getString("Brand"));
            vehicle.type.setValue(resultSet.getString("Type"));
            vehicle.buildYear.setValue(resultSet.getInt("BuildYear"));
            vehicle.fuelType.setValue(VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase()));
            result.add(vehicle);
        }
        return result;
    }

    private final DbObject<Integer> vehicleID = new DbObject<>();
    private final DbObject<String> serialnumber = new DbObject<>();
    private final DbObject<String> brand = new DbObject<>();
    private final DbObject<String> type = new DbObject<>();
    private final DbObject<Integer> buildYear = new DbObject<>();
    private final DbObject<VehicleFuelType> fuelType = new DbObject<>();

    private Vehicle(Integer vehicleID) {
        this.vehicleID.setValue(vehicleID);
    }

    /**
     * gibt die VehicleID zurück
     *
     * @return VehicleID
     */
    public Integer getVehicleID() throws NotLoadedExeption {
        return vehicleID.getValue();
    }

    /**
     * gibt die Seriennummer zurück
     *
     * @return Serialnumber
     */
    public String getSerialnumber() throws NotLoadedExeption {
        return serialnumber.getValue();
    }

    /**
     * gibt die Marke zurück
     *
     * @return Brand
     */
    public String getBrand() throws NotLoadedExeption {
        return brand.getValue();
    }

    /**
     * gibt den Fahrzeugtyp zurück
     *
     * @return Type
     */
    public String getType() throws NotLoadedExeption {
        return type.getValue();
    }

    /**
     * gibt das Baujahr zurück
     *
     * @return BuildYear
     */
    public Integer getBuildYear() throws NotLoadedExeption {
        return buildYear.getValue();
    }

    /**
     * der Typ des Treibstoffs (z.B. Benzin, Diesel, Strom, siehe enum VehicleFuelType)
     *
     * @return Typ des Treibstoffs
     */
    public VehicleFuelType getFuelType() throws NotLoadedExeption {
        return fuelType.getValue();
    }


    /**
     * Enum mit den Werten für den Treibstofftyp
     */
    public enum VehicleFuelType {
        BENZIN, DIESEL, STROM, ERDGAS
    }
}
