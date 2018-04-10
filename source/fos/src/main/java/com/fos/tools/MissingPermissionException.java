package com.fos.tools;

/**
 * Exeption welche geworfen wird, wenn eine Person nicht das Recht hat für dies Funktion
 */
public class MissingPermissionException extends Exception {
    public MissingPermissionException() {
        super("missing Permission");
    }
}
