<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>管理员管理 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="system"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>管理员管理</h2>
                    <a href="admin?action=add" class="btn btn-primary">
                        <span style="font-size: 16px; margin-right: 4px;">+</span> 新增管理员
                    </a>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />

                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>用户名</th>
                                        <th>真实姓名</th>
                                        <th>电话</th>
                                        <th>角色</th>
                                        <th>注册时间</th>
                                        <th class="text-right">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="admin" items="${list}">
                                        <tr>
                                            <td>${admin.adminId}</td>
                                            <td style="font-weight: 500;">${admin.username}</td>
                                            <td>${admin.realName}</td>
                                            <td>${admin.phone}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${admin.role == 'SYS_ADMIN'}"><span class="badge badge-blue">超级管理员</span></c:when>
                                                    <c:otherwise><span class="badge badge-gray">操作员</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${admin.registerDate}</td>
                                            <td class="text-right">
                                                <a href="admin?action=edit&id=${admin.adminId}" class="btn btn-ghost btn-sm">编辑</a>
                                                <a href="javascript:void(0);" onclick="confirmDelete('admin?action=delete&id=${admin.adminId}')" class="btn btn-ghost btn-sm text-danger">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="7" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                没有找到相关记录
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </div>
</body>
</html>
