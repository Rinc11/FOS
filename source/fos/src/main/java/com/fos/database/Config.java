package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * stellt ein fosConfig Datensatz dar
 */
public class Config {
    private Config(String value, String id){
        this.id = id;
        this.value = value;
    }

    /**
     * gibt alle Konfig Einträge zurück
     * @param conn Die Connection zur Datenbank
     * @return Eine Liste von Konfig einträge
     */
    public static List<Config> getAllConfig(Connection conn) throws SQLException {
        List<Config> result = new ArrayList<>();
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT configid, configvalue from fosconfig");
        while (resultSet.next()){
           result.add(new Config(resultSet.getString("configvalue"), resultSet.getString("configid")));
        }
        return result;
    }

    private String id;
    private String value;

    /**
     * gibt den Schlüssel des Konfig Eintrages zurück
     * @return Schlüssel bei der Konfig Tabelle
     */
    public String getId() {
        return id;
    }

    /**
     * gibt den Inhalt des Konfig Eintrages zurück
     * @return Wert des Konfig Eintrages
     */
    public String getValue() {
        return value;
    }
}
