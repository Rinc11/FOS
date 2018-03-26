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
<c:set var="navSelection" value="Startseite" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>

    <div class="title">
        <h1>Startseite</h1>
        <p>Ihre persönliche Startseite</p>
    </div>
    <div class="row">
        <c:if test="${actualPage.hasOpenTrip == false}">
            <div class="col-lg-6">
                <div class="panel panel-default" id="formStart">
                    <div class="panel-heading">Formular
                    </div>
                    <div class="panel-body">
                        <form action="/" method="post">
                            <fieldset>
                                <legend>Fahrt starten</legend>
                                <div class="form-group">
                                    <label>Fahrzeug</label>
                                    <select class="form-control" name="tripVehicle" required="true">
                                        <c:forEach var="vehicle" items="${actualPage.vehiclesToChoose}">
                                            <option value="${vehicle.vehicleID}">${vehicle.brand} ${vehicle.type}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Ortschaft</label>
                                    <input type="text" name="place" class="form-control" required="true"
                                           placeholder="Ortschaft">
                                </div>
                                <div class="form-group">
                                    <label>Kilometerstand</label>
                                    <input type="number" name="kmMilage" class="form-control" required="true" min="0"
                                           placeholder="Kilometerstand">
                                </div>
                                <div class="form-group">
                                    <label>Fahrttyp</label>
                                    <select class="form-control" name="tripType">
                                        <option selected>geschäftlich</option>
                                        <option>privat</option>
                                    </select>
                                </div>
                                <input type="hidden" name="command" value="startTrip">
                                <button type="submit" class="btn btn-default">Senden</button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${actualPage.hasOpenTrip}">
            <div class="col-lg-6">
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
                                    <input type="number" name="kmMilage" min="0" required="true" class="form-control"
                                           placeholder="Kilometerstand">
                                </div>
                                <input type="hidden" name="command" value="stopTrip">
                                <button type="submit" class="btn btn-default">Senden</button>
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
                        <form action="/Auswertung" method="post">
                            <fieldset>
                                <legend>Persönliche Auwertung</legend>
                                <table>
                                    <tr>
                                        <td style="padding-right: 15px">private Kilometer:  </td>
                                        <td>${actualPage.personalKmPrivate}km</td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 15px">gerschäftliche Kilometer:  </td>
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
                        <form action="/Auswertung" method="post">
                            <fieldset>
                                <legend>Firmafahrten Auwertung</legend>
                                <table>
                                    <tr>
                                        <td style="padding-right: 15px">private Firmen-Kilometer:</td>
                                        <td>${actualPage.companyKmPrivate}km</td>
                                    </tr>
                                    <tr>
                                        <td style="padding-right: 15px">gerschäftliche Firmen-Kilometer:</td>
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
                                        <td style="padding-right: 15px">gesperte Benutzer:</td>
                                        <td>${actualPage.lockedUserCount}</td>
                                    </tr>
                                </table>
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
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
