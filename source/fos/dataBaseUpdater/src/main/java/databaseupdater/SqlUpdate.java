package databaseupdater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Updated die Datenbank auf die neusete Version.
 */
public class SqlUpdate {

    private HashMap<Integer, String> commands = new HashMap<>();
    private int lastCommandId = -1;

    /**
     * erstellt eine Liste aller befehle in dataBaseUpdateSkript.sql
     *
     * @throws IOException beim lesen von dataBaseUpdateSkript.sql
     */
    public SqlUpdate() {
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("dataBaseUpdateSkript.sql");

            BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            int commandNumber = -1;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--#")) {
                    if (commandNumber != -1) {
                        addCommand(commandNumber, sb.toString());
                        sb = new StringBuilder();
                    }
                    String numberStr = line.substring("--#".length(), line.indexOf(":"));
                    commandNumber = Integer.parseInt(numberStr);
                } else {
                    sb.append(line);
                    sb.append("\n");
                }
            }
            if (commandNumber != -1) {
                addCommand(commandNumber, sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Fügt ein sql Befehl zur Liste hinzu
     *
     * @param id      die Id des Befehles
     * @param command der eigentliche Befehl
     */
    private void addCommand(int id, String command) {
        if (commands.containsKey(id)) {
            System.out.println("key already exists");
            throw new RuntimeException("key already exists");
        } else {
            if (lastCommandId < id) {
                commands.put(id, command);
            } else {
                System.out.println("id is smaller then last");
                throw new RuntimeException("id is smaller then last");
            }
        }
    }

    /**
     * gibt die letzte Befehls id zurück welche im sql skript vorhanden ist
     *
     * @return letze id im sql Skript(aktuellster befehl)
     */
    private int getLastCommandId() {
        return commands.keySet().stream()
                .max(Integer::compareTo)
                .orElse(-1);
    }

    /***
     * gibt eine Liste von Befehlen welche eine grössere id haben als der parameter
     * @param afterId alle Befehle mit einer Id grösser als diese Nummer werden zurückgegeben
     * @return eine Liste von entryies mit der id und dem Befehl der Id
     */
    private List<Map.Entry> getCommandsAfter(int afterId) {
        return commands.entrySet().stream()
                .filter(f -> f.getKey() > afterId)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .collect(Collectors.toList());
    }

    /**
     * started das Update der Datenbank. Es werden nur befehle ausgeführt welche noch nicht
     * auf der Datenbank vorhanden sind. Dafür wird die Tabelle "fosConfig" verwendet
     *
     * @param conn die verbindung zur Datenbank
     * @throws SQLException wird geworfen wenn es ein Fehler mit der Datenkbank gibt.
     */
    public void UpdateDatabase(Connection conn) {//kürzer und weniger try catch
        try {

            int dbVersion = getLastDbVersionFromDb(conn);

            conn.setAutoCommit(false);
            if (dbVersion < getLastCommandId()) {
                List<Map.Entry> commandsAfter= getCommandsAfter(dbVersion);
                for (Map.Entry<Integer, String> command : commandsAfter) {
                    try {
                        conn.createStatement().execute(command.getValue());
                        conn.createStatement().execute("UPDATE FosConfig set ConfigValue = '" + command.getKey() + "' WHERE ConfigId = 'dbVersion'");
                        conn.commit();
                        System.out.println("command " + command.getKey() + " executed");
                    } catch (SQLException e) {
                        System.out.println("error by command " + command.getKey());
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        throw e;
                    }
                }
                System.out.println(commandsAfter.size() + " commands executed");
            } else {
                System.out.println("database is up to date");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * gibt die Version des Datenbankstandes zurück. wenn keine Datenbank vorhanden ist probiert es diese zu erstellen.
     * @param conn Datenbnakverbindung
     * @return Datenbankstand
     */
    private int getLastDbVersionFromDb(Connection conn) {
        int dbVersion = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT ConfigValue FROM FosConfig WHERE ConfigId='dbVersion'");
            resultSet.next();
            dbVersion = Integer.parseInt(resultSet.getString(1));
        } catch (SQLException e) {
            createFosSchema(conn);
        }
        return dbVersion;
    }

    /**
     * Erstellt eine neues Fos Schema.
     * @param conn Datenbankverbindung
     */
    private void createFosSchema(Connection conn){
        try {
            Statement statement = conn.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS fos");
            statement.execute("" +
                    "CREATE TABLE FosConfig (\n" +
                    "  ConfigId TEXT NOT NULL\n" +
                    "    CONSTRAINT FosConfig_pkey\n" +
                    "    PRIMARY KEY,\n" +
                    "  ConfigValue TEXT\n" +
                    ");\n");
            statement.execute("INSERT INTO FosConfig (ConfigId, ConfigValue) VALUES ('dbVersion', '0');");
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
