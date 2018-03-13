package com.fos.tools;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    /***
     * fügt einen Fehler hinzu welcher der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     * @param request der request wo die fehlermeldung angezeigt werden soll
     * @param errorMessage die Fehlermelung
     * @param e Exeption welche geloggt wird.
     */
    public static void addError(HttpServletRequest request, String errorMessage, Exception e){
        logExeption(e);
        addError(request, errorMessage);
    }

    /***
     * fügt einen Fehler hinzu welcher der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     * @param request der request wo die fehlermeldung angezeigt werden soll
     * @param errorMessage die Fehlermelung
     */
    public static void addError(HttpServletRequest request, String errorMessage){
        List<String> errorMessages = (List<String>) request.getAttribute("errorMessage");
        if(errorMessages == null){
            errorMessages = new ArrayList<>();
            request.setAttribute("errorMessage", errorMessages);
        }
        errorMessages.add(errorMessage);
    }

    /**
     * schreibt eine Fehlermeldung in die Konsole
     * @param e Exeption welche gedruckt wird
     */
    public static void logExeption(Exception e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }

    /**
     * erstellt einen hash(SHA-256) von einem Text z.B. Passwort
     * @param password Text der gehashed werden soll.
     * @return SHA-256 Hash als String
     */
    public static String getHash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            String hexByte = Integer.toHexString(b & 0xff);
            if(hexByte.length() ==1){
                hexByte = '0'+ hexByte;
            }else if(hexByte.length() != 2){
                throw new RuntimeException();
            }
            sb.append(hexByte);
        }
        return sb.toString();
    }
}
