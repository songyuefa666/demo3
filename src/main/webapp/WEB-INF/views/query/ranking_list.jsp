<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>借阅排行榜 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="ranking"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>借阅排行榜</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <!-- Search -->
                        <form action="${pageContext.request.contextPath}/query/ranking" method="get" class="d-flex gap-2 align-center mb-4">
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">统计时间段：</label>
                                <input type="date" name="startDate" value="${param.startDate}" class="form-control" style="width: 150px;">
                                <span class="text-secondary">至</span>
                                <input type="date" name="endDate" value="${param.endDate}" class="form-control" style="width: 150px;">
                            </div>
                            <button type="submit" class="btn btn-primary">统计</button>
                        </form>
                        
                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th style="width: 60px;">排名</th>
                                        <th>书名</th>
                                        <th>ISBN</th>
                                        <th>作者</th>
                                        <th>出版社</th>
                                        <th class="text-right">借阅次数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${list}" varStatus="status">
                                        <tr>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${status.index < 3}">
                                                        <span class="badge badge-blue" style="font-size: 14px;">${status.index + 1}</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge badge-gray">${status.index + 1}</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td style="font-weight: 500;">${r.bookName}</td>
                                            <td>${r.isbn}</td>
                                            <td>${r.author}</td>
                                            <td>${r.publisher}</td>
                                            <td class="text-right" style="font-weight: 600; color: var(--system-blue);">${r.borrowCount}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="6" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                暂无统计数据
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
