package com.fos.login;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Tested die Logik der Login Seite
 */
public class LoginTest {


    /**
     * setzt die Loginversuche zurück und entsperrt den testUser.
     */
    @Before
    public void resetTestUser() throws SQLException, NotLoadedExeption {
        Connection conn = Helper.getConnection();
        Person person = Person.getPerson("testUser", conn);
        person.setLoginTry(0, conn);
        person.setLocked(false, conn);
    }

    /**
     * Tested das Anmelden für den Benutzer 'testUser'
     */
    @Test
    public void testLogin() throws IOException, NotLoadedExeption {
        HttpServletRequest request = createRequest("testUser", "1234");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);

        Login login = new Login(request, response);
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(session).setAttribute(eq("userLoggedIn"), argument.capture());
        assertEquals("testUser", argument.getValue().getUserName());
        verify(response).sendRedirect("home.jsp");
    }

    /**
     * Tested das Verhalten wenn der Benutzername falsch ist.
     */
    @Test
    public void testLoginWrongUser() throws IOException, NotLoadedExeption {

        HttpServletRequest request = createRequest("keinUser", "1234");

        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        Login login = new Login(request, response);
        verify(request).setAttribute( eq("errorMessage"), errorListArgument.capture());
        errorListArgument.getValue();
        assertEquals(1, errorListArgument.getValue().size() );
        assertEquals("Falscher Benutzername", errorListArgument.getValue().get(0));
    }

    /**
     * Tested das Verhalten wenn das Passwort mehrmals falsch ist.
     */
    @Test
    public void testLoginWronUser() throws IOException, NotLoadedExeption {
        for(int i = 0; i<=10;i++) {
            HttpServletRequest request = createRequest("testUser","falsch" );
            HttpServletResponse response = mock(HttpServletResponse.class);
            ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
            Login login = new Login(request, response);
            verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
            errorListArgument.getValue();
            assertEquals(1, errorListArgument.getValue().size());
            assertEquals("Passwort ist falsch<br>Hinweis: 1234", errorListArgument.getValue().get(0));
        }
        HttpServletRequest request = createRequest("testUser","falsch" );
        HttpServletResponse response = mock(HttpServletResponse.class);
        ArgumentCaptor<List<String>> errorListArgument = ArgumentCaptor.forClass(List.class);
        Login login = new Login(request, response);
        verify(request).setAttribute(eq("errorMessage"), errorListArgument.capture());
        errorListArgument.getValue();
        assertEquals(1, errorListArgument.getValue().size());
        assertEquals("Benutzer ist gesperrt", errorListArgument.getValue().get(0));
    }

    @Test
    /**
     * tested ob der Logout geht
     */
    public void TestLogout() throws ServletException, IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Logout logout = new Logout();
        logout.doGet(request, response);

        verify(session).removeAttribute("userName");
        verify(session).invalidate();
        verify(response).sendRedirect("login.jsp");
    }


    /**
     * erstellt einen Anmelde request mit Benutzer und Passwort
     * @param userName Benutzer Name vom Anmeldeformular
     * @param password Benutzer Passwort vom Anmeldeformular
     * @return den request welcher das Anmeldeformular erzeugen würde.
     */
    private HttpServletRequest createRequest(String userName, String password){
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userName")).thenReturn(userName);
        when(request.getParameter("pass")).thenReturn(password);
        return request;
    }
}
