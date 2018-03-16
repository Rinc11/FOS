<%--
Startseite welche nach dem einlogen aufgerufen wird.
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.HomePage" %>
<%@ page import="com.fos.tools.Helper" %>

<%
    Helper.addError(request, "statische Daten");
%>

<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="de">
<head>
    <title>FOS</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Fahrzeug" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
    <div class="title">
        <h1>Fahrzeuge</h1>
        <p>Hier können Sie bereits eingetragene Fahrzeuge einsehen, editieren und neue hinzufügen.</p>
    </div>
    <legend style="color: rgb(64, 99, 180);"><button onclick="toggle()">Filter ein-/ausblenden</button></legend>
    <fieldset id="filteroptionen" style="display: none">
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Fahrzeug</label>
                    <input type="text" class="form-control" placeholder="Fahrzeug">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Fahrer</label>
                    <input type="text" class="form-control" placeholder="Fahrer">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-3">
                <div class="form-group">
                    <label>Datum von</label>
                    <input type="date" class="form-control">
                </div>
            </div>
            <div class="col-md-3">
                <div class="form-group">
                    <label>Datum bis</label>
                    <input type="date" class="form-control">
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Fahrttyp</label>
                    <select class="form-control">
                        <option>geschäftlich</option>
                        <option>privat</option>
                    </select>
                </div>
            </div>
        </div>
    </fieldset>
    <div class="panel panel-default" id="tables">
        <div class="panel-heading">Liste
        </div>
        <div class="panel-body">
            <a class="btn btn-default" href="fahrzeugFormularNeu.html" style="color: rgb(64, 99, 180);">Fahrzeug hinzufügen</a>
            <br><br>
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>Seriennummer</th>
                        <th>Hersteller</th>
                        <th>Typ</th>
                        <th>Baujahr</th>
                        <th>Kraftstoff</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>1234kvee-a</td>
                        <td>Opel</td>
                        <td>Astra</td>
                        <td>2009</td>
                        <td>Diesel</td>
                        <td>
                            <a class="btn btn-default" href="fahrzeugFormularNeu.html"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                    <tr>
                        <td>1234kvee-a</td>
                        <td>Opel</td>
                        <td>Astra</td>
                        <td>2009</td>
                        <td>Diesel</td>
                        <td>
                            <a class="btn btn-default" href="fahrzeugFormularNeu.html"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                    <tr>
                        <td>1234kvee-a</td>
                        <td>Opel</td>
                        <td>Astra</td>
                        <td>2009</td>
                        <td>Diesel</td>
                        <td>
                            <a class="btn btn-default" href="fahrzeugFormularNeu.html"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                    <tr>
                        <td>1234kvee-a</td>
                        <td>Opel</td>
                        <td>Astra</td>
                        <td>2009</td>
                        <td>Diesel</td>
                        <td>
                            <a class="btn btn-default" href="fahrzeugFormularNeu.html"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            <a class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
