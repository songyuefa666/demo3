<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="app-sidebar">
    <div class="sidebar-header">
        <span style="color: var(--system-blue);"></span>&nbsp;图书管理系统
    </div>
    <nav class="sidebar-menu">
        <div style="margin-bottom: 8px; padding: 0 12px; font-size: 11px; color: var(--text-secondary); text-transform: uppercase; letter-spacing: 0.5px;">Main</div>
        
        <a href="${pageContext.request.contextPath}/home" class="menu-item ${param.active == 'home' ? 'active' : ''}">
            首页概览
        </a>
        <a href="${pageContext.request.contextPath}/borrow?action=index" class="menu-item ${param.active == 'borrow' ? 'active' : ''}">
            图书借阅
        </a>
        
        <div style="margin-top: 24px; margin-bottom: 8px; padding: 0 12px; font-size: 11px; color: var(--text-secondary); text-transform: uppercase; letter-spacing: 0.5px;">Management</div>
        
        <a href="${pageContext.request.contextPath}/book/list" class="menu-item ${param.active == 'book' ? 'active' : ''}">
            图书档案
        </a>
        <a href="${pageContext.request.contextPath}/reader/list" class="menu-item ${param.active == 'reader' ? 'active' : ''}">
            读者档案
        </a>
        <a href="${pageContext.request.contextPath}/book/bookshelf" class="menu-item ${param.active == 'bookshelf' ? 'active' : ''}">
            书架管理
        </a>

        <div style="margin-top: 24px; margin-bottom: 8px; padding: 0 12px; font-size: 11px; color: var(--text-secondary); text-transform: uppercase; letter-spacing: 0.5px;">Query</div>
        
        <a href="${pageContext.request.contextPath}/query/book_query" class="menu-item ${param.active == 'query' ? 'active' : ''}">
            图书查询
        </a>
        <a href="${pageContext.request.contextPath}/query/ranking" class="menu-item ${param.active == 'ranking' ? 'active' : ''}">
            借阅排行
        </a>
        <a href="${pageContext.request.contextPath}/query/expiry" class="menu-item ${param.active == 'expiry' ? 'active' : ''}">
            到期提醒
        </a>
        <a href="${pageContext.request.contextPath}/query/history" class="menu-item ${param.active == 'history' ? 'active' : ''}">
            借阅历史
        </a>
        
        <c:if test="${sessionScope.admin.role == 'SYS_ADMIN'}">
            <div style="margin-top: 24px; margin-bottom: 8px; padding: 0 12px; font-size: 11px; color: var(--text-secondary); text-transform: uppercase; letter-spacing: 0.5px;">System</div>
            <a href="${pageContext.request.contextPath}/system/admin" class="menu-item ${param.active == 'system' ? 'active' : ''}">
                管理员管理
            </a>
            <a href="${pageContext.request.contextPath}/system/library" class="menu-item ${param.active == 'library' ? 'active' : ''}">
                图书馆信息
            </a>
            <a href="${pageContext.request.contextPath}/system/params" class="menu-item ${param.active == 'params' ? 'active' : ''}">
                系统参数
            </a>
        </c:if>
    </nav>
</div>
