<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.Startseite" %>
<%--
  Created by IntelliJ IDEA.
  User: retom
  Date: 18.02.2018
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Startseite</title>
</head>
<body>
<%
    if(session.getAttribute("userName") == null){
        response.sendRedirect("login.jsp");
    }
    Startseite startseite = new Startseite();

    request.setAttribute("page", startseite);
%>
<h1>Startseite</h1>
<p>user: ${userName}</p>

<c:forEach items="${page.items}" var="conf">  <%--hier muss der get anfang von der Methode weggelassen werden--%>
    <p>Id:${conf.id} Wert: ${conf.value}</p>
</c:forEach>

<form action="Logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
