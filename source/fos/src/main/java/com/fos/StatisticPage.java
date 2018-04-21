package com.fos;

import com.fos.database.Config;
import com.fos.database.Person;
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

public class StatisticPage extends FosPage {
    private String jspFile = "/WEB-INF/jsp/statistic.jsp";
    private List<Trip> filteredTrips;
    /**
     * Erstellt eine neue Statistic Seite
     *
     * @param request         der request vom jsp
     */
    public StatisticPage(HttpServletRequest request) {
        super(request, false);
        //filterdTrips = bla
    }

    @Override
    public String getJspPath() {
        return jspFile;
    }

    public List<Config> getFilteredTrips(){

        List<Config> result = new ArrayList<>();
        return result;
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

    public List<Person> getPersonToChoose(){
        Connection conn = null;
        try {
            conn = Helper.getConnection();
            return Person.getAllPersons(conn);
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
}
