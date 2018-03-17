package com.fos.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Logout Servlet; logt den aktuellen Benutzer aus.
 */
@WebServlet(value = "/logout")
public class Logout extends HttpServlet {

    /**
     * Empfängt die Anfrage für das Ausloggen.
     *
     * @param request  Aktueller request wird benötigt um auf die session zuzugreifen
     * @param response die Antwort des Logouts. Leitet auf die Anmeldemaske weiter
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
        session.invalidate();
        response.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
