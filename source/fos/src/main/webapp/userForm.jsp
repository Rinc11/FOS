<%--
Formular für einen neuen Benutzer oder zum einen Benutzer ändern
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.user.UserPage" %>
<%
    UserPage userpage = new UserPage(request, response);
    request.setAttribute("actualPage", userpage);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="de">
<head>
    <title>Benutzer-Formular</title>
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

    <div class="panel panel-default" id="forms">
        <div class="panel-heading">
            Formular
        </div>
        <div class="panel-body">
            <form action="fahrzeug.html">
                <fieldset>
                    <legend>Benutzer</legend>
                    <div class="form-group">
                        <label>Benutzername</label>
                        <input type="text" class="form-control" placeholder="Benutzername" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort</label>
                        <input type="password" class="form-control" placeholder="Passwort" id="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Ihr Passwort muss mind. 8 Zeichen lang sein, eine Zahl und einen Grossbuchstaben beinhalten" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort bestätigen</label>
                        <input type="password" class="form-control" placeholder="Passwort bestätigen" id="password_confirm" required>
                    </div>
                    <div class="form-group">
                        <label>Vorname</label>
                        <input type="text" class="form-control" placeholder="Vorname" required>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input type="text" class="form-control" placeholder="Name" required>
                    </div>
                    <div class="form-group">
                        <label>AHV</label>
                        <input type="text" class="form-control" placeholder="AHV">
                    </div>
                    <div class="form-group">
                        <label>Strasse</label>
                        <input type="text" class="form-control" placeholder="Strasse">
                    </div>
                    <div class="form-group">
                        <label>Ort</label>
                        <input type="text" class="form-control" placeholder="Ort">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input type="email" class="form-control" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label>Passwort Hinweis</label>
                        <input type="text" class="form-control" placeholder="Passwort Hinweis">
                    </div>
                    <div class="form-group">
                        <label>Rechte</label>
                        <select class="form-control">
                            <option>ADMIN</option>
                            <option>MITARBEITER</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-default">Senden</button>
                </fieldset>
            </form>
        </div>
    </div>

</div>

<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
