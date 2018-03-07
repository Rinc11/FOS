package com.fos.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Config {
    private Config(String value, String id){
        this.id = id;
        this.value = value;
    }

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

    public String getId() {
        return id;
    }
    public String getValue() {
        return value;
    }

}
