package tools;

import databaseupdater.SqlUpdate;

import java.sql.Connection;
import java.sql.SQLException;

public class Helper {
    private static boolean alreadyLoaded = false;

    /**
     * Updated die Datenbank mit Testdaten
     * @throws SQLException Wift Fehler von der Datenbank
     */
    public static void loadDatabaseUpdates() throws SQLException {
        if(alreadyLoaded){
            return;
        }
        Connection connection = com.fos.tools.Helper.getConnection();
        new SqlUpdate(com.fos.tools.Helper.getDbschema(), true).UpdateDatabase(connection);
        alreadyLoaded = true;
    }
}
