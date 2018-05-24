package com.fos.database;

/**
 * Exeption welche geworfen wird, wenn ein Benutzer etwas ausführen will wofür er keine Rechte hat.
 */
public class NotLoadedException extends Exception {

    /**
     * Exeption welche geworfen wird, wenn ein Benutzer etwas ausführen will wofür er keine Rechte hat.
     *
     * @param message Zusätzliche Fehler Nachricht
     */
    public NotLoadedException(String message) {
        super(message);
    }
}
