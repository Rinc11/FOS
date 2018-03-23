<%@ page import="com.fos.UserPage" %><%--
Formular für einen neuen Benutzer oder zum einen Benutzer ändern
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <form action="addUser" method="post">
                <fieldset>
                    <legend>Benutzer</legend>
                    <div class="form-group">
                        <label>Benutzername</label>
                        <input class="form-control" name="username" type="text" placeholder="Benutzername" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort</label>
                        <input class="form-control" name="password" type="password" placeholder="Passwort" id="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Ihr Passwort muss mind. 8 Zeichen lang sein, eine Zahl und einen Grossbuchstaben beinhalten" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort bestätigen</label>
                        <input class="form-control" name="passwordConfirm" type="password" placeholder="Passwort bestätigen" id="password_confirm" required>
                    </div>
                    <div class="form-group">
                        <label>Vorname</label>
                        <input class="form-control" name="firstname" type="text" placeholder="Vorname" required>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input class="form-control" name="lastname" type="text" placeholder="Name" required>
                    </div>
                    <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary" onclick="toggle('optionalFieldsUser')">Optionale Felder <span class="glyphicon glyphicon-plus"></a></legend>
                    <fieldset id="optionalFieldsUser" style="display: none">
                    <div class="form-group">
                        <label>AHV</label>
                        <input class="form-control" name="ahv" type="text" placeholder="AHV">
                    </div>
                    <div class="form-group">
                        <label>Strasse</label>
                        <input class="form-control" name="street" type="text" placeholder="Strasse">
                    </div>
                    <div class="form-group">
                        <label>Ort</label>
                        <input class="form-control" name="place" type="text" placeholder="Ort">
                    </div>
                    <div class="form-group">
                        <label>Email</label>
                        <input class="form-control" name="email" type="email" placeholder="Email">
                    </div>
                    <div class="form-group">
                        <label>Passwort Hinweis</label>
                        <input class="form-control" name="passwordHint" type="text" placeholder="Passwort Hinweis">
                    </div>
                    </fieldset>
                    <div class="form-group">
                        <label>Rechte</label>
                        <select class="form-control" name="usertype">
                            <option>Admin</option>
                            <option>Mitarbeiter</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-default">Senden</button>
                </fieldset>
            </form>
        </div>
    </div>

</div>
<br><br><br>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
