package databaseupdater;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

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

        InputStream resourceAsStream = null;
        try {
            resourceAsStream = SqlUpdate.class.getClassLoader().getResourceAsStream("config.properties");
            Properties prop = new Properties();
            prop.load(resourceAsStream);

            String database = prop.getProperty("database");
            String dbschema = prop.getProperty("dbschema");
            String dbuser = prop.getProperty("dbuser");
            String dbpassword = prop.getProperty("dbpassword");
            String dbport = prop.getProperty("dbport");
            boolean withTestData = prop.getProperty("test") != null && prop.getProperty("test").equals ("true");
            String connectionString = "jdbc:postgresql://" + database + ":" + dbport + "/postgres?user=" + dbuser + "&password=" + dbpassword + "&ssl=false&useUnicode=true&characterEncoding=utf-8";

            new DataBaseUpdater(connectionString, dbschema, withTestData);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                System.out.println("File konnte nicht geschlossen werden");
            }
        }
    }

    /***
     * ruft die SqlUpdate.UpdateDatabase Methode auf - der eigentliche Update Befehl
     * @param url Verbindungs String
     * @param schema Schma der Datenbank welche geupdatet werden soll
     */
    public DataBaseUpdater(String url, String schema, boolean withTest) {
        Connection conn;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url);

            conn.setSchema(schema);
            new SqlUpdate(schema, withTest).UpdateDatabase(conn);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
