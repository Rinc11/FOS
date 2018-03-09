package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosPage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Startseite extends FosPage{

    public Startseite(HttpSession session, HttpServletResponse response) throws SQLException, IOException {
        super(session, response, false);
    }

    public List<Config> getItems() throws SQLException {
        return Config.getAllConfig(conn);
    }
}
