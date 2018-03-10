package com.fos.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
     * @param response die Aktuelle respone, um den Benutzer auf eine andere Seite weiterzuleiten
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosUserPage(HttpServletRequest request, HttpServletResponse response, Boolean needsAdminRight) {
        try {
            conn = Helper.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            addError("Datenbank Verbindungsfehler",e);
        }
        if (request.getSession().getAttribute("userName") == null) {
            try {
                response.sendRedirect("login.jsp");
            } catch (IOException e) {
                e.printStackTrace();
                addError("Ladefehler mit einer Datei", e);
            }
        }
        this.request = request;
    }

    public void addError(String errorMessage, Exception e){
        Helper.addError(request, errorMessage, e);
    }

    public void addError(String errorMessage){
        Helper.addError(request, errorMessage);
    }
}
