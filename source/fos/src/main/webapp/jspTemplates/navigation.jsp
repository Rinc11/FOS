<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: retom
  Date: 09.03.2018
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="../startPage.jsp"><img id="logoNav" src="../img/FOS_weiss.png" alt="Fahrzeug Organisations-System" style="width:70px; margin-top:-12px;" /></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <c:if  test="${navSelection=='Startseite'}"> class="active" class="menu" </c:if> ><a href="startseite.html"><strong class="active">Home</strong></a></li>
                <li><a href="fahrt.html" class="menu"><strong>Fahrten</strong></a></li>
                <li><a href="fahrzeug.html" class="menu"><strong>Fahrzeuge</strong></a></li>
                <li><a href="auswertung.html" class="menu"><strong>Auswertung</strong></a></li>
                <li><a href="benutzer.html" class="menu">Muster AG<img id="logoNav" src="../img/pawn.png" alt="Benutzer" style="width:20px; margin-top:-3px; margin-left:15px;" /></a></li>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
    <!--/.container-fluid -->
</nav>