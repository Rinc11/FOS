<%--
Ermöglicht es Fehlermeldungen vom Code dem Benutzer anzuzeigen.
Um solche Fehlermeldungen zu erfassen gibt es in Helper.addError
 oder auch bei FosPage.addError(die Klasse die das aufruft wird wahrscheindlich von der Ableiten).
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${errorMessage != null}">
    <c:forEach items="${errorMessage}" var="error">
        <p class="errorText">${error}</p>
    </c:forEach>
</c:if>