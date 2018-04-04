package com.fos.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Erstellt einen Sql update befehl.
 */
public class SqlUpdateCommand {
    private String table;
    private String wherePart;
    private List<String[]> valueList = new ArrayList<>();

    /**
     * erstellt ein sql update String
     *
     * @param table     the table in the database to update
     * @param wherePart
     */
    public SqlUpdateCommand(String table, String wherePart) {
        this.table = table;
        this.wherePart = wherePart;
    }

    private void addValue(String column, String value) {
        valueList.add(new String[]{column, value});
    }


    /**
     * fügt einen String Wert zum update Befehl hinzu
     *
     * @param column die Spalte von der der Wert ist
     * @param value  Der Wert der hinzugefügt werden soll.
     */
    public void addStringValue(String column, String value) {
        addValue(column, "'" + value + "'");
    }


    /**
     * fügt einen Integer Wert zum update Befehl hinzu
     *
     * @param column die Spalte von der der Wert ist
     * @param value  Der Wert der hinzugefügt werden soll.
     */
    public void addIntValue(String column, Integer value) {
        addValue(column, value.toString());
    }


    /**
     * fügt einen Boolean Wert zum update Befehl hinzu
     *
     * @param column die Spalte von der der Wert ist
     * @param value  Der Wert der hinzugefügt werden soll.
     */
    public void addBooleanValue(String column, Boolean value) {
        addValue(column, value ? "TRUE" : "FALSE");
    }

    /**
     * erstellt den Sql Text für den update Befehl
     *
     * @return den Sql update Befehl
     */
    public String getCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE fos.\"" + table + "\" SET ");

        Boolean isFisrt = true;
        for (String[] commandValue : valueList) {
            if (isFisrt) {
                isFisrt = false;
            } else {
                sb.append(" , ");
            }
            sb.append(" \"" + commandValue[0] + "\" = " + commandValue[1]);
        }
        sb.append(" WHERE " + wherePart);
        sb.append(";");
        return sb.toString();
    }

    @Override
    public String toString() {
        return getCommand();
    }
}
