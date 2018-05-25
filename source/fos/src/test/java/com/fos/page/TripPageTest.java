package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Trip;
import com.fos.tools.Helper;
import com.fos.tools.TestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripPageTest {
    private final int testVehicleID = 1;

    private HttpServletRequest request;
    private Connection conn;
    private Person person;
    private HttpSession session;
    private static final String userLoggedIn =  "suttema2";

    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();
    }

    @Before
    public void initConnection() throws SQLException, NotLoadedException {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        conn = Helper.getConnection();

        person = mock(Person.class);
        when(person.getUserName()).thenReturn(userLoggedIn);
        when(session.getAttribute("userLoggedIn")).thenReturn(person);
    }

    /**
     * testet die Methode getLastTripByVehicle, ob sie richtig verhält
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testGetLastTripByVehicle() throws SQLException, NotLoadedException {
        int vehicleID = 2;
        Trip trip = Trip.getLastTripByVehicle(vehicleID, conn);

        TripPage tripPage  = new TripPage(request);
        Trip tripTest = tripPage.getLastTripByVehicle(vehicleID);
        Assert.assertEquals(trip.getVehicleID(), tripTest.getVehicleID());
        Assert.assertEquals(trip.getPlaceStart(), tripTest.getPlaceStart());
        Assert.assertEquals(trip.getPlaceEnd(), tripTest.getPlaceEnd());
        Assert.assertEquals(trip.getStartKM(), tripTest.getStartKM());
        Assert.assertEquals(trip.getEndKM(), tripTest.getEndKM());
        Assert.assertEquals(trip.getStartTime(), tripTest.getStartTime());
        Assert.assertEquals(trip.getEndTime(), tripTest.getEndTime());
        Assert.assertEquals(trip.getType(), tripTest.getType());

    }

    /**
     * testet die Methode startTrip und stopTrip
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testStartTripAndStopTrip() throws SQLException, NotLoadedException {
        Integer vehicleID = 2;
        String placeStart = "Zürich";
        String placeEnd = "Winterthur";
        Integer startKM = 2000;
        Integer endKM = 2300;
        Trip.TripType type = Trip.TripType.GESCHÄFTLICH;

        when(request.getParameter("command")).thenReturn("startTrip");
        when(request.getSession().getAttribute("vehicle")).thenReturn(vehicleID.toString());
        when(request.getParameter("placeStart")).thenReturn(placeStart);
        when(request.getParameter("startKM")).thenReturn(startKM.toString());
        when(request.getParameter("type")).thenReturn(type.toString());

        new TripPage(request);


        Trip trip = Trip.getOpenTripByUsername(userLoggedIn, conn);

        Assert.assertEquals(vehicleID, trip.getVehicleID());
        Assert.assertEquals(placeStart, trip.getPlaceStart());
        Assert.assertEquals(null, trip.getPlaceEnd());
        Assert.assertEquals(startKM, trip.getStartKM());
        Assert.assertEquals((Integer) 0, trip.getEndKM());
        Assert.assertEquals(type, trip.getType());
        Assert.assertEquals(type, trip.getType());

        when(request.getParameter("command")).thenReturn("stopTrip");
        when(request.getParameter("place")).thenReturn(placeEnd);
        when(request.getParameter("kmEnd")).thenReturn(endKM.toString());

        new TripPage(request);

        Trip tripStop = Trip.getLastTripByVehicle(vehicleID, conn);

        Assert.assertEquals(endKM, tripStop.getEndKM());
        Assert.assertEquals(placeEnd, tripStop.getPlaceEnd());

    }




}
