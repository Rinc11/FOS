package com.fos.database;

import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.fos.tools.TestHelper;

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


    /**
     * tested das testVehicle ob alle Werte so sind wie in der Datenbank.
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testIfTestTripExists() throws SQLException, NotLoadedException {
        Connection conn = Helper.getConnection();
        Trip trip = Trip.getTrip(defaultTestTripID, conn);
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.of(2018, Month.MARCH, 8, 13, 4, 3, 614000000));
        Assert.assertEquals(defaultTestTripID, trip.getTripID());
        Assert.assertEquals((Integer) 1,  trip.getVehicleID());
        Assert.assertEquals(timestamp,  trip.getStartTime());
    }


}