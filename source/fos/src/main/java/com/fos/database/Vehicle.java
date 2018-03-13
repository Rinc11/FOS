package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Stellte einen Fahrzeug Datensatz dar
 */
public class Vehicle {

    /**
     * gibt den Datensatz eines Fahrzeugs zurück
     * @param vehicleID angabe des Fahrzeugs
     * @param conn Verbindung zur Datenbank
     * @return einen Datenbank Datensatz
     */
    public static Vehicle getVehicle(int vehicleID, Connection conn) throws SQLException {
        Vehicle result = null;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"VehicleID\", \"Serialnumber\", \"Brand\", \"Type\", \"BuildYear\", \"FuelType\" " +
                        " FROM fos.\"Vehicles\" WHERE \"VehicleID\" = '" + vehicleID + "';"
        );
        if (resultSet.next()) {
            result = new Vehicle(
                    resultSet.getInt("VehicleID"),
                    resultSet.getInt("Serialnumber"),
                    resultSet.getString("Brand"),
                    resultSet.getString("Type"),
                    resultSet.getInt("BuildYear"),
                    VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase()));
        }

        return result;
    }

    public static List<Vehicle> getAllVehicles(Connection conn) throws SQLException {
        List<Vehicle> result = new ArrayList<Vehicle>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"VehicleID\", \"Serialnumber\", \"Brand\", \"Type\", \"BuildYear\", \"FuelType\" " +
                        " FROM fos.\"Vehicles\";"
        );
        while (resultSet.next()){
            result.add(
                    new Vehicle(
                            resultSet.getInt("VehicleID"),
                            resultSet.getInt("Serialnumber"),
                            resultSet.getString("Brand"),
                            resultSet.getString("Type"),
                            resultSet.getInt("BuildYear"),
                            VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase())));
        }
        return result;
    }

    private Integer vehicleID;
    private Integer serialnumber;
    private String brand;
    private String type;
    private Integer buildYear;
    private VehicleFuelType fuelType;

    private Vehicle(Integer vehicleID, Integer serialnumber, String brand, String type,
                    Integer buildYear, VehicleFuelType fuelType) {
        this.vehicleID = vehicleID;
        this.serialnumber = serialnumber;
        this.brand = brand;
        this.type = type;
        this.buildYear = buildYear;
        this.fuelType = fuelType;
    }

    /**
     * gibt die VehicleID zurück
     * @return VehicleID
     */
    public Integer getVehicleID() {
        return vehicleID;
    }

    /**
     * gibt die Seriennummer zurück
     * @return Serialnumber
     */
    public Integer getSerialnumber() {
        return serialnumber;
    }

    /**
     * gibt die Marke zurück
     * @return Brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * gibt den Fahrzeugtyp zurück
     * @return Type
     */
    public String getType() {
        return type;
    }

    /**
     * gibt das Baujahr zurück
     * @return BuildYear
     */
    public Integer getBuildYear() {
        return buildYear;
    }

    /**
     * der Typ des Treibstoffs(z.B. Benzin, Diesel)
     * @return Typ des Treibstoffs
     */
    public VehicleFuelType getFuelType() {
        return fuelType;
    }


    /**
     * Enum mit den Werten für den Treibstofftyp
     */
    public enum VehicleFuelType {
        BENZIN, DIESEL, STROM, ERDGAS
    }
}
