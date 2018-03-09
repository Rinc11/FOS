<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.Startseite" %>
<%--
  Created by IntelliJ IDEA.
  User: retom
  Date: 18.02.2018
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%
    if (session.getAttribute("userName") == null) {
        response.sendRedirect("login.jsp");
    }
    Startseite startseite = new Startseite();
    request.setAttribute("page", startseite);
%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FOS</title>
    <jsp:include page="jspTemplates/importHead.jsp"/>
</head>
<body>
<c:set var="navSelection" value="Startseite" scope="request"/><%--setzt eine Variable um die Navigation richtig zu setzen--%>
<jsp:include page="jspTemplates/navigation.jsp"/>
<div class="container">
    <div class="title">
        <h1>Startseite</h1>
        <p>Ihre persönliche Startseite</p>
    </div>
    <p>user: ${userName}</p>
   <%-- eine solche Variable kann in java durch: request.setAttribute("page", startseite);
    oder in jsp durch <c:set var="navSelection" value="Startseite" scope="request"/>
    achtung der scope muss richtig angegeben werden wie weit die variable gültig ist
--%>

    <c:forEach items="${page.items}" var="conf">  <%--hier muss der get anfang von der Methode weggelassen werden--%>
        <p>Id:${conf.id} Wert: ${conf.value}</p>
    </c:forEach>

    <form action="Logout">
        <input type="submit" value="Logout">
    </form>
</div>
<jsp:include page="jspTemplates/footer.jsp"/>
</body>
</html>
