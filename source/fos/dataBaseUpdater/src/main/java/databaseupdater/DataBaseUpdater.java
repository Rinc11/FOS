package databaseupdater;

import org.apache.logging.log4j.Level;

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
        SqlUpdate.logger.log(Level.INFO, "start com.fos.database update");

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
            SqlUpdate.logger.log(Level.ERROR, "Fehler beim Ausführen des Sql Updaters", e);
            throw new RuntimeException(e);
        }finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                SqlUpdate.logger.log(Level.ERROR, "File konnte nicht geschlossen werden", e);
            }
        }
    }

    /***
     * ruft die SqlUpdate.updateDatabase Methode auf - der eigentliche Update Befehl
     * @param url Verbindungs String
     * @param schema Schma der Datenbank welche geupdatet werden soll
     */
    public DataBaseUpdater(String url, String schema, boolean withTest) {
        Connection conn;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url);

            conn.setSchema(schema);
            new SqlUpdate(schema, withTest).updateDatabase(conn);
        } catch (Exception e) {
            SqlUpdate.logger.log(Level.ERROR, "Fehler beim Ausführen des Sql Updaters",e);
            throw new RuntimeException(e);
        }
    }
}
