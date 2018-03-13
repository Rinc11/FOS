package com.fos.dataBaseUpdater;


import java.sql.Connection;
import java.sql.DriverManager;

/***
 * Diese Klasse started den Update Prozess der Datenbank.
 */
public class DataBaseUpdater {

    /***
     * main Methode. Start Methode des Konsolenanwendung
     * @param args Argumente werden keine benötigt. Macht nichts
     */
    public static void main(String[] args) {
        System.out.println("start database update");

        try {
            new DataBaseUpdater("jdbc:postgresql://localhost:5432/postgres?user=postgres&password=postgres&ssl=false");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /***
     * ruft die SqlUpdate.UpdateDatabase Methode auf - der eigentliche Update Befehl
     * @param url
     * @throws Exception
     */
    public DataBaseUpdater(String url) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);

            conn.setSchema("fos");
            new SqlUpdate().UpdateDatabase(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
