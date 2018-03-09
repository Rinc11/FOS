package com.fos.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * allgemeine Helfer Methoden
 */
public class Helper {
    private Helper() {
    }

    /**
     * gibt einem die Möglichkeit, wenn eine Methode null zurückgibt einen default wert zu setzen.
     * z.B. statt null eine leere Liste
     * @param normalCall Aufruf der evt. null zurückgibt.
     * @param nullValueCall ist der Rückgabewert, wenn der normalCall null ist.
     * @param <T> der Rückgabe typ von beiden Methoden
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
     * @param object Objekt das evt null ist.
     * @param function Funktion die aufgerufen wird, wenn das Objekt nicht null ist.
     * @param <T> typ des Objektes, welches evt. null sein kann.
     * @param <R> Rückgabewert der Funktion welche man aufrufen will.
     * @return
     */
    public static <T, R> R nullCheck(T object, Function<T, R> function) {
        if (object == null) {
            return null;
        }
        return function.apply(object);
    }

    /**
     * gibt die Connection zurück.
     * @return eine Connection für die Datenbank
     */
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres&ssl=false";
        try {
            Class.forName("org.postgresql.Driver");

            Connection conn = DriverManager.getConnection(url);
            conn.setSchema("fos");
            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
