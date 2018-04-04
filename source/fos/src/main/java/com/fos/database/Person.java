package com.fos.database;

import com.fos.tools.SqlUpdateCommand;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Stellt einen Datensatz einer Person dar
 */
public class Person implements Serializable {

    /**
     * gibt den Datensatz eines Users zurück
     *
     * @param userName angabe des Users
     * @param conn     Verbindung zur Datenbank
     * @return einen Datenbank Datensatz
     */
    public static Person getPerson(String userName, Connection conn) throws SQLException {
        Person result = null;
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT \"Username\", \"Firstname\", " +
                "\"Lastname\", \"AHV\", \"Street\", \"Place\",\"Email\", \"Password\", \"PasswordHint\", \"Locked_YN\"," +
                " \"LoginTry\", \"Usertype\",\"Deleted_YN\" FROM fos.\"Person\" WHERE \"Username\" = ?;");
        preparedStatement.setString(1, userName);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Person person = new Person(resultSet.getString("Username"));
            person.firstName.setValue(resultSet.getString("Firstname"));
            person.lastName.setValue(resultSet.getString("Lastname"));
            person.ahv.setValue(resultSet.getString("AHV"));
            person.street.setValue(resultSet.getString("Street"));
            person.place.setValue(resultSet.getString("Place"));
            person.email.setValue(resultSet.getString("Email"));
            person.passwordHash.setValue(resultSet.getString("Password"));
            person.passwordHint.setValue(resultSet.getString("PasswordHint"));
            person.locked.setValue(resultSet.getBoolean("Locked_YN"));
            person.loginTry.setValue(resultSet.getInt("LoginTry"));
            person.userType.setValue(PersonUserType.valueOf(resultSet.getString("Usertype").toUpperCase()));
            person.deleted.setValue(resultSet.getBoolean("Deleted_YN"));
            result = person;

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
                        " \"Deleted_YN\" FROM fos.\"Person\" WHERE \"Deleted_YN\" = FALSE ;"
        );
        while (resultSet.next()) {
            Person person = new Person(resultSet.getString("Username"));
            person.firstName.setValue(resultSet.getString("Firstname"));
            person.lastName.setValue(resultSet.getString("Lastname"));
            person.ahv.setValue(resultSet.getString("AHV"));
            person.street.setValue(resultSet.getString("Street"));
            person.place.setValue(resultSet.getString("Place"));
            person.email.setValue(resultSet.getString("Email"));
            person.passwordHash.setValue(resultSet.getString("Password"));
            person.passwordHint.setValue(resultSet.getString("PasswordHint"));
            person.locked.setValue(resultSet.getBoolean("Locked_YN"));
            person.loginTry.setValue(resultSet.getInt("LoginTry"));
            person.userType.setValue(PersonUserType.valueOf(resultSet.getString("Usertype").toUpperCase()));
            person.deleted.setValue(resultSet.getBoolean("Deleted_YN"));
            result.add(person);


        }
        return result;
    }

    public static void addNewPerson(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, String userType, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO fos.\"Person\" (\"Username\", \"Firstname\", \"Lastname\", \"AHV\", \"Street\", \"Place\", \"Email\", \"Password\", \"PasswordHint\", \"Usertype\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, '" + userType + "')");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, firstname);
        preparedStatement.setString(3, lastname);
        preparedStatement.setString(4, ahv);
        preparedStatement.setString(5, street);
        preparedStatement.setString(6, place);
        preparedStatement.setString(7, email);
        preparedStatement.setString(8, password);
        preparedStatement.setString(9, passwordHint);
        preparedStatement.execute();
    }

    public static void updatePerson(String username, String firstname, String lastname, String ahv, String street, String place
            , String email, String password, String passwordHint, Boolean locked, String userType, Connection conn, Boolean commandRunAsAdmin) throws SQLException {

        SqlUpdateCommand command = new SqlUpdateCommand("Person", "\"Username\" = '" + username + "'");
        if (!password.equals("")) {
            command.addStringValue("Password", password);
        }
        command.addStringValue("Firstname", firstname);
        command.addStringValue("Lastname", lastname);
        command.addStringValue("AHV", ahv);
        command.addStringValue("Street", street);
        command.addStringValue("Place", place);
        command.addStringValue("Email", email);
        command.addStringValue("PasswordHint", passwordHint);

        if(commandRunAsAdmin) {
            command.addBooleanValue("Locked_YN", locked);
            command.addStringValue("Usertype", userType);
        }

        Statement statement = conn.createStatement();
        statement.execute(command.toString());
    }

    public static void removePerson(String username, Connection conn) throws SQLException {
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE fos.\"Person\" SET \"Deleted_YN\" = TRUE WHERE \"Username\" = ?");
        preparedStatement.setString(1, username);
        preparedStatement.execute();

    }

    private final DbObject<String> userName = new DbObject<>();
    private final DbObject<String> firstName = new DbObject<>();
    private final DbObject<String> lastName = new DbObject<>();
    private final DbObject<String> ahv = new DbObject<>();
    private final DbObject<String> street = new DbObject<>();
    private final DbObject<String> place = new DbObject<>();
    private final DbObject<String> email = new DbObject<>();
    private final DbObject<String> passwordHash = new DbObject<>();
    private final DbObject<String> passwordHint = new DbObject<>();
    private final DbObject<Boolean> locked = new DbObject<>();
    private final DbObject<Integer> loginTry = new DbObject<>();
    private final DbObject<PersonUserType> userType = new DbObject<>();
    private final DbObject<Boolean> deleted = new DbObject<>();

    private Person(String userName) {
        this.userName.setValue(userName);
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

    public Boolean getIsAdmin() throws NotLoadedExeption {
        return userType.getValue() == PersonUserType.ADMIN;
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
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE fos.\"Person\" SET \"LoginTry\"= ? WHERE \"Username\" = ?;");
        preparedStatement.setInt(1, loginTry);
        preparedStatement.setString(2, userName.getValue());
        preparedStatement.execute();
        if (loginTry > 10 && userType.getValue() == PersonUserType.MITARBEITER) {
            setLocked(true, conn);
        }
        this.loginTry.setValueOnLoadedObject(loginTry);
    }

    /**
     * setzt ob der Benutzer gesperrt ist.
     *
     * @param locked ob er gesperrt ist
     * @param conn   Verbinung zu Datenbank
     */
    public void setLocked(boolean locked, Connection conn) throws SQLException, NotLoadedExeption {
        PreparedStatement preparedStatement = conn.prepareStatement("UPDATE fos.\"Person\" SET \"Locked_YN\"= ? WHERE \"Username\" = ?");
        preparedStatement.setBoolean(1, locked);
        preparedStatement.setString(2, userName.getValue());
        preparedStatement.execute();
        this.locked.setValueOnLoadedObject(locked);
    }

    /**
     * Enum mit den Werten für den Benutzertyp
     */
    public enum PersonUserType {
        MITARBEITER, ADMIN
    }
}
