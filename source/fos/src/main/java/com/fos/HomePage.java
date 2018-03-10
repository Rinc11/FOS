package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosUserPage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends FosUserPage {

    public HomePage(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, false);
    }

    public List<Config> getItems() {
        try {
            return Config.getAllConfig(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            addError("Datenbank Fehler",e);
        }
        return new ArrayList<>();
    }
}
