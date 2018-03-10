<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:if test="${errorMessage != null}">
    <c:forEach items="${errorMessage}" var="error">
        <p class="errorText">${error}</p>
    </c:forEach>
</c:if>