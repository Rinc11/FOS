package com.fos.tools;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class Logging {

    final static Logger logger = LogManager.getRootLogger();

    private Logging(){}

    /***
     * fügt einen Fehler hinzu welcher der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     * @param request der request wo die fehlermeldung angezeigt werden soll
     * @param errorMessage die Fehlermelung
     * @param e Exeption welche geloggt wird.
     */
    public static void logErrorVisibleToUser(HttpServletRequest request, String errorMessage, Exception e, Level logLevel) {
        logExeption(e, errorMessage, logLevel);
        messageToUser(request, errorMessage);
    }

    /***
     * fügt einen Fehler hinzu welcher der Benutzer sehen wird.
     * Dafür ist aber auf der jsp Seite der showErrorMessage include notwendig.
     * @param request der request wo die fehlermeldung angezeigt werden soll
     * @param errorMessage die Fehlermelung
     */
    public static void messageToUser(HttpServletRequest request, String errorMessage) {
        List<String> errorMessages = (List<String>) request.getAttribute("errorMessage");
        if (errorMessages == null) {
            errorMessages = new ArrayList<>();
            request.setAttribute("errorMessage", errorMessages);
        }
        if(!errorMessages.contains(errorMessage)) {
            errorMessages.add(errorMessage);
        }
    }

    /**
     * schreibt eine Fehlermeldung in die Konsole
     *
     * @param e Exeption welche gedruckt wird
     */
    public static void logExeption(Exception e, String msg,  Level logLevel) {
        logger.log(logLevel,msg , e);

    }

    /**
     * logt eine Nachrit ins log
     * @param message nachricht die geloggt wird
     * @param logLevel das log level der Nachricht
     */
    public static void logMessage(String message, Level logLevel){
        logger.log(logLevel, message);
    }

    /**
     * logt einen Datenbankfehler
     * @param request der Request
     * @param e den Datenbankfehler
     */
    public static void logDatabaseException(HttpServletRequest request, Exception e){
        logErrorVisibleToUser(request, "Datenbank Fehler", e , Level.ERROR);
    }

    /**
     * logt einen Serverfehler
     * @param request der Request
     * @param e den Fehler
     */
    public static void logServerError(HttpServletRequest request, Exception e){
        logErrorVisibleToUser(request, "Server Fehler", e , Level.ERROR);
    }

    /**
     * logt einen Rechtefehler
     * @param request der Request
     * @param e den Fehler
     */
    public static void logMissingPermission(HttpServletRequest request, MissingPermissionException e){
        logErrorVisibleToUser(request, "fehlende Rechte", e, Level.WARN);
    }
}
