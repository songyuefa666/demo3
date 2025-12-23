<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>图书类型管理</title>
</head>
<body>
    <h2>图书类型列表</h2>
    <a href="${pageContext.request.contextPath}/home">返回首页</a> | 
    <a href="type?action=add">添加类型</a>
    <hr/>
    <c:if test="${not empty error}">
        <div style="color: red;">${error}</div>
    </c:if>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>类型名称</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="t" items="${list}">
                <tr>
                    <td>${t.id}</td>
                    <td>${t.typeName}</td>
                    <td>${t.description}</td>
                    <td>
                        <a href="type?action=edit&id=${t.id}">编辑</a>
                        <a href="type?action=delete&id=${t.id}" onclick="return confirm('确定删除吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>