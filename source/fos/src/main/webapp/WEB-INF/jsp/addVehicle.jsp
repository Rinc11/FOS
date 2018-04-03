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
        <h1>Fahrzeuge</h1>
        <p>Hier können Sie einige Änderungen bezüglich Ihren Fahrzeugen vornehmen.</p>
    </div>

    <div class="panel panel-default" id="forms">
        <div class="panel-heading">
            Formular
        </div>
        <div class="panel-body">
            <form action="fahrzeug" method="post">
                <fieldset>
                    <legend>Fahrzeug</legend>
                    <div class="form-group">
                        <label>FahrzeugID</label>
                        <input class="form-control" name="vehicleID" type="number" placeholder="12345678" required>
                    </div>
                    <div class="form-group">
                        <label>Seriennummer</label>
                        <input class="form-control" name="serialnumber" type="text" placeholder="Seriennummer" required>
                    </div>
                    <div class="form-group">
                        <label>Baujahr</label>
                        <input class="form-control" name="buildYear" type="year" placeholder="2018" required>
                    </div>
                    <div class="form-group">
                        <label>Treibstoff</label>
                        <select class="form-control" name="fuelType">
                            <option>Benzin</option>
                            <option>Diesel</option>
                            <option>Strom</option>
                            <option>Erdgas</option>
                        </select></div>
                    <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary"></a>
                        <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary"
                                                                    onclick="toggle('optionalFieldsVehicle')">Optionale
                            Felder
                            <span class="glyphicon glyphicon-plus"></a></legend>
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
                        <input name="command" value="addVehicle" type="hidden">
                        <button type="submit" class="btn btn-default">Senden</button>
                    </legend>
                </fieldset>
            </form>
        </div>
    </div>

</div>
<br><br><br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
