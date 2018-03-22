package com.fos.login;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;
import com.fos.tools.Helper;
import org.apache.catalina.realm.UserDatabaseRealm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

public class Login {

    private Connection conn;
    private String formularUserName;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * Merkt sich die Login Daten. Bei einem Erfolgreichen Login wird zur Home Seite weitergeleitet
     *
     * @param request  request von jsp
     * @param response response von jsp
     */
    public Login(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        formularUserName = request.getParameter("userName");
        String pass = request.getParameter("pass");

        if (formularUserName != null && pass != null) {
            try {
                conn = Helper.getConnection();

                Person user = Person.getPerson(formularUserName, conn);
                if (user != null) {
                    if (!user.getLocked() && !user.getDeleted()) {
                        checkPassword(pass, user);
                    } else {
                        Helper.addError(request, "Benutzer ist gesperrt");
                    }
                } else {
                    Helper.addError(request, "Falscher Benutzername");
                }
            } catch (NotLoadedExeption | SQLException e) {
                Helper.addError(request, "Datenbank Fehler", e);
            }
        }
    }

    /**
     * überprüft das Passwort und leitet weiter wenn es richtig ist.
     *
     * @param pass Passwort von der Bnutzereingabe
     * @param user Benutzer der sich einloggen will.
     */
    private void checkPassword(String pass, Person user) {
        String hash = null;
        try {
            hash = Helper.getHash(pass);

            if (hash.equals(user.getPasswordHash())) {
                if (user.getLoginTry() != 0) {
                    user.setLoginTry(0, conn);
                }
                request.getSession().setAttribute("userLoggedIn", user);
                try {//wird wegfallen
                    response.sendRedirect("home.jsp");
                } catch (IOException e) {
                    Helper.addError(request, "Fehler beim weiterleiten zur Startseite", e);
                }
            } else {
                user.setLoginTry(user.getLoginTry() + 1, conn);
                Helper.addError(request, "Passwort ist falsch<br>Hinweis: " + user.getPasswordHint());
            }
        } catch (SQLException e) {
            Helper.addError(request, "Datenbank Fehler", e);
        } catch (NotLoadedExeption | NoSuchAlgorithmException e) {
            Helper.addError(request, "Server Fehler", e);
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
