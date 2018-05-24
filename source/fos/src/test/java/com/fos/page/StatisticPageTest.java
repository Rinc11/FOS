package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.page.StatisticPage;
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

public class StatisticPageTest {

    private Connection conn;
    private HttpServletRequest request;
    private HttpSession session;

    private static final String userLoggedIn =  "mayerret";

    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();
    }


    @Before
    public void initConnection() throws SQLException {
        conn = com.fos.tools.Helper.getConnection();

        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testTripsAdmin() throws NotLoadedException {
        setLoggedIn(true);
        StatisticPage statisticPage = new StatisticPage(request);
        Assert.assertTrue(statisticPage.filteredTrips.size() != 0);
        Assert.assertTrue( statisticPage.filteredTrips.stream().anyMatch(s -> {
            try {
                return s.getTripID().equals(1);
            } catch (NotLoadedException e) {
                Assert.fail();
            }
            return false;
        }));
    }

    @Test
    public void testTripsNotAdmin() throws NotLoadedException {
        setLoggedIn(false);
        StatisticPage statisticPage = new StatisticPage(request);
        Assert.assertTrue(statisticPage.filteredTrips.size() != 0);
        Assert.assertTrue( statisticPage.filteredTrips.stream().allMatch(s -> {
            try {
                return s.getUsername().equals(userLoggedIn);
            } catch (NotLoadedException e) {
                Assert.fail();
            }
            return false;
        }));
    }

    @Test
    public void testTripsFiltered() throws NotLoadedException {
        setLoggedIn(true);
        when(request.getParameter("tripVehicle")).thenReturn("1");
        StatisticPage statisticPage = new StatisticPage(request);
        Assert.assertTrue(statisticPage.filteredTrips.size() != 0);
        Assert.assertTrue( statisticPage.filteredTrips.stream().allMatch(s -> {
            try {
                return s.getVehicleID().equals(1);
            } catch (NotLoadedException e) {
                Assert.fail();
            }
            return false;
        }));
    }

    @Test
    public void textExport() throws NotLoadedException {
        setLoggedIn(true);
        StatisticPage statisticPage = new StatisticPage(request);
        String export = statisticPage.getExport();
        Assert.assertTrue(export.startsWith("Fahrer;Auto;Fahrt Start;Fahrt Ziel;Kilometer;Fahrt Typ"));
        Assert.assertTrue(export.contains(userLoggedIn+";"));
    }

    /**
     * setzt den Benutzer der eingeloggt ist.
     * @param isAdmin sezt ob es einen Admin ist
     */
    private void setLoggedIn(Boolean isAdmin) throws NotLoadedException {
        Person person = mock(Person.class);
        when(session.getAttribute("userLoggedIn")).thenReturn(person);
        when(person.getUserName()).thenReturn(userLoggedIn);
        if (isAdmin) {
            when(person.getIsAdmin()).thenReturn(true);
        } else {
            when(person.getIsAdmin()).thenReturn(false);
        }
    }
}
