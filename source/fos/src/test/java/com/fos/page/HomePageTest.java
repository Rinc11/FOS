package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.page.HomePage;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.fos.tools.TestHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;


public class HomePageTest {
    private HttpServletRequest request;
    private HomePage homePage;
    private Connection conn;
    private Person person;
    private HttpSession session;

    private static final String userLoggedIn =  "suttema2";

    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws Exception {
        TestHelper.loadDatabaseUpdates();

    }

    @Before
    public void createRequest() throws SQLException, NotLoadedException {
        request = mock(HttpServletRequest.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        homePage = new HomePage(request);
        conn = Helper.getConnection();

        person = mock(Person.class);
        when(person.getUserName()).thenReturn(userLoggedIn);
        when(session.getAttribute("userLoggedIn")).thenReturn(person);

    }

    @Test
    public void testGetLockedUserCount() throws SQLException, NotLoadedException {
        Person testUser = Person.getPerson("testUser", conn);
        testUser.setLocked(false, conn);

        int oldLockCount = homePage.getLockedUserCount();
        testUser.setLocked(true, conn);

        int newLockCount = homePage.getLockedUserCount();
        assertEquals(oldLockCount+1, newLockCount);

        testUser.setLocked(false, conn);
    }

    /**
     * testet die Methode getCompanyKMBusiness
     */

    @Test
    public void testGetCompanyKMBusiness() throws SQLException {
        int companyKMBusiness = 0;
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT SUM(\"End_km\"-\"Start_km\")summe FROM \"Trip\" WHERE \"Type\" = 'GESCHÄFTLICH';");
        if (resultSet.next()) {
            companyKMBusiness =  resultSet.getInt("summe");
        }
        Assert.assertEquals(companyKMBusiness, homePage.getCompanyKmBusiness());
    }

    /**
     * testet die Methode getCompanyKmPrivate
     */

    @Test
    public void testGetCompanyKmPrivate() throws SQLException {
        int companyKMPrivate = 0;
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT SUM(\"End_km\"-\"Start_km\")summe FROM \"Trip\" WHERE \"Type\" = 'PRIVAT';");
        if (resultSet.next()) {
          companyKMPrivate =  resultSet.getInt("summe");
        }
        Assert.assertEquals(companyKMPrivate, homePage.getCompanyKmPrivate());
    }
    /**
     * testet die Methode getCompanyKmPrivate
     */

    @Test
    public void testGetPersonalKmBusiness() throws SQLException {
        int companyKMPrivate = 0;
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT SUM(\"End_km\"-\"Start_km\")summe FROM \"Trip\" WHERE \"Type\" = 'GESCHÄFTLICH' AND \"Username\" = 'suttema2';");
        if (resultSet.next()) {
            companyKMPrivate =  resultSet.getInt("summe");
        }
        Assert.assertEquals(companyKMPrivate, homePage.getPersonalKmBusiness());
    }

}
