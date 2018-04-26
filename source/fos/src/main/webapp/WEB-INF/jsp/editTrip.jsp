<%--
Formular zum eine Fahrt abändern
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <title>Fahrt-Formular</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Fahrten" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
    <div class="title">
        <h1>Fahrt</h1>
        <p>Hier können Sie einige Änderungen bezüglich Ihren Fahrten vornehmen.</p>
    </div>

    <div class="panel panel-default" id="forms">
        <div class="panel-heading">
            Formular
        </div>
        <div class="panel-body">
            <c:set var="trip" value="${actualPage.requestTrip}"/>
            <form action="/fahrt" method="post">
                <fieldset>
                    <legend>Fahrt</legend>

                    <div class="form-group">
                        <label>Start-Ort</label>
                        <input class="form-control" name="placeStart" type="text" placeholder="Seriennummer"
                               value="${trip.placeStart}" required>
                    </div>
                    <div class="form-group">
                        <label>Stopp-Ort</label>
                        <input class="form-control" name="placeEnd" type="text" placeholder="Tesla"
                               value="${trip.placeEnd}" required>
                    </div>
                    <div class="form-group">
                        <label>Kilometerstand vor der Fahrt</label>
                        <input class="form-control" name="startKM" type="number"  placeholder="Model S"
                               value="${trip.startKM}" required>
                    </div>
                    <div class="form-group">
                        <label>Kilometerstand nach der Fahrt</label>
                        <input class="form-control" name="endKM" type="number" placeholder="2018"
                               value="${trip.endKM}" required>
                    </div>
                    <div class="form-group">
                        <label>Typ</label>
                        <select class="form-control" name="type">
                            <option value="GESCHÄFTLICH"
                                    <c:if test="${trip.type == 'GESCHÄFTLICH'}">selected</c:if>>GESCHÄFTLICH
                            </option>
                            <option value="PRIVAT"
                                    <c:if test="${trip.type == 'PRIVAT'}">selected</c:if>>PRIVAT
                            </option>
                        </select>
                    </div>
                    <input name="tripID" type="hidden" value="${trip.tripID}" required>
                    <input name="command" value="editTrip" type="hidden">
                    <button type="submit" class="btn btn-default">Ändern</button>
                </fieldset>
            </form>
        </div>
    </div>

</div>
<br><br><br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
