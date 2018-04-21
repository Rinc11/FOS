package com.fos.database;

import com.fos.tools.Logging;
import org.apache.logging.log4j.Level;

/**
 * Ein Objekt um ein Datenbankfeld abzubilden
 * Es unterstützt auch nicht geladene Datenbank felder
 * der wert darf auch null sein, da dies auch auf der Db gespeichert werden kann.
 *
 * @param <T> Der Typ der den Datenbanktyp in Java representiert.
 */
public class DbObject<T> {
    private T value = null;
    private Boolean loaded;

    /**
     * erstellt eine neue DbObject mit einem Wert.
     *
     * @param value der Wert, der von der Datenbank kommt.
     */
    public DbObject(T value) {
        this.value = value;
        loaded = true;
    }

    /**
     * erstellt ein neues DbObject, welche noch nicht geladen ist.
     */
    public DbObject() {
        loaded = false;
    }

    /**
     * gibt den gespeicherten Wert zurück. Wenn es nicht geladen ist wirft es eine Exeption.
     *
     * @return den Wert des Objektes
     * @throws NotLoadedException wenn das Feld noch nicht geladen ist wird diese Exeption geworfen.
     */
    public T getValue() throws NotLoadedException {
        if (!loaded) {
            throw new NotLoadedException("Database field is not loaded");
        }
        return value;
    }

    /**
     * fügt den Wett zu einem Feld hinzu. Funktjioniert nur wenn es noch nicht geladen ist.
     *
     * @param value der Wert der hinzugefügt wird.
     */
    void setValue(T value) {
        if (isLoaded()) {
            String msg = "Feld wurde bereits geladen";
            Logging.logMessage(msg, Level.FATAL);
            throw new RuntimeException(msg);
        }
        loaded = true;
        this.value = value;
    }


    /**
     * setzt den Wert auf ein geladenes Feld.
     * Diese Methode sollte nur dann verwendet werden, wenn das Feld auf die Datenbank aktualisiert wird.
     *
     * @param value den neune Wert, den man auch auf dei Datenbank geupdatet hat.
     * @throws NotLoadedException Fehler wenn das Objekt noch nicht geladen ist.
     */
    void setValueOnLoadedObject(T value) throws NotLoadedException {
        if (!loaded) {
            String msg = "Datenbankfeld ist nicht geladen";
            Logging.logMessage(msg, Level.FATAL);
            throw new NotLoadedException(msg);
        }
        this.value = value;
    }

    /**
     * Ob das Objekt geladen ist.
     *
     * @return true wenn es einen Wert von der Db hat. Null ist auch möglich als Rückgabewert.
     */
    public Boolean isLoaded() {
        return loaded;
    }

    /**
     * gibt an ob der Wert Null ist. Wenn es nicht geladen ist gibt es true zurück.
     *
     * @return ob der Wert Null ist. Wenn nicht gelaen, gibt es true zurück.
     */
    public Boolean isNull() {
        return !isLoaded() || value == null;
    }

    /**
     * gibt den geladenen Wert zurück. Wenn kein Wert gesetz wurde gibt es null zurück.
     * Wirf deshalb auch keine Exeption.
     *
     * @return der Wert oder null.
     */
    public T getValueOrNull() {
        return value;
    }
}

