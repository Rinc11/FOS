<%@ page import="com.fos.page.HomePage" %>
<%@ page import="com.fos.database.Person" %>
<%@ page import="com.fos.page.StatisticPage" %>
<%--
Auswertung
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="de">
<head>
    <title>FOS</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
    <link rel="stylesheet" type="text/css" href="print.css" media="print"/>
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
    <form action="auswertung" method="get">
        <legend style="color: rgb(64, 99, 180);"><a class="btn btn-default" onclick="toggle('filteroptionen')">Filter
            ein-/ausblenden</a></legend>
        <fieldset id="filteroptionen">

            <div class="row">
                <div class="col-lg-6">
                    <div class="form-group">
                        <label>Fahrzeug</label>
                        <select class="form-control" name="tripVehicle">
                            <option value="">Alle Fahrzeuge</option>
                            <c:forEach var="vehicle" items="${actualPage.vehiclesToChoose}">
                                <option value="${vehicle.vehicleID}"
                                        <c:if test="${ param.tripVehicle == vehicle.vehicleID}">selected</c:if>>${vehicle.brand} ${vehicle.type}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <c:if test="${userLoggedIn.isAdmin == true}">
                    <div class="col-lg-6">
                        <div class="form-group">
                            <label>Fahrer</label>
                            <select class="form-control" name="tripPerson">
                                <option value="">Alle Personen</option>
                                <c:forEach var="person" items="${actualPage.personToChoose}">
                                    <option value="${person.userName}"
                                            <c:if test="${ param.tripPerson == person.userName}">selected</c:if>>${person.firstName} ${person.lastName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </c:if>
            </div>

            <div class="row">
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Datum von</label>
                        <input type="date" name="dateFrom" class="form-control" value="${param.dateFrom}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Datum bis</label>
                        <input type="date" name="dateTo" class="form-control" value="${param.dateTo}">
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="form-group">
                        <label>Fahrttyp</label>
                        <select class="form-control" name="tripType">
                            <option value="" <c:if test="${param.tripType == ''}"> selected</c:if>>alle</option>
                            <option value="g" <c:if test="${param.tripType == 'g'}"> selected</c:if> >geschäftlich
                            </option>
                            <option value="p" <c:if test="${param.tripType == 'p'}"> selected</c:if>>privat</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-6">
                    <label> </label>
                    <div class="form-group">
                        <a href="auswertung" class="btn btn-default">Reset</a>
                        <button type="submit" class="btn btn-default">Filtern</button>
                        <a class="btn btn-default"
                           href="auswertung.csv?tripVehicle=${param.tripVehicle}&tripPerson=${param.tripPerson}&dateFrom=${param.dateFrom}&dateTo=${param.dateTo}&tripType=${param.tripType}">Export</a>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>

    <div class="row">
        <div class="col-md-6">

            <script>
                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);

                function drawChart() {

                    var data = google.visualization.arrayToDataTable([
                        ['Type', 'KM'],
                        ['Geschäftlich',      ${actualPage.filteredKmBusiness}],
                        ['Privat',     ${actualPage.filteredKmPrivat}],


                    ]);

                    var options = {
                        title: 'Vergleich Fahrten: Privat/Geschäftlich'
                    };

                    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

                    chart.draw(data, options);
                }
            </script>
            <div id="piechart" ></div>
            <br>
        </div>
        <div class="col-md-6">
            <div class="panel panel-default" id="auswertungen">
                <div class="panel-heading">Auswertung</div>
                <table class="table table-responsive">
                    <tr>
                        <td><strong>Anzahl Kilometer Privat</strong></td>
                        <td>${actualPage.filteredKmPrivat}</td>
                    </tr>
                    <tr>
                        <td><strong>Anzahl Kilometer Geschäftlich</strong></td>
                        <td>${actualPage.filteredKmBusiness}</td>
                    </tr>
                    <tr>
                        <td><strong>Totale Kilometer:</strong></td>
                        <td>${actualPage.filteredKm}</td>
                    </tr>
                    <tr>
                        <td><strong>Anzahl Fahrten</strong></td>
                        <td>${actualPage.filteredListCount}</td>
                    </tr>

                </table>
            </div>
        </div>

    </div>
    <div class="panel panel-default" id="auswertungen">
        <div class="panel-heading">Liste</div>
        <div class="panel-body">
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>Fahrer</th>
                        <th>Auto</th>
                        <th>Fahrt Start</th>
                        <th>Fahrt Ziel</th>
                        <th>Kilometer</th>
                        <th>Fahrt Typ</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${actualPage.filteredTrips}"
                               var="trip">
                        <tr>
                            <td>${trip.username}</td>
                            <td>${trip.vehicle.brand} ${trip.vehicle.type}</td>
                            <td>${trip.placeStart}</td>
                            <td>${trip.placeEnd}</td>
                            <td>${trip.endKM - trip.startKM}</td>
                            <td>${trip.type}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<br>
<br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
