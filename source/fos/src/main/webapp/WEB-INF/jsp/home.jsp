<%@ page import="com.fos.page.HomePage" %>
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
<c:set var="navSelection" value="Startseite" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>

    <div class="title">
        <h1>Startseite</h1>
        <p>Ihre persönliche Startseite</p>
    </div>
    <div class="row">
        <c:if test="${vehicle == null}">
            <div class="col-lg-12">
                <div class="panel panel-default" id="formStart">
                    <div class="panel-heading">Formular
                    </div>
                    <div class="panel-body">
                        <form action="/fahrt" method="post">
                            <fieldset>
                                <legend>Fahrzeug wählen</legend>
                                <div class="form-group">
                                    <label>Fahrzeug</label>
                                    <select class="form-control" name="tripVehicle" required="true"
                                            onload="setLastTripData(this);" onchange="lastTripData(this);">
                                        <c:forEach var="vehicle" items="${actualPage.vehiclesToChoose}">
                                            <option value="${vehicle.vehicleID}">${vehicle.brand} ${vehicle.type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <input type="hidden" name="command" value="saveVehicle">
                                <button type="submit" class="btn btn-default">Wählen</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${userLoggedIn.isAdmin == false}">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Auswertung
                    </div>
                    <div class="panel-body">
                        <form action="/auswertung" method="post">
                            <fieldset>
                                <legend>Persönliche Auwertung</legend>
                                <table>
                                    <tr>
                                        <td style="padding-right: 15px">private Kilometer:</td>
                                        <td>${actualPage.personalKmPrivate}km</td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 15px">gerschäftliche Kilometer:</td>
                                        <td>${actualPage.personalKmBusiness}km</td>
                                    </tr>
                                </table>
                                <input type="hidden" name="command" value="personalStatistic">
                                <br>
                                <button type="submit" class="btn btn-default">zur Auswertung</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${userLoggedIn.isAdmin}">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Auswertung
                    </div>
                    <div class="panel-body">
                        <form action="/auswertung" method="post">
                            <fieldset>
                                <legend>Firmafahrten Auwertung</legend>
                                <table>
                                    <tr>
                                        <td style="padding-right: 15px">private Firmen-Kilometer:</td>
                                        <td>${actualPage.companyKmPrivate}km</td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 15px">geschäftliche Firmen-Kilometer:</td>
                                        <td>${actualPage.companyKmBusiness}km</td>
                                    </tr>
                                </table>
                                <input type="hidden" name="command" value="CompanyStatistic">
                                <br>
                                <button type="submit" class="btn btn-default">zur Auswertung</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${userLoggedIn.isAdmin}">
            <div class="col-lg-6">
                <div class="panel panel-default">
                    <div class="panel-heading">Auswertung
                    </div>
                    <div class="panel-body">
                        <form action="/benutzer" method="post">
                            <fieldset>
                                <legend>Benutzerverwaltung</legend>
                                <table>
                                    <tr>
                                        <td style="padding-right: 15px">gesperrte Benutzer:</td>
                                        <td>${actualPage.lockedUserCount}</td>
                                    </tr>
                                </table>
                                <br>
                                <br>
                                <button type="submit" class="btn btn-default">zur Benutzerverwaltung</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
<br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
