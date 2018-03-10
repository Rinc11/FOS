<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="java.util.Map" %>
<%@ page import="com.fos.login.Login" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%
    Login login = new Login(request, response);
    request.setAttribute("actualPage", login);
%>
<!DOCTYPE html>
<html lang="de">
<head>
    <title>FOS</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
    <link rel="stylesheet" type="text/css" href="css/anmelden.css">
</head>
<body>

<div class="container welcome">
    <div class="jumbotron">
        <img src="img/FOS.png" id="FOSmaske" alt="Fahrzeug Organisations-System"/>
        <div class="account-wall">
            <h2 class="text-center login-title" style="float:left; line-height:1.4;">Herzlich willkommen bei FOS, dem
                Fahrzeug Organisations-System!<br>
                <hr style="border-color:#777; border-width:2px;">
                Melden Sie sich an:
            </h2>
            <jsp:include page="jspTemplates/showErrorMessage.jsp"/>
            <form class="form-signin" action="login.jsp" method="post">
                <input type="text" name="userName" class="form-control" placeholder="Benutzername" required autofocus
                       value="${actualPage.formularUserName}">
                <input type="password" name="pass" class="form-control" placeholder="Passwort" required>
                <button class="btn btn-lg btn-primary btn-block" type="submit">
                    Anmelden
                </button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
