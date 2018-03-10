package com.fos.login;

import com.fos.tools.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Login {

    private Connection conn;
    private String formularUserName;
    public Login(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        conn = Helper.getConnection();
        formularUserName = request.getParameter("userName");
        String pass = request.getParameter("pass");

        if(formularUserName != null || pass != null) {
            if (formularUserName.equals("reto") && pass.equals("bla")) {
                HttpSession session = request.getSession();
                session.setAttribute("userName", formularUserName);
                response.sendRedirect("home.jsp");
            }else{
                request.setAttribute("errorReason", "Falsches Passwort oder Benutzername");
            }
        }
    }

    public String getFormularUserName() {
        return formularUserName;
    }

    /**
     * eine Test Methode wird gelöscht sobald mehr Methoden hier sind um zu testen
     * @return den String "test"
     */
    @Deprecated
    public String aTest(){
        return "test";
    }
}
