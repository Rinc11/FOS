package com.fos.user;

import com.fos.UserPage;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Logout Servlet; logt den aktuellen Benutzer aus.
 */
@WebServlet(name = "/editUser", value = "/editUser")
public class EditUser extends HttpServlet {

    /**
     * Empfängt die Benutzer löschen Anfrage.
     *
     * @param request  Aktueller request wird benötigt um auf die session zuzugreifen
     * @param response die Antwort. Leitet auf die UserPage weiter
     */


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserPage userpage = new UserPage(request, response);
        userpage.editItem(request.getParameter("username"), request.getParameter("firstname"), request.getParameter("lastname")
                , request.getParameter("ahv"), request.getParameter("street"), request.getParameter("place")
                , request.getParameter("email"), request.getParameter("password"), request.getParameter("passwordHint")
                , Boolean.valueOf(request.getParameter("locked")), request.getParameter("usertype"));
        response.sendRedirect("user.jsp");
        System.out.print("bool" + Boolean.valueOf(request.getParameter("locked")) +"ohne"+request.getParameter("locked"));

    }
}
