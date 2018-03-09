package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosPage;
import com.fos.tools.Helper;

import javax.lang.model.element.VariableElement;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Startseite extends FosPage{

    public Startseite(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        super(session, response, false);
    }

    public List<Config> getItems() throws SQLException {
        return Config.getAllConfig(conn);
    }
}
