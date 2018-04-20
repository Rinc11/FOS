package com.fos;

import com.fos.UserPage;
import com.fos.VehiclePage;
import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Vehicle;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testet die VehiclePage-Klasse
 */
public class VehiclePageTest {
    private final int testVehicleID = 1;

    private Connection conn;
    private HttpServletRequest request;
    private HttpSession session;

    @Before
    public void initConnection() throws SQLException {
        conn = com.fos.tools.Helper.getConnection();

        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    /**
     * testet die Methode updateItem als Admin-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testUpdateItemAsAdmin() throws SQLException, NotLoadedException {
        Integer vehicleID = 2;
        String serialnumber = "136c8b4";
        String brand = "Honda";
        String type = "Civic";
        Integer buildYear = 2001;
        Vehicle.VehicleFuelType fuelType = Vehicle.VehicleFuelType.BENZIN;

        loggedInUserIsAdmin();

        when(request.getParameter("command")).thenReturn("editVehicle:" + vehicleID);
        when(request.getParameter("vehicleID")).thenReturn(vehicleID.toString());
        when(request.getParameter("serialnumber")).thenReturn(serialnumber);
        when(request.getParameter("brand")).thenReturn(brand);
        when(request.getParameter("type")).thenReturn(type);
        when(request.getParameter("buildYear")).thenReturn(buildYear.toString());
        when(request.getParameter("fuelType")).thenReturn(fuelType.toString());

        new VehiclePage(request);

        Vehicle vehicle = Vehicle.getVehicle(vehicleID, conn);
        assertEquals(vehicleID, vehicle.getVehicleID());
        assertEquals(serialnumber, vehicle.getSerialnumber());
        assertEquals(brand, vehicle.getBrand());
        assertEquals(type, vehicle.getType());
        assertEquals(buildYear, vehicle.getBuildYear());
        assertTrue(Vehicle.VehicleFuelType.BENZIN == vehicle.getFuelType());
        assertEquals(true, vehicle.isActive());

        //abgeänderter Test-Datensatz wieder zurücksetzen
        Vehicle.updateVehicle(2, "136c8b4", "Honda","Civic",2010, Vehicle.VehicleFuelType.BENZIN, conn);
    }

    /**
     * testet die Methode removeItem als Admin-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testRemoveItemAsAdmin() throws SQLException, NotLoadedException {
        Vehicle vehicle = Vehicle.getVehicle(testVehicleID, conn);
        Assert.assertEquals(true, vehicle.isActive());

        loggedInUserIsAdmin();

        when(request.getParameter("command")).thenReturn("removeVehicle:" + testVehicleID);

        new VehiclePage(request);

        vehicle = Vehicle.getVehicle(testVehicleID, conn);
        assertEquals(false, vehicle.isActive());

        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE \"Vehicles\" SET \"Active_YN\" = TRUE WHERE \"VehicleID\" = ?");
        preparedStatement.setInt(1, testVehicleID);
        preparedStatement.execute();
    }

    /**
     * testet die Methode removeItem als Mitarbeiter-User
     *
     * @throws SQLException
     * @throws NotLoadedException
     */
    @Test
    public void testRemoveItemAsEmployee() throws SQLException, NotLoadedException {
        Vehicle vehicle = Vehicle.getVehicle(testVehicleID, conn);
        Assert.assertEquals(true, vehicle.isActive());

        loggedInUserIsEmployee();

        when(request.getParameter("command")).thenReturn("removeVehicle:" + testVehicleID);

        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);

        new VehiclePage(request);

        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("fehlende Rechte", errorListArgument.getValue().get(0));

        vehicle = Vehicle.getVehicle(testVehicleID, conn);
        assertEquals(true, vehicle.isActive());
    }

    private void loggedInUserIsAdmin() throws NotLoadedException {
        Person loggedInPerson = mock(Person.class);
        when(loggedInPerson.getIsAdmin()).thenReturn(true);

        when(session.getAttribute("userLoggedIn")).thenReturn(loggedInPerson);
    }


    private void loggedInUserIsEmployee() throws NotLoadedException {
        Person loggedInPerson = mock(Person.class);
        when(loggedInPerson.getIsAdmin()).thenReturn(false);

        when(session.getAttribute("userLoggedIn")).thenReturn(loggedInPerson);
    }
}
