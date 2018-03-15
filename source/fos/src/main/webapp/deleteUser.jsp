<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.fos.UserPage" %>
<%
    UserPage userpage = new UserPage(request, response);
    userpage.removeItem(request.getParameter("username"));
    response.sendRedirect("benutzer.jsp");
%>