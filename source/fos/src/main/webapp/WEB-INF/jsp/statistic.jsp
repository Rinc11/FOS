<%@ page import="com.fos.HomePage" %>
<%@ page import="com.fos.database.Person" %><%--
Startseite welche nach dem einlogen aufgerufen wird.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="de">
<head>
    <title>FOS</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Auswertung" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>

    <div class="title">
        <h1>Auswertung</h1>
        <p>Laden Sie Ihre ausgewerteten Daten bequem als *.csv herunter.</p>
    </div>
    <legend style="color: rgb(64, 99, 180);"><button onclick="toggle()">Filter ein-/ausblenden</button></legend>
    <fieldset id="filteroptionen">
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Fahrzeug</label>
                    <input type="text" class="form-control" placeholder="Fahrzeug">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Fahrer</label>
                    <input type="text" class="form-control" placeholder="Fahrer">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Datum von</label>
                    <input type="date" class="form-control">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Datum bis</label>
                    <input type="date" class="form-control">
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Fahrttyp</label>
                    <select class="form-control">
                        <option>geschäftlich</option>
                        <option>privat</option>
                    </select>
                </div>
            </div>
        </div>
    </fieldset>
    <div class="panel panel-default" id="auswertungen">
        <div class="panel-heading">Auswertungen
        </div>

        <div class="scrollme">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>Fahrtnr.</th>
                    <th>Fahrer</th>
                    <th>Auto</th>
                    <th>Fahrt Start</th>
                    <th>Fahrt Ziel</th>
                    <th>Kilometer</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>Fabian Wipf</td>
                    <td>Fiat</td>
                    <td>Frauenfeld</td>
                    <td>Winterthur</td>
                    <td>15</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Jonas Rüegge</td>
                    <td>Smart</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>Reto Mayer</td>
                    <td>VW</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>Marco Sutter</td>
                    <td>Audi</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
