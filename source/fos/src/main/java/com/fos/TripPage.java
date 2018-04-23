package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Trip;
import com.fos.database.Vehicle;
import com.fos.tools.FosPage;
import com.fos.tools.Helper;
import com.fos.tools.Logging;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TripPage extends FosPage {

    private String jspFile = "/WEB-INF/jsp/trip.jsp";

    public TripPage(HttpServletRequest request, String jspFile) {
        this(request);
        this.jspFile = jspFile;
    }

    public TripPage(HttpServletRequest request) {
        super(request, false);
    }

    public List<Trip> getItems() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Trip.getAllTrips(conn);
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public String getJspPath() {
        try {
            if (getUser().getIsAdmin()) {
                return jspFile;
            }
        } catch (NotLoadedException notLoadedExeption) {
            Logging.logDatabaseException(request, notLoadedExeption);
        }
        return "/WEB-INF/jsp/trip.jsp";
    }
}
