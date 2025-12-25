<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Hidden elements for UI.js to pick up and show as toasts --%>
<c:if test="${not empty error}">
    <div class="alert-server-msg" data-type="error" data-msg="${error}"></div>
</c:if>

<c:if test="${not empty message}">
    <div class="alert-server-msg" data-type="success" data-msg="${message}"></div>
</c:if>

<c:if test="${not empty warning}">
    <div class="alert-server-msg" data-type="warning" data-msg="${warning}"></div>
</c:if>
