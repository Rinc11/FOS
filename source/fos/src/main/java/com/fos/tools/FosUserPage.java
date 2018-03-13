package com.fos.tools;

import com.fos.database.NotLoadedExeption;
import com.fos.database.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Logik die für alle Seiten des fos Projektes gilt
 */
public abstract class FosUserPage {

    /**
     * Datenbank Verbindung
     */
    protected Connection conn;
    protected HttpServletRequest request;

    /**
     * Erstellt eine neue Fos Seite welche den Login überprüft
     * @param request der request vom jsp
     * @param response die aktuelle response, um den Benutzer auf eine andere Seite weiterzuleiten
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosUserPage(HttpServletRequest request, HttpServletResponse response, Boolean needsAdminRight) {
        this.request = request;
        try {
            conn = Helper.getConnection();

            Person user = getUser();
            if (user == null || (needsAdminRight && user.getUserType() != Person.PersonUserType.ADMIN)) {
                try {
                    response.sendRedirect("login.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                    addError("Ladefehler mit einer Datei", e);
                }
            }
        } catch (SQLException e) {
            addError("Datenbank Verbindungsfehler", e);
        } catch (NotLoadedExeption e) {
            addError("Fehler auf der Seite", e);
        }
    }

    /**
     * gibt den angemeldeten Benutzer zurück
     *
     * @return angemeldeter Benutzer
     */
    public Person getUser() {
        return (Person) request.getSession().getAttribute("userLoggedIn");
    }

    /**
     * fügt einen Fehler hinzu den der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     *
     * @param errorMessage Fehlermeldung
     * @param e Exception welche auch geloggt wird
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
