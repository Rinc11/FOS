package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosUserPage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Startseite extends FosUserPage {

    public Startseite(HttpSession session, HttpServletResponse response) throws IOException, SQLException {
        super(session, response, false);
    }

    public List<Config> getItems() throws SQLException {
        return Config.getAllConfig(conn);
    }
}
