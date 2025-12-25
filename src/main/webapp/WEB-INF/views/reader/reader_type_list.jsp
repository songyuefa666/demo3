<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>读者类型管理 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="reader"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>读者类型管理</h2>
                    <a href="type?action=add" class="btn btn-primary">
                        <span style="font-size: 16px; margin-right: 4px;">+</span> 新增类型
                    </a>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger mb-4">${error}</div>
                        </c:if>

                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>类型名称</th>
                                        <th>最大借阅数</th>
                                        <th>最长借阅天数</th>
                                        <th>日罚金</th>
                                        <th class="text-right">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="rt" items="${list}">
                                        <tr>
                                            <td>${rt.typeId}</td>
                                            <td style="font-weight: 500;">${rt.typeName}</td>
                                            <td>${rt.maxBorrowNum} 本</td>
                                            <td>${rt.maxBorrowDays} 天</td>
                                            <td>¥${rt.finePerDay}</td>
                                            <td class="text-right">
                                                <a href="type?action=edit&id=${rt.typeId}" class="btn btn-ghost btn-sm">编辑</a>
                                                <a href="javascript:void(0);" onclick="confirmDelete('type?action=delete&id=${rt.typeId}')" class="btn btn-ghost btn-sm text-danger">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="6" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                暂无类型数据
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