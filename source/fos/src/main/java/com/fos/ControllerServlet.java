package com.fos;

import com.fos.tools.FosPage;
import com.fos.tools.FosPageExport;
import com.fos.tools.Logging;
import org.apache.logging.log4j.Level;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"", "/benutzer", "/fahrzeug", "/fahrzeugHinzufuegen", "/fahrzeugAendern", "/benutzerHinzufuegen",
        "/benutzerAendern", "/auswertung", "/auswertung.csv","/fahrt", "/fahrtAendern"})//
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        checkLogout(request);

        FosPage fosUserPage = null;
        boolean exportCsv = false;
        switch (request.getRequestURI()) {
            case "/benutzer":
                fosUserPage = new UserPage(request);
                break;
            case "/benutzerHinzufuegen":
                fosUserPage = new UserPage(request, "/WEB-INF/jsp/addUser.jsp");
                break;
            case "/benutzerAendern":
                fosUserPage = new UserPage(request, "/WEB-INF/jsp/editUser.jsp");
                break;
            case "/fahrzeug":
                fosUserPage = new VehiclePage(request);
                break;
            case "/fahrzeugHinzufuegen":
                fosUserPage = new VehiclePage(request, "/WEB-INF/jsp/addVehicle.jsp");
                break;
            case "/fahrzeugAendern":
                fosUserPage = new VehiclePage(request, "/WEB-INF/jsp/editVehicle.jsp");
                break;
            case "/auswertung":
                fosUserPage = new StatisticPage(request);
                break;
            case "/auswertung.csv":
                exportCsv = true;
                fosUserPage = new StatisticPage(request);
                break;
            case "/fahrt":
                fosUserPage = new TripPage(request);
                break;
            case "/fahrtAendern":
                fosUserPage = new TripPage(request, "/WEB-INF/jsp/editTrip.jsp");
                break;
            case "/":
                fosUserPage = new HomePage(request);
                break;
            default:
                Logging.logMessage("Seite nicht erkannt", Level.ERROR);
                break;
        }
        if (fosUserPage != null && fosUserPage.loginValid()) {
            if (!exportCsv) {
                request.setAttribute("actualPage", fosUserPage);
                request.getRequestDispatcher(fosUserPage.getJspPath()).include(request, response);
            } else {
                createCsv(response,(FosPageExport) fosUserPage);
            }
        } else {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").include(request, response);
        }
    }

    //https://www.mkyong.com/java/how-to-download-file-from-website-java-jsp/
    private void createCsv(HttpServletResponse response, FosPageExport fosPageExport) throws IOException {
        response.setContentType("text/csv; charset=UTF-16");
        response.setHeader("Content-Disposition",
                "attachment;filename=export.csv");
        StringBuilder sb = new StringBuilder();
        sb.append(fosPageExport.getExport());
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(sb.toString().getBytes("utf-16be"));
    }

    private void checkLogout(HttpServletRequest request) {
        String command = request.getParameter("command");
        if (command != null && command.equals("logout")) {
            FosPage.logout(request);
        }
    }
}
