<%--
Formular für ein neues Fahrzeug oder zum ein Fahrzeug zu ändern
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <title>Fahrzeug-Formular</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Fahrzeug" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
    <div class="title">
        <h1>Fahrzeug</h1>
        <p>Hier können Sie einige Änderungen bezüglich Ihren Fahrzeugen vornehmen.</p>
    </div>

    <div class="panel panel-default" id="forms">
        <div class="panel-heading">
            Formular
        </div>
        <div class="panel-body">
            <c:set var="vehicle" value="${actualPage.requestVehicle}"/>
            <form action="/fahrzeug" method="post">
                <fieldset>
                    <legend>Fahrzeug</legend>
                    <div class="form-group">
                        <label>FahrzeugID</label>
                        <input class="form-control" readonly name="vehicleID" type="number" placeholder="12345678"
                               value="${vehicle.vehicleID}" required>
                    </div>
                    <div class="form-group">
                        <label>Seriennummer</label>
                        <input class="form-control" name="serialnumber" type="text" placeholder="Seriennummer"
                               value="${vehicle.serialnumber}" required>
                    </div>
                    <div class="form-group">
                        <label>Baujahr</label>
                        <input class="form-control" name="buildYear" type="year" placeholder="2018"
                               value="${vehicle.buildYear}" required>
                    </div>
                    <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary"
                                                                onclick="toggle('optionalFieldsVehicle')">Optionale
                        Felder
                        </span class="glyphicon glyphicon-plus"></a></legend>
                    <fieldset id="optionalFieldsVehicle" style="display: none">
                        <div class="form-group">
                            <label>Marke</label>
                            <input class="form-control" name="brand" type="text" placeholder="Tesla">
                        </div>
                        <div class="form-group">
                            <label>Typ</label>
                            <input class="form-control" name="type" type="text" placeholder="Limousine">
                        </div>
                    </fieldset>
                    <c:if test="${userLoggedIn.isAdmin}">
                        <div class="form-group">
                            <label>Treibstoff</label>
                            <select class="form-control" name="fuelType">
                                <option
                                        <c:if test="${vehicle.fuelType == 'BENZIN'}">selected</c:if>>Benzin
                                </option>
                                <option
                                        <c:if test="${vehicle.fuelType == 'DIESEL'}">selected</c:if>>Diesel
                                </option>
                                <option
                                        <c:if test="${vehicle.fuelType == 'STROM'}">selected</c:if>>Strom
                                </option>
                                <option
                                        <c:if test="${vehicle.fuelType == 'ERDGAS'}">selected</c:if>>Erdgas
                                </option>
                            </select>
                        </div>
                    </c:if>
                    <input name="command" value="editVehicle" type="hidden">
                    <button type="submit" class="btn btn-default">Senden</button>
                </fieldset>
            </form>
        </div>
    </div>

</div>
<br><br><br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
