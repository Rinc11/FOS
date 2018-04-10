<%--
Formular für einen neuen Benutzer oder zum einen Benutzer ändern
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
            <form action="benutzer" method="post">
                <fieldset>
                    <legend>Benutzer</legend>
                    <div class="form-group">
                        <label>Benutzername</label>
                        <input class="form-control" name="username" type="text" placeholder="Benutzername" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort</label>
                        <div class="input-group">
                            <input class="form-control" id="pwd" name="password" type="password" placeholder="Passwort"
                                   onchange="validatePassword()"
                                   pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" required
                                   title="Ihr Passwort muss mind. 8 Zeichen lang sein, eine Zahl und einen Grossbuchstaben beinhalten">
                            <span class="input-group-btn">
                                 <a class="btn btn-default btn-md form-control" id="showhide" data-val='1'><span
                                         id='eye' class="glyphicon glyphicon-eye-open"
                                         style="color: rgb(64, 99, 180)"></span></a>
                            </span>
                            <span class="input-group-btn">
                                 <a class="btn btn-default btn-md form-control" id="generatePW"
                                    onclick="password_generator('pwd', 'password_confirm');">automatisches Passwort</a>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Passwort bestätigen</label>
                        <input class="form-control" name="passwordConfirm" type="password" onchange="validatePassword()"
                               placeholder="Passwort bestätigen" id="password_confirm" required>
                    </div>
                    <div class="form-group">
                        <label>Vorname</label>
                        <input class="form-control" name="firstname" type="text" placeholder="Vorname" required>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input class="form-control" name="lastname" type="text" placeholder="Name" required>
                    </div>
                    <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary"
                                                                onclick="toggle('optionalFieldsUser')">Optionale Felder
                        <span class="glyphicon glyphicon-plus"></span></a></legend>
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
                    <input name="command" value="addUser" type="hidden">
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
