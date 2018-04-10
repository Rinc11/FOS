import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.tools.FosUserPage;
import com.fos.tools.Helper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tested ide FosUserPage
 */
public class FosUserPageTest {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private Connection conn;


    /**
     * updated die Datenbank auf den neusten Stand mit Testdaten
     */
    @BeforeClass
    public static void updateDatabase() throws SQLException {
        tools.Helper.loadDatabaseUpdates();
    }


    /**
     * setzt die Loginversuche zurück und entsperrt den testUser.
     */
    @Before
    public void resetTestUser() throws SQLException, NotLoadedException {
        conn = Helper.getConnection();
        Person person = Person.getPerson("testUser", conn);
        person.setLoginTry(0, conn);
        person.setLocked(false, conn);
    }

    /**
     * Tested ob der Testbenutzer sich anmelden kann und ob diese Person in der
     * Session gespeichert wird.
     */
    @Test
    public void testTryLoginValid() throws NotLoadedException {
        createMock();
        when(request.getParameter("loginUserName")).thenReturn("testUser");
        when(request.getParameter("pass")).thenReturn("1234");
        TestFosPage testFosPage = new TestFosPage(request, response, false);
        testFosPage.tryLogIn(request);
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);

        verify(request.getSession()).setAttribute(eq("userLoggedIn"), argument.capture());
        Assert.assertEquals("testUser", argument.getValue().getUserName());
    }

    /**
     * Tested ob der logintry erhöht wird wenn für den TestUser das falsche Passwort
     * eingegben wird.
     */
    @Test
    public void testTryLoginWrongPasswordMultipleTimes() throws NotLoadedException, SQLException {
        for (int i = 0; i <= 10; i++) {
            createMock();
            when(request.getParameter("loginUserName")).thenReturn("testUser");
            when(request.getParameter("pass")).thenReturn("bla");
            TestFosPage testFosPage = new TestFosPage(request, response, false);
            testFosPage.tryLogIn(request);

            ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
            verify(request.getSession(), never()).setAttribute(eq("userLoggedIn"), ArgumentMatchers.any());
            verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
            assertEquals(1, errorListArgument.getValue().size());
            assertEquals("Passwort ist falsch<br>Hinweis: 1234", errorListArgument.getValue().get(0));
        }

        createMock();
        when(request.getParameter("loginUserName")).thenReturn("testUser");
        when(request.getParameter("pass")).thenReturn("bla");

        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        TestFosPage testFosPage = new TestFosPage(request, response, false);
        testFosPage.tryLogIn(request);
        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        errorListArgument.getValue();
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("Benutzer ist gesperrt", errorListArgument.getValue().get(0));

    }

    /**
     * Tested ob der logintry erhöht wird wenn für den TestUser das falsche Passwort
     * eingegben wird.
     */
    @Test
    public void testTryLoginWrongUserName() throws NotLoadedException, SQLException {
        createMock();
        when(request.getParameter("loginUserName")).thenReturn("nichtVorhanden");
        when(request.getParameter("pass")).thenReturn("bla");
        TestFosPage testFosPage = new TestFosPage(request, response, false);
        testFosPage.tryLogIn(request);

        verify(request.getSession(), never()).setAttribute(eq("userLoggedIn"), ArgumentMatchers.any());
        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("Falscher Benutzername", errorListArgument.getValue().get(0));
    }

    /**
     * tested ob der Logout geht
     */
    @Test
    public void TestLogout() throws ServletException, IOException {
        createMock();

        TestFosPage.logout(request);

        verify(request.getSession()).removeAttribute("userName");
        verify(request.getSession()).invalidate();
    }


    /**
     * Testet ob getUser den erwarteten Benutzer zurückgibt.
     */
    @Test
    public void testGetUser() throws NotLoadedException {
        createMock();
        createSessionPersonMock(false);

        TestFosPage testFosPage = new TestFosPage(request, response, false);
        Person person = testFosPage.getUser();
        Assert.assertEquals("MockUser", person.getUserName());
        Assert.assertEquals(Person.PersonUserType.MITARBEITER, person.getUserType());
    }


    /**
     * erstelle die Mocks ohne eine Person die angemeldet ist.
     */
    private void createMock() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    /**
     * Fügt eine Person zur session hinzu, damit wird eine angemeldete Person simuliert
     *
     * @param isAdmin ob die Perosn Administrator ist
     */
    private void createSessionPersonMock(Boolean isAdmin) throws NotLoadedException {
        Person person = mock(Person.class);
        HttpSession session = request.getSession();
        when(session.getAttribute("userLoggedIn")).thenReturn(person);
        when(person.getUserName()).thenReturn("MockUser");

        if (isAdmin) {
            when(person.getUserType()).thenReturn(Person.PersonUserType.ADMIN);
        } else {
            when(person.getUserType()).thenReturn(Person.PersonUserType.MITARBEITER);
        }
    }


    /**
     * test Klasse welche zum testen von FosUserPage benutzt wird.
     */
    private class TestFosPage extends FosUserPage {
        /**
         * Erstellt eine neue test Fos Seite
         *
         * @param request         mocked request
         * @param response        mocked response
         * @param needsAdminRight ob admin rechte benötigt werden.
         */
        public TestFosPage(HttpServletRequest request, HttpServletResponse response, Boolean needsAdminRight) {
            super(request, needsAdminRight);
        }

        @Override
        public String getJspPath() {
            return "";
        }
    }
}
