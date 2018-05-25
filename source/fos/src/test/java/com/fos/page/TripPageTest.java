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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TripPageTest {
    private final int testVehicleID = 1;

    private HttpServletRequest request;
    private Connection conn;
    private Person person;
    private HttpSession session;
    private TripPage tripPage;
    private static final String userLoggedIn = "suttema2";

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
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testGetLastTripByVehicle() throws SQLException, NotLoadedException {
        int vehicleID = 2;
        Trip trip = Trip.getLastTripByVehicle(vehicleID, conn);

        TripPage tripPage = new TripPage(request);
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
        Trip.TripType type = Trip.TripType.BUSINESS;

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


    /**
     * testet die Methode updateTrip
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testUpdateTrip() throws SQLException, NotLoadedException {

        Integer tripID = 2;
        Integer vehicleID = 2;
        String placeStart = "Altnau";
        String placeEnd = "Güttingen";
        Integer startKM = 403;
        Integer endKM = 432;
        Trip.TripType type = Trip.TripType.PRIVATE;

        when(request.getParameter("command")).thenReturn("editTrip");
        when(request.getParameter("tripID")).thenReturn(tripID.toString());
        when(request.getParameter("tripVehicle")).thenReturn(vehicleID.toString());
        when(request.getParameter("placeStart")).thenReturn(placeStart);
        when(request.getParameter("placeEnd")).thenReturn(placeEnd);
        when(request.getParameter("startKM")).thenReturn(startKM.toString());
        when(request.getParameter("endKM")).thenReturn(endKM.toString());
        when(request.getParameter("type")).thenReturn(type.toString());

        new TripPage(request);

        Trip trip = Trip.getTrip(tripID, conn);

        Assert.assertEquals(vehicleID, trip.getVehicleID());
        Assert.assertEquals(placeStart, trip.getPlaceStart());
        Assert.assertEquals(startKM, trip.getStartKM());
        Assert.assertEquals(type, trip.getType());
        Assert.assertEquals(type, trip.getType());
        Assert.assertEquals(endKM, trip.getEndKM());
        Assert.assertEquals(placeEnd, trip.getPlaceEnd());
    }

    /**
     * testet die Methode getOpenTrip bei der keine Fahrt offen ist
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testGetOpenTripWithNoOpenTrip() throws SQLException, NotLoadedException {

        tripPage = new TripPage(request);
        Trip trip = tripPage.getOpenTrip();

        Assert.assertEquals(null, trip);
    }


}
