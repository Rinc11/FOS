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
