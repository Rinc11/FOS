<%--
Bentutzerverwaltungseite
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.UserPage" %>
<%
    UserPage userpage = new UserPage(request, response);
    request.setAttribute("actualPage", userpage);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <title>Benutzerverwaltung</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Benutzer" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
    <div class="title">
        <h1>Benutzer</h1>
        <p>Hier können Sie einige Änderungen bezüglich Ihres Kontos vornehmen.</p>
    </div>


    <legend style="color: rgb(64, 99, 180);"><button onclick="toggle()">Filter ein-/ausblenden</button></legend>
    <fieldset id="filteroptionen">
        <div class="row">
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" placeholder="Name">
                </div>
            </div>
            <div class="col-lg-6">
                <div class="form-group">
                    <label>Vorname</label>
                    <input type="text" class="form-control" placeholder="Vorname">
                </div>
            </div>
        </div>


    </fieldset>
    <div class="panel panel-default" id="tables">
        <div class="panel-heading">Liste
        </div>
        <div class="panel-body">
            <a class="btn btn-default" href="benutzerFormularNeu.html" style="color: rgb(64, 99, 180);">Benutzer hinzufügen</a>
            <br><br>
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>Benutzername</th>
                        <th>Name</th>
                        <th>Vorname</th>
                        <th>Rechte</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${actualPage.items}"
                               var="person">
                        <tr>
                            <td>${person.userName}</td>
                            <td>${person.lastName}</td>
                            <td>${person.firstName}</td>
                            <td>${person.userType}</td>
                            <td>
                                <a class="btn btn-default" href="benutzerFormularNeu.html"> <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                                <a class="btn btn-default"> <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
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
