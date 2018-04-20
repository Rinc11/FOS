package com.fos.tools;

import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Klasse für allgemeine Helfer Methoden
 */
public class Helper {
    private Helper() {
    }

    private static String connectionString = null;
    private static String dbschema;
    private static boolean withTestData = false;


    /**
     * gibt einem die Möglichkeit, wenn eine Methode null zurückgibt einen default wert zu setzen.
     * z.B. statt null eine leere Liste
     *
     * @param normalCall    Aufruf der evt. null zurückgibt.
     * @param nullValueCall ist der Rückgabewert, wenn der normalCall null ist.
     * @param <T>           der Rückgabe typ von beiden Methoden
     * @return gibt den Rückgabetyp von der 1. Methode zurück. Wenn diese null zurückgibt die 2. Methode.
     */
    public static <T> T nullValue(Supplier<T> normalCall, Supplier<T> nullValueCall) {
        T result = normalCall.get();
        if (result == null) {
            result = nullValueCall.get();
        }
        return result;
    }

    /**
     * sicheres aufrufen von Methoden eines Objektes, welches evt null sein kann.
     * Im null fall gibt die Methode auch null zurück.
     *
     * @param object   Objekt das evt null ist.
     * @param function Funktion die aufgerufen wird, wenn das Objekt nicht null ist.
     * @param <T>      Typ des Objektes, welches evt. null sein kann.
     * @param <R>      Rückgabewert der Funktion welche man aufrufen will.
     * @return
     */
    public static <T, R> R nullCheck(T object, Function<T, R> function) {
        if (object == null) {
            return null;
        }
        return function.apply(object);
    }


    /**
     * Erstellt nach Möglichkeit eine SQL Datenbank Connection
     *
     * @return eine Connection für die Datenbank
     */
    public static Connection getConnection() throws SQLException {
        if (connectionString == null) {
            InputStream resourceAsStream = null;
            try {
                resourceAsStream = Helper.class.getClassLoader().getResourceAsStream("config.properties");
                Properties prop = new Properties();
                prop.load(resourceAsStream);

                String database = prop.getProperty("database");
                dbschema = prop.getProperty("dbschema");
                String dbuser = prop.getProperty("dbuser");
                String dbpassword = prop.getProperty("dbpassword");
                String  dbport= prop.getProperty("dbport");
                withTestData = nullValue(() -> nullCheck(prop.getProperty("test"), o -> o.equals("true")), () -> false );
                connectionString = "jdbc:postgresql://"+database+":"+dbport+"/postgres?user="+dbuser+"&password="+dbpassword+"&ssl=false&useUnicode=true&characterEncoding=utf-8";
            } catch (IOException e) {
                String msg = "Lesefehler bei der Connection String Config";
                Logging.logMessage(msg, Level.FATAL);
                throw new RuntimeException(msg);
            } finally {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    Logging.logExeption(e, "ressource not closeable", Level.ERROR);
                }
            }
        }

        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(connectionString);
            conn.setSchema(dbschema);
            return conn;
        } catch (ClassNotFoundException e) {
            String msg = "connection konnte nicht erstellt werden";
            Logging.logMessage(msg, Level.FATAL);
            throw new RuntimeException(msg);
        }
    }

    /**gibt das Datenbank schema zurück
     *
     * @return das Datenbankschema
     */
    public static String getDbchema() {
        return dbschema;
    }


    /**
     * Gibt zurück, ob in der Konfig eingetragen ist, ob testdaten mit erstellt werden sollen.
     * @return true wenn testdaten miterstellt werden sollen
     */
    public static boolean getWithTestData(){
        return withTestData;
    }


    /**
     * erstellt einen hash(SHA-256) von einem Text z.B. Passwort
     *
     * @param password Text der gehashed werden soll.
     * @return SHA-256 Hash als String
     */
    public static String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hexByte = Integer.toHexString(b & 0xff);
            if (hexByte.length() == 1) {
                hexByte = '0' + hexByte;
            } else if (hexByte.length() != 2) {
                String msg = "fehler beim Hasalgorithmus";
                Logging.logMessage(msg, Level.FATAL);
                throw new RuntimeException(msg);
            }
            sb.append(hexByte);
        }
        return sb.toString();
    }

    public static Timestamp dateToSqlTimestamp(Date date){
        Timestamp timestamp = new java.sql.Timestamp(date.getTime());
        return timestamp;
    }

}
