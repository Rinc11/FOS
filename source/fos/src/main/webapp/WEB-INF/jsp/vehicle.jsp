<%--
Bentutzerverwaltungseite
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <title>Fahrzeugverwaltung</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Fahrzeug" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
    <div class="title">
        <h1>Fahrzeuge</h1>
        <p>Hier können Sie einige Änderungen bezüglich Ihren Fahrzeugen vornehmen.</p>
    </div>


    <legend style="color: rgb(64, 99, 180);">
        <button onclick="toggle('filteroptionen')">Filter ein-/ausblenden</button>
    </legend>
    <fieldset id="filteroptionen">
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label>ID</label>
                    <input type="number" class="form-control" placeholder="12345678">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Seriennummer</label>
                    <input type="text" class="form-control" placeholder="Seriennummer">
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Baujahr</label>
                    <input type="number" class="form-control" placeholder="2018">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Treibstoff</label>
                    <select class="form-control" name="fuelType">
                        <option>Benzin</option>
                        <option>Diesel</option>
                        <option>Strom</option>
                        <option>Erdgas</option>
                    </select>
                </div>
            </div>
        </div>


    </fieldset>
    <div class="panel panel-default" id="tables">
        <div class="panel-heading">Liste
        </div>
        <div class="panel-body">
            <c:if test="${userLoggedIn.isAdmin}">
                <a class="btn btn-default" href="/fahrzeugHinzufuegen" style="color: rgb(64, 99, 180);"><span
                        class="glyphicon glyphicon-plus"></span> Fahrzeug hinzufügen</a>
                <br><br>
            </c:if>
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>FahrzeugID</th>
                        <th>Seriennummer</th>
                        <th>Baujahr</th>
                        <th>Treibstoff</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${userLoggedIn.isAdmin == false}">
                        <tr>
                            <td>${userLoggedIn.vehicleID}</td>
                            <td>${userLoggedIn.serialnumber}</td>
                            <td>${userLoggedIn.buildYear}</td>
                            <td>${userLoggedIn.fuelType}</td>
                            <td>
                                    <%--@toDo kann was passiert wenn die VehicleID geändert wird, (schäget update fehl?)--%>
                                <a class="btn btn-default"
                                   href="/fahrzeugAendern?vehicleID=${vehicleLoggedIn.vehicleID}"><span
                                        class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${userLoggedIn.isAdmin}">
                        <c:forEach items="${actualPage.items}"
                                   var="vehicle">
                            <tr>
                                <td>${vehicle.vehicleID}</td>
                                <td>${vehicle.serialnumber}</td>
                                <td>${vehicle.buildYear}</td>
                                <td>${vehicle.fuelType}</td>
                                <c:choose>
                                    <c:when test="${person.locked == false}">
                                        <td>nein</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>ja</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                        <%--@toDo kann was passiert wenn die VehicleID geändert wird, (schäget update fehl?)--%>
                                    <a class="btn btn-default"
                                       href="/fahrzeugAendern?vehicleID=${vehicle.vehicleID}"><span
                                            class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                                    <a onclick="saveDeleteVehicleID('${vehicle.vehicleID}')" class="btn btn-danger"
                                       data-toggle="modal" data-target="#myModal"> <span
                                            class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Löschen</h4>
            </div>
            <div class="modal-body">
                Wollen sie das Fahrzeug wirklich löschen
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Nein</button>
                <a class="btn btn-primary" id="deleteVehicleYesButton" href="#" onclick="deleteVehicle()">Ja</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>