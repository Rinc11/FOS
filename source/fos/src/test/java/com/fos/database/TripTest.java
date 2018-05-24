package com.fos.database;

import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.fos.tools.TestHelper;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;


/**
 * Testet ob die Klasse Trip sich richtig verhält
 */
public class TripTest {

    private Integer defaultTestTripID = 1;

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     *
     * @throws SQLException
     */
    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();
    }


    @Test
    public void testGetLastTripByVehicle() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();
        Trip trip = Trip.getLastTripByVehicle(1, conn);
        Assert.assertEquals(trip.getPlaceEnd(), "Winterthur");
    }

    /**
     * testet ob die getTrip Methode sich korrekt verhält
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testGetTrip() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();

        int vehicleID = 1;
        Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.of(2018, Month.MARCH, 8, 13, 4, 3, 614000000));
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.of(2018, Month.MARCH, 9, 13, 4, 10, 340000000));
        String placeStart = "Frauenfeld";
        String placeEnd = "Winterthur";
        int startKM = 100;
        int endKM = 130;
        Trip.TripType type = Trip.TripType.PRIVAT;
        String username = "suttema2";

        Trip trip = Trip.getTrip(defaultTestTripID,conn);

        Assert.assertEquals(defaultTestTripID, trip.getTripID());
        Assert.assertEquals((Integer) vehicleID, trip.getVehicleID());
        Assert.assertEquals(startTimestamp,  trip.getStartTime());
        Assert.assertEquals(endTimestamp,  trip.getEndTime());
        Assert.assertEquals(placeStart, trip.getPlaceStart());
        Assert.assertEquals(placeEnd, trip.getPlaceEnd());
        Assert.assertEquals((Integer) startKM, trip.getStartKM());
        Assert.assertEquals((Integer) endKM, trip.getEndKM());
        Assert.assertTrue(type == trip.getType());
        Assert.assertEquals(username, trip.getUsername());
    }

    /**
     * testet ob die startNewTrip-Methode sich richtig verhält
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testStartNewTrip() throws SQLException, NotLoadedException {

        Connection conn = Helper.getConnection();

        int vehicleID = 2;
        Date startTime = new Date();
        String placeStart = "Amriswil";
        int startKM = 5000;
        Trip.TripType type = Trip.TripType.GESCHÄFTLICH;
        String username = "testUser";

        Trip.startNewTrip(vehicleID, startTime, placeStart, startKM, type, username, conn);
        Trip trip = Trip.getLastTripByVehicle(vehicleID, conn);
        Assert.assertEquals((Integer) vehicleID, trip.getVehicleID());
        Assert.assertEquals(startTime, trip.getStartTime());
        Assert.assertEquals(placeStart, trip.getPlaceStart());
        Assert.assertEquals((Integer) startKM, trip.getStartKM());
        Assert.assertEquals(type, trip.getType());
        Assert.assertEquals(username, trip.getUsername());
        Assert.assertEquals((Integer) 0, trip.getEndKM());
        Assert.assertEquals(null, trip.getEndTime());

    }

    /**
     * testet ob die updateTrip-Methode sich richtig verhält
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testUpdateTrip() throws SQLException, NotLoadedException {

        Connection conn = Helper.getConnection();

        int tripID = 1;
        int vehicleID = 3;
        Date startTime = new Date();
        Date endTime = new Date();
        String placeStart = "Amriswil";
        String placeEnd = "Romanshorn";
        int startKM = 5500;
        int endKM = 5510;
        Trip.TripType type = Trip.TripType.GESCHÄFTLICH;
        String username = "testUser";

        Trip tripBefore = Trip.getTrip(1, conn);
        Trip.updateTrip(tripID,vehicleID, startTime, endTime, placeStart, placeEnd, startKM, endKM, type, username, conn);

        Trip tripAfterUpdate = Trip.getTrip(1, conn);
        Assert.assertEquals((Integer) tripID, tripAfterUpdate.getTripID());
        Assert.assertEquals((Integer) vehicleID, tripAfterUpdate.getVehicleID());
        Assert.assertEquals(startTime, tripAfterUpdate.getStartTime());
        Assert.assertEquals(placeStart, tripAfterUpdate.getPlaceStart());
        Assert.assertEquals((Integer) startKM, tripAfterUpdate.getStartKM());
        Assert.assertEquals(type, tripAfterUpdate.getType());
        Assert.assertEquals(username, tripAfterUpdate.getUsername());
        Assert.assertEquals((Integer) endKM, tripAfterUpdate.getEndKM());
        Assert.assertEquals(endTime, tripAfterUpdate.getEndTime());

        Trip.updateTrip(tripBefore.getTripID(), tripBefore.getVehicleID(), tripBefore.getStartTime(), tripBefore.getEndTime(), tripBefore.getPlaceStart(), tripBefore.getPlaceEnd(), tripBefore.getStartKM(), tripBefore.getEndKM(), tripBefore.getType(), tripBefore.getUsername(), conn );

    }



}