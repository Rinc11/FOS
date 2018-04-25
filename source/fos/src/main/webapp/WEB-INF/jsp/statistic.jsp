<%@ page import="com.fos.HomePage" %>
<%@ page import="com.fos.database.Person" %>
<%@ page import="com.fos.StatisticPage" %>
<%--
Auswertung
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
    <form action="auswertung" method="get">
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
                <div class="col-md-3">
                    <label> </label>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default">Filtern</button>
                    </div>
                </div>
            </div>
        </fieldset>
    </form>
        <div class="panel panel-default" id="auswertungen">
            <div class="panel-heading">Auswertung</div>
            <table class="table table-responsive">
                <p>design anpassen</p>
                <tr>
                    <td>Totale Kilometer:</td>
                    <td>${actualPage.filteredKm}</td>
                </tr>
                <tr>
                    <td>Anzahl Fahrten</td>
                    <td>${actualPage.filteredListCount}</td>
                </tr>
            </table>
        </div>
    <div class="panel panel-default" id="auswertungen">
        <div class="panel-heading">Liste</div>

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
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
