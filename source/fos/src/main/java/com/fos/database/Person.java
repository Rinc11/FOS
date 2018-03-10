package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Stellte ein Person Datensatz dar
 */
public class Person {

    /**
     * gibt den Datensatz eines Users zurück
     * @param userName angabe des Users
     * @param conn Verbindung zur Datenbank
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

    private String userName;
    private String firstName;
    private String lastName;
    private String ahv;
    private String street;
    private String place;
    private String email;
    private String passwordHash;
    private String passwordHint;
    private Boolean locked;
    private Integer loginTry;
    private PersonUserType userType;
    private Boolean deleted;

    private Person(String userName, String firstName, String lastName, String ahv,
                   String street, String place, String email, String passwordHash,
                   String passwordHint, Boolean locked, Integer loginTry,
                   PersonUserType userType, Boolean deleted) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ahv = ahv;
        this.street = street;
        this.place = place;
        this.email = email;
        this.passwordHash = passwordHash;
        this.passwordHint = passwordHint;
        this.locked = locked;
        this.loginTry = loginTry;
        this.userType = userType;
        this.deleted = deleted;
    }

    /**
     * gibt den Benutzername zurück
     * @return Benutzername
     */
    public String getUserName() {
        return userName;
    }

    /**
     * gibt den Vorname zurück
     * @return Vorname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * gibt den Nachname zurück
     * @return Nachname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gibt die AHV nummer als Text zurück
     * @return AHV nummer als Text
     */
    public String getAhv() {
        return ahv;
    }

    /**
     * gibt die Strasse zurück
     * @return Strassenname
     */
    public String getStreet() {
        return street;
    }

    /**
     * gibt den Wohnort zurück
     * @return Wohnort des Benutzers
     */
    public String getPlace() {
        return place;
    }

    /**
     * gibt die E-Mail Addresse zurück
     * @return E-Mail Addresse
     */
    public String getEmail() {
        return email;
    }

    /**
     * gibt das Passwort in gehashter Form zurück
     * @return Passwort als SHA-256
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * gibt den Passwort Hinweis zurück
     * @return Passwort Hinweis
     */
    public String getPasswordHint() {
        return passwordHint;
    }

    /**
     * gibt an ob der Benutzer gesperrt ist
     * @return ob Benutzer gesperrt ist
     */
    public Boolean getLocked() {
        return locked;
    }

    /**
     * Anzahl misslungenen Anmelde Versuche
     * @return Anzahl misslungenen Anmelde Versuche
     */
    public Integer getLoginTry() {
        return loginTry;
    }

    /**
     * der Typ des Benutzers(z.B. Admin, Mitarbeiter)
     * @return Typ des Benutzers
     */
    public PersonUserType getUserType() {
        return userType;
    }

    /**
     * gibt an ob der Benutzer gesperrt ist.
     * @return ob der Benutzer gesperrt ist.
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * setzt den Login Zähler auf einen neuen Wert.
     * @param loginTry neuer Login Zähler Wert
     * @param conn Verbindung zur Datenbank
     * @throws SQLException
     */
    public void setLoginTry(int loginTry, Connection conn) throws SQLException {
        conn.createStatement().execute("UPDATE fos.\"Person\" SET \"LoginTry\"=" + loginTry + " WHERE \"Username\" = '" + userName + "'");
        if (loginTry > 10 && userType == PersonUserType.MITARBEITER) {
            conn.createStatement().execute("UPDATE fos.\"Person\" SET \"Locked_YN\" = TRUE WHERE \"Username\" = '" + userName + "';");
        }
        this.loginTry = loginTry;
    }

    /**
     * Enum mit den Werten für den Benutzertyp
     */
    public enum PersonUserType {
        MITARBEITER, ADMIN
    }
}
