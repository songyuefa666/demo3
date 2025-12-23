<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>读者类型管理</title>
</head>
<body>
    <h2>读者类型列表</h2>
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
                <th>最大借阅数</th>
                <th>最长借阅天数</th>
                <th>日罚金</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="rt" items="${list}">
                <tr>
                    <td>${rt.typeId}</td>
                    <td>${rt.typeName}</td>
                    <td>${rt.maxBorrowNum}</td>
                    <td>${rt.maxBorrowDays}</td>
                    <td>${rt.finePerDay}</td>
                    <td>
                        <a href="type?action=edit&id=${rt.typeId}">编辑</a>
                        <a href="type?action=delete&id=${rt.typeId}" onclick="return confirm('确定删除吗？')">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>