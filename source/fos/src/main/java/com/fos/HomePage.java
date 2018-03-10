package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosUserPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Startseite
 */
public class HomePage extends FosUserPage {

    /**
     * Logic für die Startseite
     * @param request servlet request
     * @param response servlet response
     */
    public HomePage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, false);
    }

    /**
     * zum Test und zur Demonstration eine Liste von Konfig Eintägen
     * @return
     */
    @Deprecated
    public List<Config> getItems() {
        try {
            return Config.getAllConfig(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            addError("Datenbank Fehler",e);
        }
        return new ArrayList<>();
    }
}
