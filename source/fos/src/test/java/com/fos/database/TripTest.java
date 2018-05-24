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



}