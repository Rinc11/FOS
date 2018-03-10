package com.fos.tools;

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

    /**
     * Erstellt eine neue Fos Seite welche den Login überprüft
     * @param session die httpSession, welche verwerdet wird
     * @param response die Aktuelle respone, um den Benutzer auf eine andere Seite weiterzuleiten
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosUserPage(HttpSession session, HttpServletResponse response, Boolean needsAdminRight) throws SQLException, IOException {
        conn = Helper.getConnection();
        if (session.getAttribute("userName") == null) {
            response.sendRedirect("login.jsp");
        }
    }
}
