<%--
Fahrzeugverwaltungseite
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
                        <th>Seriennummer</th>
                        <th>Fahrzeugmarke</th>
                        <th>Modell</th>
                        <th>Baujahr</th>
                        <th>Treibstoff</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${actualPage.items}"
                               var="vehicle">
                        <tr>
                            <td>${vehicle.serialnumber}</td>
                            <td>${vehicle.brand}</td>
                            <td>${vehicle.type}</td>
                            <td>${vehicle.buildYear}</td>
                            <td>${vehicle.fuelType}</td>
                            <c:if test="${userLoggedIn.isAdmin}">
                                <td>
                                        <%--@toDo kann was passiert wenn die VehicleID geändert wird, (schäget update fehl?)--%>
                                    <a class="btn btn-default"
                                       href="/fahrzeugAendern?vehicleID=${vehicle.vehicleID}"><span
                                            class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                                    <a onclick="saveDeleteVehicle('${vehicle.vehicleID}')" class="btn btn-danger"
                                       data-toggle="modal" data-target="#myModal"> <span
                                            class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Löschabfrage für Fahrzeug  -->
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