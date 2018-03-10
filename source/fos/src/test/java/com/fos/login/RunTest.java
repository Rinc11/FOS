package com.fos.login;

import javafx.beans.binding.When;
import org.apache.catalina.connector.Request;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RunTest {

    @Test
    public void runTest() throws IOException, SQLException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Login lo = new Login(request, response);
        assertTrue(lo.aTest() == "test");
    }

    @Test
    public void testLogin() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("userName")).thenReturn("reto");
        when(request.getParameter("pass")).thenReturn("bla");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        HttpServletResponse response = mock(HttpServletResponse.class);

        Login lo = new Login(request, response);
        verify(session).setAttribute("userName", "reto");
        verify(response).sendRedirect("home.jsp");
    }
}
