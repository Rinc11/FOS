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

        <div class="scrollme">
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>Fahrtnr.</th>
                    <th>Auto</th>
                    <th>Start</th>
                    <th>Stopp</th>
                    <th>Anzahl Kilometer</th>
                    <th>Fahrttyp</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>Fiat</td>
                    <td>Frauenfeld</td>
                    <td>Winterthur</td>
                    <td>15</td>
                    <td>geschäftlich</td>
                    <td>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                    </td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Smart</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                    <td>geschäftlich</td>
                    <td>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                    </td>
                </tr>
                <tr>
                    <td>3</td>
                    <td>VW</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                    <td>privat</td>
                    <td>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                    </td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>Audi</td>
                    <td>Romanshorn</td>
                    <td>Zürich</td>
                    <td>40</td>
                    <td>geschäftlich</td>
                    <td>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></button>
                        <button type="button" class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
