package com.fos;

import com.fos.database.NotLoadedExeption;
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
    private static final String REMOVEUSERTAG = "removeUser:";
    private static final String EDITUSERTAG = "editUser";
    private String jspFile = "/WEB-INF/jsp/user.jsp";


    /**
     * Logic für die Userseite mit einer anderen jsp Seite
     *
     * @param request  servlet request
     * @param response servlet response
     * @param jspFile  eine andere Seite welche geladen wird.
     */
    public UserPage(HttpServletRequest request, HttpServletResponse response, String jspFile) {
        this(request, response);
        this.jspFile = jspFile;
    }


    /**
     * Logic für die Userseite
     *
     * @param request  servlet request
     * @param response servlet response
     */
    public UserPage(HttpServletRequest request, HttpServletResponse response) {
        super(request, false);
        String command = request.getParameter("command");
        if (command != null) {
            if (command.startsWith(REMOVEUSERTAG)) {
                removeItem(command.substring(REMOVEUSERTAG.length()));
            } else if (command.equals("addUser")) {
                addNewItem(request.getParameter("username"), request.getParameter("firstname"), request.getParameter("lastname")
                        , request.getParameter("ahv"), request.getParameter("street"), request.getParameter("place")
                        , request.getParameter("email"), request.getParameter("password"), request.getParameter("passwordHint")
                        , request.getParameter("usertype"));
            } else if (command.startsWith(EDITUSERTAG)) {
                updateItem(request.getParameter("username"), request.getParameter("firstname"), request.getParameter("lastname")
                        , request.getParameter("ahv"), request.getParameter("street"), request.getParameter("place")
                        , request.getParameter("email"), request.getParameter("password"), request.getParameter("passwordHint")
                        , (request.getParameter("locked") != null), request.getParameter("usertype"));
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

    public void removeItem(String username) {
        try {
            Person.removePerson(username, conn);
        } catch (SQLException e) {
            addError("Datenbank Fehler", e);
        }

    }

    @Override
    public String getJspPath() {
        try {
            if (getUser().getIsAdmin()) {
                return jspFile;
            }
        } catch (NotLoadedExeption notLoadedExeption) {
            notLoadedExeption.printStackTrace();
        }
        return "/WEB-INF/jsp/editUser.jsp";
    }

    public void addNewItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, String userType) {
        try {
            if (getUser().getIsAdmin()) {
                password = Helper.getHash(password);
                Person.addNewPerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, userType, conn);
            } else {
                addError("fehlende Rechte");
            }
        } catch (NoSuchAlgorithmException e) {
            addError("Server Fehler", e);
        } catch (NotLoadedExeption | SQLException e) {
            addError("Datenbank Fehler", e);
        }
    }

    public void updateItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, Boolean locked, String userType) {
        if (!password.equals("")) {
            try {
                password = Helper.getHash(password);
            } catch (NoSuchAlgorithmException e) {
                addError("Server Fehler", e);
            }
        }
        try {
            if(getUser().getIsAdmin() || username.equals(getUser().getUserName())){
                Person.updatePerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, locked, userType, conn, getUser().getIsAdmin());
                if(username.equals(getUser().getUserName())){
                    request.getSession().setAttribute("userLoggedIn", Person.getPerson(getUser().getUserName(), conn));
                }
            }else{
                addError("fehlende Rechte");
            }
        } catch (NotLoadedExeption | SQLException e) {
            addError("Datenbank Fehler", e);
        }
    }

    public Person getRequestPerson() {
        String userName = request.getParameter("username");
        Person result = null;
        try {
            if(getUser().getIsAdmin()){
                if (userName != null) {
                    result = Person.getPerson(userName, conn);
                }
            }else {
                result = getUser();
            }
        } catch (NotLoadedExeption | SQLException e) {
            addError("Datenbank Fehler", e);
        }
        return result;
    }
}
