package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Stellt einen Datensatz einer Person dar
 */
public class Person {

    /**
     * gibt den Datensatz eines Users zurück
     *
     * @param userName angabe des Users
     * @param conn     Verbindung zur Datenbank
     * @return einen Datenbank Datensatz
     */
    public static Person getPerson(String userName, Connection conn) throws SQLException {
        Person result = null;
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"Username\", \"Firstname\", \"Lastname\", \"AHV\", \"Street\", \"Place\"," +
                        " \"Email\", \"Password\", \"PasswordHint\", \"Locked_YN\", \"LoginTry\", \"Usertype\"," +
                        " \"Deleted_YN\" FROM fos.\"Person\" WHERE \"Username\" = '" + userName + "';"
        );
        if (resultSet.next()) {
            result = new Person(
                    resultSet.getString("Username"),
                    resultSet.getString("Firstname"),
                    resultSet.getString("Lastname"),
                    resultSet.getString("AHV"),
                    resultSet.getString("Street"),
                    resultSet.getString("Place"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("PasswordHint"),
                    resultSet.getBoolean("Locked_YN"),
                    resultSet.getInt("LoginTry"),
                    PersonUserType.valueOf(resultSet.getString("Usertype").toUpperCase()),
                    resultSet.getBoolean("Deleted_YN"));
        }

        return result;
    }

    /**
     * gibt alle Personen zurück
     *
     * @param conn Die Connection zur Datenbank
     * @return Eine Liste von Personen
     */
    public static List<Person> getAllPersons(Connection conn) throws SQLException {
        List<Person> result = new ArrayList<Person>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT \"Username\", \"Firstname\", \"Lastname\", \"AHV\", \"Street\", \"Place\"," +
                        " \"Email\", \"Password\", \"PasswordHint\", \"Locked_YN\", \"LoginTry\", \"Usertype\"," +
                        " \"Deleted_YN\" FROM fos.\"Person\";"
        );
        while (resultSet.next()) {
            result.add(
                    new Person(
                            resultSet.getString("Username"),
                            resultSet.getString("Firstname"),
                            resultSet.getString("Lastname"),
                            resultSet.getString("AHV"),
                            resultSet.getString("Street"),
                            resultSet.getString("Place"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getString("PasswordHint"),
                            resultSet.getBoolean("Locked_YN"),
                            resultSet.getInt("LoginTry"),
                            PersonUserType.valueOf(resultSet.getString("Usertype").toUpperCase()),
                            resultSet.getBoolean("Deleted_YN")));
        }
        return result;
    }

    private DbObject<String> userName = new DbObject<>();
    private DbObject<String> firstName = new DbObject<>();
    private DbObject<String> lastName = new DbObject<>();
    private DbObject<String> ahv = new DbObject<>();
    private DbObject<String> street = new DbObject<>();
    private DbObject<String> place = new DbObject<>();
    private DbObject<String> email = new DbObject<>();
    private DbObject<String> passwordHash = new DbObject<>();
    private DbObject<String> passwordHint = new DbObject<>();
    private DbObject<Boolean> locked = new DbObject<>();
    private DbObject<Integer> loginTry = new DbObject<>();
    private DbObject<PersonUserType> userType = new DbObject<>();
    private DbObject<Boolean> deleted = new DbObject<>();

    private Person(String userName, String firstName, String lastName, String ahv,
                   String street, String place, String email, String passwordHash,
                   String passwordHint, Boolean locked, Integer loginTry,
                   PersonUserType userType, Boolean deleted) {
        this.userName.setValue(userName);
        this.firstName.setValue(firstName);
        this.lastName.setValue(lastName);
        this.ahv.setValue(ahv);
        this.street.setValue(street);
        this.place.setValue(place);
        this.email.setValue(email);
        this.passwordHash.setValue(passwordHash);
        this.passwordHint.setValue(passwordHint);
        this.locked.setValue(locked);
        this.loginTry.setValue(loginTry);
        this.userType.setValue(userType);
        this.deleted.setValue(deleted);
    }

    /**
     * gibt den Benutzername zurück
     *
     * @return Benutzername
     */
    public String getUserName() throws NotLoadedExeption {
        return userName.getValue();
    }

    /**
     * gibt den Vorname zurück
     *
     * @return Vorname
     */
    public String getFirstName() throws NotLoadedExeption {
        return firstName.getValue();
    }

    /**
     * gibt den Nachname zurück
     *
     * @return Nachname
     */
    public String getLastName() throws NotLoadedExeption {
        return lastName.getValue();
    }

    /**
     * gibt die AHV nummer als Text zurück
     *
     * @return AHV nummer als Text
     */
    public String getAhv() throws NotLoadedExeption {
        return ahv.getValue();
    }

    /**
     * gibt die Strasse zurück
     *
     * @return Strassenname
     */
    public String getStreet() throws NotLoadedExeption {
        return street.getValue();
    }

    /**
     * gibt den Wohnort zurück
     *
     * @return Wohnort des Benutzers
     */
    public String getPlace() throws NotLoadedExeption {
        return place.getValue();
    }

    /**
     * gibt die E-Mail Addresse zurück
     *
     * @return E-Mail Addresse
     */
    public String getEmail() throws NotLoadedExeption {
        return email.getValue();
    }

    /**
     * gibt das Passwort in gehashter Form zurück
     *
     * @return Passwort als SHA-256
     */
    public String getPasswordHash() throws NotLoadedExeption {
        return passwordHash.getValue();
    }

    /**
     * gibt den Passwort Hinweis zurück
     *
     * @return Passwort Hinweis
     */
    public String getPasswordHint() throws NotLoadedExeption {
        return passwordHint.getValue();
    }

    /**
     * gibt an ob der Benutzer gesperrt ist
     *
     * @return ob Benutzer gesperrt ist
     */
    public Boolean getLocked() throws NotLoadedExeption {
        return locked.getValue();
    }

    /**
     * Anzahl misslungenen Anmelde Versuche
     *
     * @return Anzahl misslungenen Anmelde Versuche
     */
    public Integer getLoginTry() throws NotLoadedExeption {
        return loginTry.getValue();
    }

    /**
     * der Typ des Benutzers(z.B. Admin, Mitarbeiter)
     *
     * @return Typ des Benutzers
     */
    public PersonUserType getUserType() throws NotLoadedExeption {
        return userType.getValue();
    }

    /**
     * gibt an ob der Benutzer gesperrt ist.
     *
     * @return ob der Benutzer gesperrt ist.
     */
    public Boolean getDeleted() throws NotLoadedExeption {
        return deleted.getValue();
    }

    /**
     * setzt den Login Zähler auf einen neuen Wert.
     *
     * @param loginTry neuer Login Zähler Wert
     * @param conn     Verbindung zur Datenbank
     * @throws SQLException
     */
    public void setLoginTry(int loginTry, Connection conn) throws SQLException, NotLoadedExeption {
        conn.createStatement().execute("UPDATE fos.\"Person\" SET \"LoginTry\"=" + loginTry + " WHERE \"Username\" = '" + userName + "'");
        if (loginTry > 10 && userType.getValue() == PersonUserType.MITARBEITER) {
            conn.createStatement().execute("UPDATE fos.\"Person\" SET \"Locked_YN\" = TRUE WHERE \"Username\" = '" + userName + "';");
        }
        this.loginTry.setValueOnLoadedObject(loginTry);
    }

    /**
     * Enum mit den Werten für den Benutzertyp
     */
    public enum PersonUserType {
        MITARBEITER, ADMIN
    }
}
