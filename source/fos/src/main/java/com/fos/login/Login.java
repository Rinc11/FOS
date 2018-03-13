package com.fos.login;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class Login {

    private Connection conn;
    private String formularUserName;

    /**
     * Merkt sich die Login Daten. Bei einem Erfolgreichen Login wird man zur Home Seite weitergeleitet
     *
     * @param request  request von jsp
     * @param response response von jsp
     */
    public Login(HttpServletRequest request, HttpServletResponse response) {
        formularUserName = request.getParameter("userName");
        String pass = request.getParameter("pass");

        if (formularUserName != null || pass != null) {
            try {
                conn = Helper.getConnection();

                Person user = Person.getPerson(formularUserName, conn);
                if (user != null) {
                    if (!user.getLocked() && !user.getDeleted()) {
                        String hash = Helper.getHash(pass);
                        if (hash.equals(user.getPasswordHash())) {
                            if (user.getLoginTry() != 0) {
                                user.setLoginTry(0, conn);
                            }
                            request.getSession().setAttribute("userLoggedIn", user);
                            try {
                                response.sendRedirect("home.jsp");
                            } catch (IOException e) {
                                Helper.addError(request, "Fehler beim weiterleiten zur Startseite", e);
                            }
                        } else {
                            user.setLoginTry(user.getLoginTry() + 1, conn);
                            Helper.addError(request, "Passwort ist falsch<br>Hinweis: " + user.getPasswordHint());
                        }
                    } else {
                        Helper.addError(request, "Benutzer ist gesperrt");
                    }
                } else {
                    Helper.addError(request, "Falscher Benutzername");
                }
            } catch (SQLException e) {
                Helper.addError(request, "Datenbank Fehler", e);
            } catch (NoSuchAlgorithmException e) {
                Helper.addError(request, "Server Fehler", e);
            } catch (NotLoadedExeption e) {
                Helper.addError(request, "Fehler auf der Seite", e);
            }
        }
    }

    /**
     * gibt den Benutzername des Formulares zurück.
     *
     * @return
     */
    public String getFormularUserName() {
        return formularUserName;
    }
}
