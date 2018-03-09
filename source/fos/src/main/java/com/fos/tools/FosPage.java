package com.fos.tools;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class FosPage {
    protected Connection conn;

    public FosPage(HttpSession session, HttpServletResponse response, Boolean needsAdminRight) throws SQLException, IOException {
        conn = Helper.getConnection();
        if (session.getAttribute("userName") == null) {
            response.sendRedirect("login.jsp");
        }
    }
}
