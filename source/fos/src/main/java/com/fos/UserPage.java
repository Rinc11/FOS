package com.fos;

import com.fos.database.Person;
import com.fos.tools.FosUserPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Userseite
 */
public class UserPage extends FosUserPage {

    /**
     * Logic für die Userseite
     * @param request servlet request
     * @param response servlet response
     */
    public UserPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, false);
    }

    /**
     * zum Test und zur Demonstration eine Liste von Konfig Eintägen
     * @return
     */
    @Deprecated
    public List<Person> getItems() {
        try {
            return Person.getAllPersons(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            addError("Datenbank Fehler",e);
        }
        return new ArrayList<>();
    }
}
