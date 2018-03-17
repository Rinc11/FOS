package com.fos;

import com.fos.homepage.HomePage;
import com.fos.tools.FosUserPage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"", "/benutzer", "/fahrzeug", "/benutzerFormular"})
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String command = request.getParameter("command");
        if(command != null && command.equals("logout")){
            FosUserPage.logout(request);
        }
        FosUserPage fosUserPage = null;
        String staticPage = "";
        switch (request.getRequestURI()) {
            case "/benutzer":
                fosUserPage = new UserPage(request, response);
                break;
            case "/benutzerFormular":
                fosUserPage = new UserPage(request, response, "/jsp/userForm.jsp");
            case "/fahrzeug":
                staticPage = "jsp/vehicle.jsp";
                break;
            case "/":
                fosUserPage = new HomePage(request, response);
                break;
            default:
                System.out.println(request.getRequestURI());
                break;
        }
        if (fosUserPage != null && fosUserPage.loginValid()) {
            request.setAttribute("actualPage", fosUserPage);
            request.getRequestDispatcher(fosUserPage.getJspPath()).include(request, response);
        } else if (staticPage != "") {
            request.getRequestDispatcher(staticPage).include(request, response);
        } else {
            request.getRequestDispatcher("/jsp/login.jsp").include(request, response);
        }
    }
}
