package com.fos;

import com.fos.database.Config;
import com.fos.tools.FosPage;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class StatisticPage extends FosPage {
    private String jspFile = "/WEB-INF/jsp/statistic.jsp";

    /**
     * Erstellt eine neue Statistic Seite
     *
     * @param request         der request vom jsp
     */
    public StatisticPage(HttpServletRequest request) {
        super(request, false);
    }

    @Override
    public String getJspPath() {
        return jspFile;
    }

    public List<Config> getFilteredTrips(){

        List<Config> result = new ArrayList<>();
        return result;
    }
}
