package com.fos.login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/***
 * Login servlet; empfängt das Anmelde Formular und überprüft den Login
 */
@WebServlet(name = "/Login", value = "/Login")
public class Login extends HttpServlet {

    /**
     * Empfängt den Post Requst des Anmelde Formulares
     * @param request Daten des Anmelde Formulares
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String pass = request.getParameter("pass");

        if(userName.equals("reto") && pass.equals("bla")){
            HttpSession session = request.getSession();
            session.setAttribute("userName", userName);
            response.sendRedirect("startPage.jsp");
        }else{
            response.sendRedirect("login.jsp");
        }
    }

    public String aTest(){
        return "test";
    }
}
