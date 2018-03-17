<%--
Naviationsleiste oben.
Um das richtige Element zu selektieren muss vor dem aufruf das Element angegeben werden.
z.B. für home: <c:set var="navSelection" value="Startseite" scope="request"/>
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><img src="/jsp/img/FOS_weiss.png"
                                                            alt="Fahrzeug Organisations-System"
                                                            style="width:70px; margin-top:-12px;"/></a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li <c:if test="${navSelection=='Startseite'}"> class="active" </c:if>><a href="/"><strong>Home</strong></a>
                </li>
                <li <c:if test="${navSelection=='Fahrt'}"> class="active" </c:if>><a
                        href="/fahrt"><strong>Fahrten</strong></a></li>
                <li <c:if test="${navSelection=='Fahrzeug'}"> class="active" </c:if>><a href="/fahrzeug"><strong>Fahrzeuge</strong></a>
                </li>
                <li <c:if test="${navSelection=='Auswertung'}"> class="active" </c:if>><a
                        href="/auswertung"><strong>Auswertung</strong></a></li>
                <li <c:if test="${navSelection=='Benutzer'}"> class="active" </c:if>><a href="/benutzer">
                    <strong>
                        <c:choose>
                            <c:when test="${userLoggedIn.userType == 'ADMIN'}">
                                Benutzer
                            </c:when>
                            <c:otherwise>
                                ${userLoggedIn.firstName} ${userLoggedIn.lastName}
                            </c:otherwise>
                        </c:choose>
                    </strong>
                    <img src="/jsp/img/pawn.png" alt="Benutzer"
                         style="width:20px; margin-top:-3px; margin-left:15px;"/></a></li>
                <li><a onclick="logout('${pageContext.request.requestURI}')" href="#" class="menu"><strong>Abmelden</strong></a></li>
            </ul>
        </div>
    </div>
</nav>