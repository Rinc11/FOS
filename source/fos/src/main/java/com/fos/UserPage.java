package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.tools.FosPage;
import com.fos.tools.Helper;
import com.fos.tools.Logging;
import com.fos.tools.MissingPermissionException;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Userseite
 */
public class UserPage extends FosPage {
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
                        , Person.PersonUserType.valueOf(request.getParameter("usertype")));
            } else if (command.startsWith(EDITUSERTAG)) {
                updateItem(request.getParameter("username"), request.getParameter("firstname"), request.getParameter("lastname")
                        , request.getParameter("ahv"), request.getParameter("street"), request.getParameter("place")
                        , request.getParameter("email"), request.getParameter("password"), request.getParameter("passwordHint")
                        , (request.getParameter("locked") != null), Person.PersonUserType.valueOf(request.getParameter("usertype")));
            }
        }
    }

    /**
     * zum Test und zur Demonstration eine Liste von Konfig Eintägen
     *
     * @return
     */
    public List<Person> getItems() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Person.getAllPersons(conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
        return new ArrayList<>();
    }

    public void removeItem(String username) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if(getUser().getIsAdmin()) {
                Person.removePerson(username, conn);
            }else{
                throw new MissingPermissionException();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
    }

    @Override
    public String getJspPath() {
        try {
            if (getUser().getIsAdmin()) {
                return jspFile;
            }
        } catch (NotLoadedException notLoadedExeption) {
            notLoadedExeption.printStackTrace();
        }
        return "/WEB-INF/jsp/editUser.jsp";
    }

    public void addNewItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, Person.PersonUserType userType) {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if (getUser().getIsAdmin()) {
                password = Helper.getHash(password);
                Person.addNewPerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, userType, conn);
            } else {
                throw new MissingPermissionException();
            }
        } catch (NoSuchAlgorithmException e) {
            Logging.logServerError(request, e);
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
    }

    public void updateItem(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, Boolean locked, Person.PersonUserType userType) {
        if (!password.equals("")) {
            try {
                password = Helper.getHash(password);
            } catch (NoSuchAlgorithmException e) {
                Logging.logServerError(request, e);
            }
        }

        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if(getUser().getIsAdmin() || username.equals(getUser().getUserName())){
                Person.updatePerson(username, firstname, lastname, ahv, street, place, email, password, passwordHint, locked, userType, conn, getUser().getIsAdmin());
                if(username.equals(getUser().getUserName())){
                    request.getSession().setAttribute("userLoggedIn", Person.getPerson(getUser().getUserName(), conn));
                }
            }else{
                throw new MissingPermissionException();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (MissingPermissionException e) {
            Logging.logMissingPermission(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
    }

    public Person getRequestPerson() {
        String userName = request.getParameter("username");
        Person result = null;
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            if(getUser().getIsAdmin()){
                if (userName != null) {
                    result = Person.getPerson(userName, conn);
                }
            }else {
                result = getUser();
            }
        } catch (NotLoadedException | SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable();
            }
        }
        return result;
    }
}
