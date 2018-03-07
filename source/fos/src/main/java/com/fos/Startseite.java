package com.fos;

import com.fos.database.Config;
import com.fos.tools.Helper;

import javax.lang.model.element.VariableElement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Startseite {
    private Connection conn;
    public Startseite() throws SQLException {
        conn = Helper.getConnection();
    }
    public List<Config> getItems() throws SQLException {
        return Config.getAllConfig(conn);
    }
}
