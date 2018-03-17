package com.fos.user;

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
@WebServlet(name = "/deleteUser", value = "/deleteUser")
public class DeleteUser extends HttpServlet {

    /**
     * Empfängt die Benutzer löschen Anfrage.
     *
     * @param request  Aktueller request wird benötigt um auf die session zuzugreifen
     * @param response die Antwort. Leitet auf die UserPage weiter
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserPage userpage = new UserPage(request, response);
        userpage.removeItem(request.getParameter("username"));
        response.sendRedirect("user.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
