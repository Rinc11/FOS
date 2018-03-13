package com.fos.database;

/**
 * Ein Objekt um ein Datenbankfeld abzubilden
 * Es unterstützt auch nicht geladene Datenbank felder
 * der wert darf auch null sein, da dies auch auf der Db gespeichert werden kann.
 *
 * @param <T> Der Typ der den Datenbanktyp in Java representiert.
 */
public class DbObject<T> {
    private T value = null;
    private Boolean loaded = false;

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
     * @throws NotLoadedExeption wenn das Feld noch nicht geladen ist wird diese Exeption geworfen.
     */
    public T getValue() throws NotLoadedExeption {
        if (loaded == false) {
            throw new NotLoadedExeption("Database field is not loaded");
        }
        return value;
    }

    /**
     * fügt den Wett zu einem Feld hinzu. Funktjioniert nur wenn es noch nicht geladen ist.
     *
     * @param value der Wert der hinzugefügt wird.
     */
    public void setValue(T value) {
        if (isLoaded()) {
            throw new RuntimeException("Feld wurde bereits geladen");
        }
        loaded = true;
        this.value = value;
    }


    /**
     * setzt den Wert auf ein geladenes Feld.
     * Diese Methode sollte nur dann verwendet werden, wenn das Feld auf die Datenbank aktualisiert wird.
     *
     * @param value den neune Wert, den man auch auf dei Datenbank geupdatet hat.
     * @throws NotLoadedExeption Fehler wenn das Objekt noch nicht geladen ist.
     */
    void setValueOnLoadedObject(T value) throws NotLoadedExeption {
        if (!loaded) {
            throw new NotLoadedExeption("Database field is not loaded");
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
        if (isLoaded()) {
            if (value != null) {
                return false;
            }
        }
        return true;
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
