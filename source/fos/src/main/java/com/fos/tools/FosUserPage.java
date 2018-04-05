package com.fos.tools;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Logik die für alle Seiten des fos Projektes gilt
 */
public abstract class FosUserPage {


    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
        session.invalidate();
    }

    public abstract String getJspPath();

    /**
     * Datenbank Verbindung
     */
    protected Connection conn;
    protected HttpServletRequest request;
    private Boolean needsAdminRight;

    /**
     * Erstellt eine neue Fos Seite welche das Login überprüft
     *
     * @param request         der request vom jsp
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosUserPage(HttpServletRequest request, Boolean needsAdminRight) {
        this.request = request;
        this.needsAdminRight = needsAdminRight;
        try {
            conn = Helper.getConnection();
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }
    }

    public Boolean loginValid() {
        try {
            tryLogIn(request);
            Person user = getUser();
            if (user != null && (!needsAdminRight || user.getUserType() == Person.PersonUserType.ADMIN)) {
                return true;
            }
        } catch (NotLoadedExeption e) {
            addError("Fehler auf der Seite", e);
        }
        return false;
    }

    /**
     * gibt den angemeldeten Benutzer zurück
     *
     * @return angemeldeter Benutzer
     */
    public Person getUser() {
        return (Person) request.getSession().getAttribute("userLoggedIn");
    }

    public void tryLogIn(HttpServletRequest request){
        String formularUserName = request.getParameter("loginUserName");
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
     * fügt einen Fehler hinzu den der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     *
     * @param errorMessage Fehlermeldung
     * @param e            Exception welche auch geloggt wird
     */
    public void addError(String errorMessage, Exception e) {
        Helper.addError(request, errorMessage, e);
    }

    /**
     * fügt einen Fehler hinzu den der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     *
     * @param errorMessage Fehlermeldung
     */
    public void addError(String errorMessage) {
        Helper.addError(request, errorMessage);
    }
}
