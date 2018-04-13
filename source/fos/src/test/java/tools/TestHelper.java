package tools;

import databaseupdater.SqlUpdate;

import java.sql.Connection;
import java.sql.SQLException;

public class TestHelper {
    private static boolean alreadyLoaded = false;

    /**
     * Updated die Datenbank mit Testdaten
     *
     * @throws SQLException Wift Fehler von der Datenbank
     */
    public static void loadDatabaseUpdates() throws Exception {
        if (alreadyLoaded) {
            return;
        }
        Connection connection = com.fos.tools.Helper.getConnection();
        connection.createStatement().execute("DROP SCHEMA IF EXISTS " + com.fos.tools.Helper.getDbchema() + " CASCADE ");
        new SqlUpdate(com.fos.tools.Helper.getDbchema(), true).UpdateDatabase(connection);
        alreadyLoaded = true;
    }
}
