import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.FosUserPage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

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

    /**
     * tested ob man zur Anmeldeseite weitergeleitet wird, wenn man nicht angemeldet ist.
     */
    @Test
    public void testNotLoggedInUser() throws IOException {
        createMock();

        TestFosPage testFosPage = new TestFosPage(request, response, false);
        verify(response).sendRedirect(eq("login.jsp"));
    }

    /**
     * Tested ob ein angemeldeter Mitarbeiter auf der Seite bleiben kann.
     */
    @Test
    public void testEmployeeLoggedIn() throws IOException, NotLoadedExeption {
        createMock();
        createSessionPersonMock(false);

        TestFosPage testFosPage = new TestFosPage(request, response, false);
        verify(response, never()).sendRedirect(eq("login.jsp"));
    }


    /**
     * Tested ob ein angemeldeter Admin auf einer normalen Seite bleiben kann.
     */
    @Test
    public void testAdminLoggedInOnNormalPage() throws IOException, NotLoadedExeption {
        createMock();
        createSessionPersonMock(true);

        TestFosPage testFosPage = new TestFosPage(request, response, false);
        verify(response, never()).sendRedirect(eq("login.jsp"));
    }


    /**
     * Tested ob ein angemeldeter Mitarbeiter welcher eine Admin Seite aufrufen will
     * zur Startseite weitergeleitet wird.
     */
    @Test
    public void testEmployeeLoggedInOnAdminPage() throws IOException, NotLoadedExeption {
        createMock();
        createSessionPersonMock(false);

        TestFosPage testFosPage = new TestFosPage(request, response, true);
        verify(response).sendRedirect(eq("login.jsp"));
    }

    /**
     * Tested ob ein angemeldeter Admin auf einer admin Seite bleiben kann.
     */
    @Test
    public void testAdminLoggedInOnAdminPage() throws IOException, NotLoadedExeption {
        createMock();
        createSessionPersonMock(true);

        TestFosPage testFosPage = new TestFosPage(request, response, true);
        verify(response, never()).sendRedirect(eq("login.jsp"));
    }

    /**
     * Testet ob getUser den erwarteten Benutzer zurückgibt.
     */
    @Test
    public void testGetUser() throws NotLoadedExeption {
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
    private void createSessionPersonMock(Boolean isAdmin) throws NotLoadedExeption {
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
