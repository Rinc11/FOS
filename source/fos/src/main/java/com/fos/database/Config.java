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
        this.id.setValue(id);
        this.value.setValue(value);
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

    private DbObject<String> id = new DbObject<>();
    private DbObject<String> value = new DbObject<>();

    /**
     * gibt den Schlüssel des Konfig Eintrages zurück
     * @return Schlüssel bei der Konfig Tabelle
     */
    public String getId() throws NotLoadedExeption {
        return id.getValue();
    }

    /**
     * gibt den Inhalt des Konfig Eintrages zurück
     * @return Wert des Konfig Eintrages
     */
    public String getValue() throws NotLoadedExeption {
        return value.getValue();
    }
}
