package com.fos.page;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Vehicle;
import com.fos.tools.Helper;
import com.fos.tools.Logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik die für alle Seiten des fos Projektes gilt
 */
public abstract class FosPage {


    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("userLoggedIn");
        session.invalidate();
    }

    public abstract String getJspPath();

    /**
     * Datenbank Verbindung
     */
    protected HttpServletRequest request;
    private Boolean needsAdminRight;

    /**
     * Erstellt eine neue Fos Seite welche das Login überprüft
     *
     * @param request         der request vom jsp
     * @param needsAdminRight Angabe, ob Administratorenrechte für diese Seite benötigt werden.
     */
    public FosPage(HttpServletRequest request, Boolean needsAdminRight) {
        this.request = request;
        this.needsAdminRight = needsAdminRight;
    }

    public Boolean loginValid() {
        try {
            String formularUserName = request.getParameter("loginUserName");
            String pass = request.getParameter("pass");
            tryLogIn(formularUserName, pass);
            Person user = getUser();
            if (user != null && (!needsAdminRight || user.getUserType() == Person.PersonUserType.ADMIN)) {
                return true;
            }
        } catch (NotLoadedException e) {
            Logging.logDatabaseException(request, e);
        }
        return false;
    }

    /**
     * gibt den angemeldeten Benutzer zurück
     *
     * @return angemeldeter Benutzer
     */
    public Person getUser() {
        Object userLoggedIn = request.getSession().getAttribute("userLoggedIn");
        if (userLoggedIn == null) {
            return null;
        }
        return (Person) userLoggedIn;
    }

    /**
     * gibt das Fahrzeug zurück, welches in der Session gespeichert ist
     *
     * @return vehicle
     */
    public Vehicle getVehicle() {

        Connection conn = null;
        Vehicle vehicle = null;
        try {
            conn = Helper.getConnection();
            vehicle = Vehicle.getVehicle(Integer.valueOf(request.getSession().getAttribute("vehicle").toString()), conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return vehicle;
    }

    public int getSelectedVehicleID() {
        Object vehicleID = request.getSession().getAttribute("vehicle");
        if (vehicleID == null) {
            return 0;
        }
        return (int) vehicleID;
    }

    /**
     * probiert den Benutzer einzuloggen
     *
     * @param userName Benutzername
     * @param password Passwort
     */
    public void tryLogIn(String userName, String password) {
        //check ob die Anmeldedaten übertragen wuden
        if (userName == null && password == null) {
            return;
        }
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            Person user = Person.getPerson(userName, conn);

            //check ob benutzer in der DB vorhanden
            if (user == null) {
                Logging.messageToUser(request, "Falscher Benutzername");
                return;
            }

            //check ob benutzer ist gesperrt
            if (user.getLocked() || user.getDeleted()) {
                Logging.messageToUser(request, "Benutzer ist gesperrt");
                return;
            }
            String hash = Helper.getHash(password);
            // überprüfe  Passwort mit Hash ob es falsch ist
            if (!hash.equals(user.getPasswordHash())) {
                user.setLoginTry(user.getLoginTry() + 1, conn);
                Logging.messageToUser(request, "Passwort ist falsch<br>Hinweis: " + user.getPasswordHint());
                return;
            }

            //ab hier ist der Login erfolgreich
            //versuchte login zurücksetzen fals nicht 0
            if (user.getLoginTry() != 0) {
                user.setLoginTry(0, conn);
            }
            request.getSession().setAttribute("userLoggedIn", user);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } catch (NoSuchAlgorithmException | NotLoadedException e) {
            Logging.logServerError(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
    }

    public List<Vehicle> getVehiclesToChoose() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Vehicle.getAllVehicles(conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return new ArrayList<>();
    }
}
