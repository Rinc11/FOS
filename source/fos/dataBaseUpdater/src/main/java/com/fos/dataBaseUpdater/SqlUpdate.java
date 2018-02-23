package com.fos.dataBaseUpdater;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SqlUpdate {

    private HashMap<Integer, String> commands = new HashMap<>();

    public SqlUpdate() throws Exception {
        addCommand(1, ""+
                "CREATE TABLE bla (\n" +
                "  col int not null\n" +
                ");");
        addCommand(2, ""+
                "CREATE TABLE blaa (\n" +
                "  col int not null\n" +
                ");");
    }

    private int lastCommandId = -1;
    private void addCommand(int id, String command) throws Exception {
        if(commands.containsKey(id)){
            System.out.println("key already exists");
            throw new Exception("key already exists");
        }else {
            if(lastCommandId<id){
                commands.put(id,command );
            }else{
                System.out.println("id is smaller then last");
                throw new Exception("id is smaller then last");
            }
        }
    }

    private int getLastCommandId(){
        return commands.keySet().stream()
                .max(Integer::compareTo)
                .orElse(-1);
    }

    private List<Map.Entry> getCommandsAfter(int afterId){
        return commands.entrySet().stream()
                .filter(f -> f.getKey()> afterId)
                .sorted(Comparator.comparingInt(Map.Entry::getKey))
                .collect(Collectors.toList());
    }

    public void UpdateDatabase(Connection conn) throws SQLException {
        int dbVersion = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT value FROM \"Config\" WHERE \"Id\"='dbVersion'");
            resultSet.next();
            dbVersion = Integer.parseInt(resultSet.getString(1));
        }catch(SQLException e) {
            Statement statement = conn.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS fos");
            statement.execute(""+
                    "CREATE TABLE \"Config\" (\n" +
                    "  \"Id\"  TEXT NOT NULL\n" +
                    "    CONSTRAINT \"Config_pkey\"\n" +
                    "    PRIMARY KEY,\n" +
                    "  value TEXT\n" +
                    ");\n");
            statement.execute("INSERT INTO \"Config\" (\"Id\", value) VALUES ('dbVersion', '0');");
        }

        conn.setAutoCommit(false);
        if(dbVersion < getLastCommandId()){
            List<Map.Entry> commands = getCommandsAfter(dbVersion);
            for(Map.Entry<Integer, String> command : commands){
                try {
                    conn.createStatement().execute(command.getValue());
                    conn.createStatement().execute("UPDATE \"Config\" set value = '" + command.getKey() + "' WHERE \"Id\" = 'dbVersion'");
                    conn.commit();
                    System.out.println("command " + command.getKey() + " executed");
                }catch (SQLException e){
                    System.out.println("error by command " + command.getKey());
                    throw e;
                }
            }
            System.out.println(commands.size() + " commands executed");
        }else{
            System.out.println("database is up to date");
        }
    }
}
