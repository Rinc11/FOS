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
    private static final String REMOVEUSERTAG = "removeUser:";

    /**
     * Logic für die Userseite
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public UserPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, false);
        String command = request.getParameter("command");
        if(command != null){
            if(command.startsWith(REMOVEUSERTAG)){
                removeItem(command.substring(REMOVEUSERTAG.length()));
            }
        }
    }

    /**
     * zum Test und zur Demonstration eine Liste von Konfig Eintägen
     *
     * @return
     */
    public List<Person> getItems() {
        try {
            return Person.getAllPersons(conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }
        return new ArrayList<>();
    }

    public void removeItem(String username){
        try {
            Person.removePerson(username, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }

    }

    @Override
    public String getJspPath() {
        return "/jsp/benutzer.jsp";
    }
}
