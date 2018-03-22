package com.fos.user;

import com.fos.database.Person;
import com.fos.tools.FosUserPage;
import com.fos.tools.Helper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Userseite
 */
public class UserPage extends FosUserPage {

    /**
     * Logik für die Userseite
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public UserPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, false);
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

    public void addNewItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, String userType) {

        try {
            password = Helper.getHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            Person.addNewPerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, userType, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }

    }

    public void editItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, Boolean locked, String userType) {
        if (!password.equals("")) {
            try {
                password = Helper.getHash(password);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        try {
            Person.updatePerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, locked, userType, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }



    }
}
