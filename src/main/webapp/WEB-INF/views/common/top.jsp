<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="app-topbar">
    <div class="d-flex align-center">
        <!-- Breadcrumb placeholder -->
        <span class="text-secondary" style="font-size: 13px;">管理控制台</span>
    </div>
    <div class="d-flex align-center gap-4">
        <div class="d-flex align-center gap-2">
            <div style="width: 32px; height: 32px; background: var(--system-gray5); border-radius: 50%; display: flex; align-items: center; justify-content: center; color: var(--system-blue); font-weight: bold;">
                ${sessionScope.admin.username.substring(0, 1).toUpperCase()}
            </div>
            <div style="font-size: 13px;">
                <div style="font-weight: 600;">${sessionScope.admin.realName}</div>
                <div style="font-size: 11px; color: var(--text-secondary);">${sessionScope.admin.role}</div>
            </div>
        </div>
        <div style="height: 20px; width: 1px; background: var(--border-color);"></div>
        <a href="${pageContext.request.contextPath}/login?action=logout" class="text-danger" style="font-size: 13px;">退出</a>
    </div>
</div>
