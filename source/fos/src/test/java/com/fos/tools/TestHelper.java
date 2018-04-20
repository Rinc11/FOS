package com.fos.tools;

import databaseupdater.SqlUpdate;

import java.io.InputStream;
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
        InputStream sqlCommandStream = TestHelper.class.getClassLoader().getResourceAsStream("dataBaseUpdateSkript.sql");
        new SqlUpdate(com.fos.tools.Helper.getDbchema(), true, sqlCommandStream).updateDatabase(connection);
        sqlCommandStream.close();
        alreadyLoaded = true;
    }
}
