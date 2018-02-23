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
%>
<h1>Startseite</h1>
<p>user: ${userName}</p>

<form action="Logout">
    <input type="submit" value="Logout">
</form>
</body>
</html>
