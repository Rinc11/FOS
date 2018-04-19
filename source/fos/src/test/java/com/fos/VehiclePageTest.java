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
