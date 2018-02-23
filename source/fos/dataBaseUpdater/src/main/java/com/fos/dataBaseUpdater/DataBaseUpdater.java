package com.fos.dataBaseUpdater;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseUpdater {
    public static void main (String [] args){
        System.out.println("start database update");

        try {
            new DataBaseUpdater("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres&ssl=false");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataBaseUpdater(String url) throws Exception {
        Connection conn = DriverManager.getConnection(url);
        conn.setSchema("fos");
        new SqlUpdate().UpdateDatabase(conn);
    }


}
