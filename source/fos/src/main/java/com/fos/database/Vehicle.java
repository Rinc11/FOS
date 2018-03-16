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
            result = new Vehicle(
                    resultSet.getInt("VehicleID"),
                    resultSet.getString("Serialnumber"),
                    resultSet.getString("Brand"),
                    resultSet.getString("Type"),
                    resultSet.getInt("BuildYear"),
                    VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase()));
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
            result.add(
                    new Vehicle(
                            resultSet.getInt("VehicleID"),
                            resultSet.getString("Serialnumber"),
                            resultSet.getString("Brand"),
                            resultSet.getString("Type"),
                            resultSet.getInt("BuildYear"),
                            VehicleFuelType.valueOf(resultSet.getString("FuelType").toUpperCase())));
        }
        return result;
    }

    private DbObject<Integer> vehicleID = new DbObject<>();
    private DbObject<String> serialnumber = new DbObject<>();
    private DbObject<String> brand = new DbObject<>();
    private DbObject<String> type = new DbObject<>();
    private DbObject<Integer> buildYear = new DbObject<>();
    private DbObject<VehicleFuelType> fuelType = new DbObject<>();

    private Vehicle(Integer vehicleID, String serialnumber, String brand, String type,
                    Integer buildYear, VehicleFuelType fuelType) {
        this.vehicleID.setValue(vehicleID);
        this.serialnumber.setValue(serialnumber);
        this.brand.setValue(brand);
        this.type.setValue(type);
        this.buildYear.setValue(buildYear);
        this.fuelType.setValue(fuelType);
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
