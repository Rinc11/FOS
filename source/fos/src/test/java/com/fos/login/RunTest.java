package com.fos.login;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RunTest {

    /**
     * einen test für einen Testfall
     */
    @Test
    @Deprecated
    public void runTest() throws IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Login lo = new Login(request, response);
        assertTrue(lo.aTest().equals("test"));
    }

    /**
     * Tested das Anmelden für den Benutzer 'mayerret'
     */
    @Test
    public void testLogin() throws IOException, NotLoadedExeption {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userName")).thenReturn("mayerret");
        when(request.getParameter("pass")).thenReturn("1234");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);


        Login lo = new Login(request, response);
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(session).setAttribute(eq ("userLoggedIn"), argument.capture());
        assertEquals("mayerret", argument.getValue().getUserName());
        verify(response).sendRedirect("home.jsp");
    }
}
