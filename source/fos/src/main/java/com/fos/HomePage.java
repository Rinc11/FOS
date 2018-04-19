package com.fos;

import com.fos.database.NotLoadedException;
import com.fos.database.Person;
import com.fos.database.Vehicle;
import com.fos.tools.FosPage;
import com.fos.tools.Helper;
import com.fos.tools.Logging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Logik für die Startseite
 */
public class HomePage extends FosPage {
    /**
     * Logic für die Startseite
     *
     * @param request  servlet request
     */
    public HomePage(HttpServletRequest request) {
        super(request, false);
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public Boolean getHasOpenTrip() {
        return false;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getPersonalKmBusiness() {
        return 500;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getPersonalKmPrivate() {
        return 210;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getCompanyKmBusiness() {
        return 50000;
    }

    /**
     * muss noch mit Trip verbunden werden(logik kommt in Meilenstein 3)
     *
     * @return
     */
    public int getCompanyKmPrivate() {
        return 311;
    }

    public Integer getLockedUserCount() {
        Connection conn = null;

        try {
            conn = Helper.getConnection();
            return (int) Person.getAllPersons(conn).stream().filter(f -> {
                try {
                    return f.getLocked();
                } catch (NotLoadedException notLoadedExeption) {
                    Logging.logServerError(request, notLoadedExeption);
                }
                return false;
            }).count();
        } catch (SQLException e) {
            Logging.logDatabaseException(request, e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                Logging.logConnectionNotCloseable(e);
            }
        }
        return null;
    }

    public List<Vehicle> getVehiclesToChoose() {
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Vehicle.getAllVehicles(conn);
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
        return "/WEB-INF/jsp/home.jsp";
    }
}
