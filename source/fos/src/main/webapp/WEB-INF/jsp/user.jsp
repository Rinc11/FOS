<%--
Bentutzerverwaltungseite
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

    <div class="panel panel-default" id="tables">
        <div class="panel-heading">Liste
        </div>
        <div class="panel-body">
            <c:if test="${userLoggedIn.isAdmin}">
            <a class="btn btn-default" href="/benutzerHinzufuegen" style="color: rgb(64, 99, 180);"><span
                    class="glyphicon glyphicon-plus"></span> Benutzer hinzufügen</a>
            <br><br>
            </c:if>
            <div class="scrollme">
                <table class="table table-responsive">
                    <thead>
                    <tr>
                        <th>Benutzername</th>
                        <th>Name</th>
                        <th>Vorname</th>
                        <th>Rechte</th>
                        <c:if test="${userLoggedIn.isAdmin}">
                            <th>gesperrt</th>
                        </c:if>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${userLoggedIn.isAdmin == false}">
                        <tr>
                            <td>${userLoggedIn.userName}</td>
                            <td>${userLoggedIn.lastName}</td>
                            <td>${userLoggedIn.firstName}</td>
                            <td>${userLoggedIn.userType}</td>
                            <td>
                                    <%--@toDo kann was passiert wenn der Benutzername geändert wird, (schäget update fehl?)--%>
                                <a class="btn btn-default"
                                   href="/benutzerAendern?username=${userLoggedIn.userName}"><span
                                        class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                            </td>
                        </tr>
                    </c:if>

                    <c:if test="${userLoggedIn.isAdmin}">
                        <c:forEach items="${actualPage.items}"
                                   var="person">
                            <tr>
                                <td>${person.userName}</td>
                                <td>${person.lastName}</td>
                                <td>${person.firstName}</td>
                                <td>${person.userType}</td>
                                <c:choose>
                                    <c:when test="${person.locked == false}">
                                        <td>nein</td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>ja</td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                        <%--@toDo kann was passiert wenn der Benutzername geändert wird, (schäget update fehl?)--%>
                                    <a class="btn btn-default" href="/benutzerAendern?username=${person.userName}"><span
                                            class="glyphicon glyphicon-pencil" aria-hidden="true"></span></a>
                                    <a onclick="saveDeleteUsername('${person.userName}')" class="btn btn-danger"
                                       data-toggle="modal" data-target="#myModal"> <span
                                            class="glyphicon glyphicon-trash" aria-hidden="true"></span></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Löschen</h4>
            </div>
            <div class="modal-body">
                Wollen sie den Benutzer wirklich löschen
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Nein</button>
                <a class="btn btn-primary" id="deleteUserYesButton" href="#" onclick="deleteUser()">Ja</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
