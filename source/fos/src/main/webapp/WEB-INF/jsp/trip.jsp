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
<c:set var="navSelection" value="Fahrt" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>

    <div class="title">
        <h1>Fahrten</h1>
        <p>Hier können Sie bereits eingetragene Fahrten einsehen und editieren.</p>
    </div>

    <c:if test="${vehicle == null}">
        <div class="panel panel-default" id="formStart">
            <div class="panel-heading">Formular
            </div>
            <div class="panel-body">
                <form action="/fahrt" method="post">
                    <fieldset>
                        <legend>Fahrzeug wählen</legend>
                        <div class="form-group">
                            <label>Fahrzeug</label>
                            <select class="form-control" name="tripVehicle" required="true" onload="setLastTripData(this);" onchange="lastTripData(this);">
                                <c:forEach var="vehicle" items="${actualPage.vehiclesToChoose}">
                                    <option value="${vehicle.vehicleID}">${vehicle.brand} ${vehicle.type}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="hidden" name="command" value="saveVehicle">
                        <button type="submit" class="btn btn-default">Senden</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </c:if>
    <c:if test="${actualPage.hasOpenTrip == false && vehicle != null}">

            <div class="panel panel-default" id="formStart">
                <div class="panel-heading">Formular
                </div>
                <div class="panel-body">
                    <form action="/fahrt" method="post">
                        <fieldset>
                            <legend>Fahrt starten</legend>
                            <div class="form-group">
                                <label>Fahrzeug</label>
                                <p>Sie haben folgendes Fahrzeug gewählt: <b>${actualPage.vehicle.brand} ${actualPage.vehicle.type}</b></p>
                            </div>
                            <div class="form-group">
                                <label>Ortschaft</label>
                                <input id="kmdfasdsf" type="text" id="startPlace" name="placeStart" class="form-control" required="true"
                                       placeholder="Ortschaft" value="${actualPage.getLastTripByVehicle(vehicle).placeEnd}">
                            </div>
                            <div class="form-group">
                                <label>Kilometerstand</label>
                                <input  type="number" name="startKM" class="form-control" required="true" min="0" placeholder="Kilometerstand" value="${actualPage.getLastTripByVehicle(vehicle).endKM}">
                            </div>
                            <div class="form-group">
                                <label>Fahrttyp</label>
                                <select class="form-control" name="type">
                                    <option selected>GESCHÄFTLICH</option>
                                    <option>PRIVAT</option>
                                </select>
                            </div>
                            <input type="hidden" name="command" value="startTrip">
                            <button type="submit" class="btn btn-default">Senden</button>
                        </fieldset>
                    </form>
                </div>
            </div>

    </c:if>
    <c:if test="${actualPage.hasOpenTrip}">

            <div class="panel panel-default" id="formStop">
                <div class="panel-heading">Formular
                </div>
                <div class="panel-body">
                    <form>
                        <fieldset>
                            <legend>Fahrt stoppen</legend>
                            <div class="form-group">
                                <label>Ortschaft</label>
                                <input type="text" required="true" name="place" class="form-control"
                                       placeholder="Ortschaft">
                            </div>
                            <div class="form-group">
                                <label>Kilometerstand</label>
                                <input type="number" name="kmEnd" min="${actualPage.openTrip.startKM}" required="true" class="form-control"
                                       placeholder="Kilometerstand">
                            </div>
                            <input type="hidden" name="command" value="stopTrip">
                            <button type="submit" class="btn btn-default">Senden</button>
                        </fieldset>
                    </form>
                </div>
            </div>

    </c:if>
    <div class="panel panel-default" id="tables">
        <div class="panel-heading">Liste
        </div>
        <div class="panel-body">
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>Fahrtnr.</th>
                        <th>Auto</th>
                        <th>Start</th>
                        <th>Stopp</th>
                        <th>Start km</th>
                        <th>Stopp km</th>
                        <th>Fahrttyp</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${actualPage.items}"
                               var="trip">
                        <tr>
                            <td>${trip.tripID}</td>
                            <td>${trip.vehicle.brand} ${trip.vehicle.type}</td>
                            <td>${trip.placeStart}</td>
                            <td>${trip.placeEnd}</td>
                            <td>${trip.startKM}</td>
                            <td>${trip.endKM}</td>
                            <td>${trip.type}</td>
                            <td>
                                <a class="btn btn-default" href="/fahrtAendern?tripID=${trip.tripID}"><span
                                        class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
