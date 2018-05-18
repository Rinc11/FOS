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
                        <label>Seriennummer</label>
                        <input class="form-control" name="serialnumber" type="text" placeholder="Seriennummer" required>
                    </div>
                    <div class="form-group">
                        <label>Fahrzeugmarke</label>
                        <input class="form-control" name="brand" type="text" placeholder="Tesla" required>
                    </div>
                    <div class="form-group">
                        <label>Model</label>
                        <input class="form-control" name="type" type="text" placeholder="Model S" required>
                    </div>
                    <div class="form-group">
                        <label>Baujahr</label>
                        <input class="form-control" name="buildYear" type="year" placeholder="2018" pattern="[1-9][0-9][0-9][0-9]"
                               title="Bitte geben Sie ein korrektes Jahr ein" required>
                    </div>
                    <div class="form-group">
                        <label>Treibstoff</label>
                        <select class="form-control" name="fuelType">
                            <option>BENZIN</option>
                            <option>DIESEL</option>
                            <option>STROM</option>
                            <option>ERDGAS</option>
                        </select>
                    </div>

                    <input name="command" value="addVehicle" type="hidden">
                    <button type="submit" class="btn btn-default">Speichern</button>
                </fieldset>
            </form>
        </div>
    </div>

</div>
<br><br><br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
