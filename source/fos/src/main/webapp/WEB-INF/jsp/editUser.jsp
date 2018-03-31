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
            <c:set var="person" value="${actualPage.requestPerson}"/>
            <form action="/benutzer" method="post">
                <fieldset>
                    <legend>Benutzer</legend>
                    <div class="form-group">
                        <label>Benutzername</label>
                        <input class="form-control" readonly name="username" type="text" placeholder="Benutzername" value="${person.userName}" required>
                    </div>
                    <div class="form-group">
                        <label>Passwort ändern</label>
                        <div class="input-group">
                        <input class="form-control" id="pwd" name="password" type="password" placeholder="Passwort ändern"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Ihr Passwort muss mind. 8 Zeichen lang sein, eine Zahl und einen Grossbuchstaben beinhalten" >
                            <span class="input-group-btn">
                                 <a class="btn btn-default btn-md form-control" id="showhide" data-val='1'><span id='eye' class="glyphicon glyphicon-eye-open" style="color: rgb(64, 99, 180)"></span></a>
                            </span>
                            <span class="input-group-btn">
                                 <a class="btn btn-default btn-md form-control" id="generatePW" onclick="password_generator('pwd', 'password_confirm');">automatisches Passwort</a>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Passwort bestätigen</label>
                        <input class="form-control" name="passwordConfirm" type="password" placeholder="Passwort bestätigen" id="password_confirm" >
                    </div>
                    <div class="form-group">
                        <label>Vorname</label>
                        <input class="form-control" name="firstname" type="text" placeholder="Vorname" value="${person.firstName}"required>
                    </div>
                    <div class="form-group">
                        <label>Name</label>
                        <input class="form-control" name="lastname" type="text" placeholder="Name" value="${person.lastName}"required>
                    </div>
                    <legend style="color: rgb(64, 99, 180);"><a class="btn btn-sm btn-primary" onclick="toggle('optionalFieldsUser')">Optionale Felder <span class="glyphicon glyphicon-plus"></a></legend>
                    <fieldset id="optionalFieldsUser" style="display: none">
                        <div class="form-group">
                            <label>AHV</label>
                            <input class="form-control" name="ahv" type="text" placeholder="AHV" value="${person.ahv}">
                        </div>
                        <div class="form-group">
                            <label>Strasse</label>
                            <input class="form-control" name="street" type="text" placeholder="Strasse" value="${person.street}">
                        </div>
                        <div class="form-group">
                            <label>Ort</label>
                            <input class="form-control" name="place" type="text" placeholder="Ort" value="${person.place}">
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input class="form-control" name="email" type="email" placeholder="Email" value="${person.email}">
                        </div>
                        <div class="form-group">
                            <label>Passwort Hinweis</label>
                            <input class="form-control" name="passwordHint" type="text" placeholder="Passwort Hinweis" value="${person.passwordHint}">
                        </div>
                        <div class="form-group">
                            <label>Benutzer sperren</label>
                            <input class="form-control" name="locked" <c:if test="${person.locked}"> checked="checked" </c:if>  type="checkbox"  >
                        </div>
                    </fieldset>
                    <div class="form-group">
                        <label>Rechte</label>
                        <select class="form-control" name="usertype">
                            <option <c:if test="${person.userType == 'ADMIN'}">selected</c:if> >Admin</option>
                            <option <c:if test="${person.userType == 'MITARBEITER'}">selected</c:if>>Mitarbeiter</option>
                        </select>
                    </div>
                    <input name="command" value="editUser" type="hidden">
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
