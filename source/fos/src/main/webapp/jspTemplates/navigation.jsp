<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="../startPage.jsp"><img src="../img/FOS_weiss.png" alt="Fahrzeug Organisations-System" style="width:70px; margin-top:-12px;" /></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <c:if  test="${navSelection=='Startseite'}"> class="active" </c:if>><a href="../home.jsp"><strong>Home</strong></a></li>
                <li <c:if  test="${navSelection=='Fahrt'}"> class="active" </c:if>><a href="../fahrt.jsp"><strong>Fahrten</strong></a></li>
                <li <c:if  test="${navSelection=='Fahrzeug'}"> class="active" </c:if>><a href="../fahrzeug.jsp"><strong>Fahrzeuge</strong></a></li>
                <li <c:if  test="${navSelection=='Auswertung'}"> class="active" </c:if>><a href="../auswertung.jsp"><strong>Auswertung</strong></a></li>
                <li <c:if  test="${navSelection=='Benutzer'}"> class="active" </c:if>><a href="../benutzer.jsp"><strong>Muster AG</strong>
                    <img src="../img/pawn.png" alt="Benutzer" style="width:20px; margin-top:-3px; margin-left:15px; margin-right:0px;" /></a></li>
                <li><a href="Logout" class="menu" style="margin-left:-15px;"><strong>Abmelden</strong></a></li>
            </ul>
        </div>
    </div>
</nav>