package com.fos.database;

import com.fos.tools.Helper;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Stellt Methoden für den Zugriff auf die Fahrtendaten aus der Datenbank bereit,
 * Sowie den Fahrtentyp auf den programmintern zugegriffen werden kann
 */
public class Trip implements Serializable {

    /**
     * gibt den Datensatz einer Fahrt zurück
     *
     * @param tripID Angabe der Fahrt
     * @param conn      Verbindung zur Datenbank
     * @return Trip Datensatz der angefragten Fahrt aus der übergebenen Datenbank
     */
    public static Trip getTrip(int tripID, Connection conn) throws SQLException {
        Trip result = null;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"TripID\", \"VehicleID\", \"StartTime\", \"EndTime\", \"PlaceStart\", \"PlaceEnd\", \"Start_km\", \"End_km\", \"Type\", \"Username\" " +
                " FROM \"Trip\" WHERE \"TripID\" = ?;");
        preparedStatement.setInt(1, tripID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Trip trip = new Trip(resultSet.getInt("TripID"));
            trip.vehicleID.setValue(resultSet.getInt("VehicleID"));
            trip.startTime.setValue(resultSet.getTimestamp("StartTime"));
            trip.endTime.setValue(resultSet.getTimestamp("EndTime"));
            trip.placeStart.setValue(resultSet.getString("PlaceStart"));
            trip.placeEnd.setValue(resultSet.getString("PlaceEnd"));
            trip.startKM.setValue(resultSet.getInt("Start_km"));
            trip.endKM.setValue(resultSet.getInt("End_km"));
            trip.type.setValue(TripType.valueOf(resultSet.getString("Type").toUpperCase()));
            trip.username.setValue(resultSet.getString("Username"));
            result = trip;
        }
        return result;
    }

    /**
     * gibt alle Fahrten zurück in einer Liste
     *
     * @param conn die Verbindung zur Datenbank
     * @return eine Liste mit allen Fahrten aus der Datenbank zu der die Verbindung übergeben wurde
     */
    public static List<Trip> getAllTrips(Connection conn) throws SQLException {
        return getFilteredTrips(conn, null, null, null, null, null);
    }

    public static List<Trip> getFilteredTrips(Connection conn,
                                              Integer tripVehicleId,
                                              String tripPersonUserName,
                                              Date dateFrom,
                                              Date dateTo,
                                              TripType tripType) throws SQLException{
        List<Trip> result = new ArrayList<>();
        Statement statement = conn.createStatement();
        StringBuilder sqlCommand = new StringBuilder();
        sqlCommand.append("SELECT \"TripID\", \"VehicleID\", \"Brand\", \"Vehicles\".\"Type\" as \"VehicleType\", ");
        sqlCommand.append("\"StartTime\", \"EndTime\", \"PlaceStart\", \"PlaceEnd\", \"Start_km\", \"End_km\", ");
        sqlCommand.append("\"Trip\".\"Type\" as \"TripType\", \"Username\" ");
        sqlCommand.append("FROM \"Trip\" join \"Vehicles\" using (\"VehicleID\") WHERE 1=1\n");
        if(tripVehicleId != null){
            sqlCommand.append("AND \"VehicleID\" = "+tripVehicleId+"\n");
        }
        if(tripPersonUserName != null){
            sqlCommand.append("AND \"Username\" = '"+tripPersonUserName+"'\n");
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dateFrom != null){
            sqlCommand.append("AND \"StartTime\" >= '"+dateFormat.format(dateFrom)+"'\n");
        }
        if(dateTo != null){
            Calendar c = Calendar.getInstance();
            c.setTime(dateTo);
            c.add(Calendar.DATE, 1);
            sqlCommand.append( "AND \"StartTime\" < '"+dateFormat.format(c.getTime())+"'\n");
        }
        if(tripType != null){
            sqlCommand.append( "AND \"Type\" = '"+tripType.name()+"'\n");
        }
        ResultSet resultSet = statement.executeQuery(sqlCommand.toString());

        while (resultSet.next()) {
            Trip trip = new Trip(resultSet.getInt("TripID"));
            trip.vehicleID.setValue(resultSet.getInt("VehicleID"));
            trip.startTime.setValue(resultSet.getTimestamp("StartTime"));
            trip.endTime.setValue(resultSet.getTimestamp("EndTime"));
            trip.placeStart.setValue(resultSet.getString("PlaceStart"));
            trip.placeEnd.setValue(resultSet.getString("PlaceEnd"));
            trip.startKM.setValue(resultSet.getInt("Start_km"));
            trip.endKM.setValue(resultSet.getInt("End_km"));
            trip.type.setValue(TripType.valueOf(resultSet.getString("TripType").toUpperCase()));
            trip.username.setValue(resultSet.getString("Username"));

            Vehicle tripVehicle = new Vehicle(resultSet.getInt("VehicleID"));
            tripVehicle.brand.setValue(resultSet.getString("Brand"));
            tripVehicle.type.setValue(resultSet.getString("VehicleType"));
            trip.vehicle.setValue(tripVehicle);
            result.add(trip);
        }
        return result;
    }

    public static void startNewTrip(Integer vehicleID, Date startTime, String placeStart, Integer startKM, TripType type, String username, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO \"Trip\" (\"VehicleID\", \"StartTime\",  \"PlaceStart\",  \"Start_km\",  \"Type\", \"Username\") VALUES (?, ?, ?, ?,'" + type + "', ?)");
        preparedStatement.setInt(1, vehicleID);
        preparedStatement.setTimestamp(2, Helper.dateToSqlTimestamp(startTime));
        preparedStatement.setString(3, placeStart);
        preparedStatement.setInt(4, startKM);
        preparedStatement.setString(5, username);
        preparedStatement.execute();
    }

    public static void updateTrip(Integer tripID, Integer vehicleID, Date startTime, Date endTime, String placeStart, String placeEnd, Integer startKM, Integer endKM, TripType type, String username, Connection conn) throws SQLException {

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"Trip\" SET  \"VehicleID\" = ?, \"StartTime\" = ?, \"EndTime\" = ?, \"PlaceStart\" = ?, \"PlaceEnd\" = ?, \"Start_km\" = ?, \"End_km\" = ?,\"Type\" = '" + type + "', \"Username\" = ? WHERE \"TripID\" = ?");

        preparedStatement.setInt(1, vehicleID);
        preparedStatement.setTimestamp(2, Helper.dateToSqlTimestamp(startTime));
        preparedStatement.setTimestamp(3, Helper.dateToSqlTimestamp(endTime));
        preparedStatement.setString(4, placeStart);
        preparedStatement.setString(5, placeEnd);
        preparedStatement.setInt(6, startKM);
        preparedStatement.setInt(7, endKM);
        preparedStatement.setString(8, username);
        preparedStatement.setInt(9, tripID);

        preparedStatement.execute();
    }

    public static Trip getOpenTripByUsername(String username, Connection conn) throws SQLException {
        Trip trip = null;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT \"TripID\", \"VehicleID\", \"StartTime\", \"EndTime\", \"PlaceStart\", \"PlaceEnd\", \"Start_km\", \"End_km\", \"Type\", \"Username\" " +
                " FROM \"Trip\" WHERE \"Username\" = '"+ username +"' AND \"PlaceEnd\" IS NULL");

        if (resultSet.next()) {

            trip = new Trip(resultSet.getInt("TripID"));
            trip.vehicleID.setValue(resultSet.getInt("VehicleID"));
            trip.startTime.setValue(resultSet.getTimestamp("StartTime"));
            trip.endTime.setValue(resultSet.getTimestamp("EndTime"));
            trip.placeStart.setValue(resultSet.getString("PlaceStart"));
            trip.placeEnd.setValue(resultSet.getString("PlaceEnd"));
            trip.startKM.setValue(resultSet.getInt("Start_km"));
            trip.endKM.setValue(resultSet.getInt("End_km"));
            trip.type.setValue(TripType.valueOf(resultSet.getString("Type").toUpperCase()));
            trip.username.setValue(resultSet.getString("Username"));
        }
        return trip;
    }


    private final DbObject<Integer> tripID = new DbObject<>();
    private final DbObject<Integer> vehicleID = new DbObject<>();
    private final DbObject<Date> startTime = new DbObject<>();
    private final DbObject<Date> endTime = new DbObject<>();
    private final DbObject<String> placeStart = new DbObject<>();
    private final DbObject<String> placeEnd = new DbObject<>();
    private final DbObject<Integer> startKM = new DbObject<>();
    private final DbObject<Integer> endKM = new DbObject<>();
    private final DbObject<TripType> type = new DbObject<>();
    private final DbObject<String> username = new DbObject<>();

    private final DbObject<Vehicle> vehicle = new DbObject<>();

    private Trip(Integer vehicleID) {
        this.tripID.setValue(vehicleID);
    }

    public Integer getTripID() throws NotLoadedException {
        return tripID.getValue();
    }

    public Integer getVehicleID() throws NotLoadedException {
        return vehicleID.getValue();
    }

    public Date getStartTime() throws NotLoadedException {
        return startTime.getValue();
    }

    public Date getEndTime() throws NotLoadedException {
        return endTime.getValue();
    }

    public String getPlaceStart() throws NotLoadedException {
        return placeStart.getValue();
    }

    public String getPlaceEnd() throws NotLoadedException {
        return placeEnd.getValue();
    }

    public Integer getStartKM() throws NotLoadedException {
        return startKM.getValue();
    }

    public Integer getEndKM() throws NotLoadedException {
        return endKM.getValue();
    }

    public TripType getType() throws NotLoadedException {
        return type.getValue();
    }

    public String getUsername() throws NotLoadedException {
        return username.getValue();
    }

    public Vehicle getVehicle() throws NotLoadedException {
        return vehicle.getValue();
    }

    /**
     * Enum mit den Werten für den Fahrtentyp
     */
    public enum TripType{
        GESCHÄFTLICH, PRIVAT
    }
}
