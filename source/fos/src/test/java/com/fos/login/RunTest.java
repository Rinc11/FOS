package com.fos.login;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RunTest {

    /**
     * Tested das Anmelden für den Benutzer 'testUser'
     */
    @Test
    public void testLogin() throws IOException, NotLoadedExeption {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userName")).thenReturn("testUser");
        when(request.getParameter("pass")).thenReturn("1234");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);
        Login lo = new Login(request, response);
        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(session).setAttribute(eq("userLoggedIn"), argument.capture());
        assertEquals("testUser", argument.getValue().getUserName());
        verify(response).sendRedirect("home.jsp");
    }
}
