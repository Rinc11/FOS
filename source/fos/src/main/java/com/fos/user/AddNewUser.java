package com.fos.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Logout Servlet; logt den aktuellen Benutzer aus.
 */
@WebServlet(name = "/addUser", value = "/addUser")
public class AddNewUser extends HttpServlet {
    /**
     * Empfängt die Benutzer löschen Anfrage.
     *
     * @param request  Aktueller request wird benötigt um auf die session zuzugreifen
     * @param response die Antwort. Leitet auf die UserPage weiter
     */


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserPage userpage = new UserPage(request, response);
        userpage.addNewItem(request.getParameter("username"), request.getParameter("firstname"), request.getParameter("lastname")
        , request.getParameter("ahv"), request.getParameter("street"), request.getParameter("place")
        , request.getParameter("email"), request.getParameter("password"), request.getParameter("passwordHint")
        , request.getParameter("usertype"));
        response.sendRedirect("user.jsp");
    }

}
