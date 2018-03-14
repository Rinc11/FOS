package com.fos.database;

/**
 * Exeption welche geworfen wird, wenn auf ein Feld zugegriffen wird, welches noch nicht geladen ist.
 */
public class NotLoadedExeption extends Exception {
    public NotLoadedExeption(String message) {
        super(message);
    }
}
