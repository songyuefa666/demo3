<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>图书借阅查询 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="history"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>图书借阅查询</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/query/history" method="get" class="d-flex gap-3 align-center flex-wrap mb-4">
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">读者条码：</label>
                                <input type="text" name="readerBarcode" value="${param.readerBarcode}" class="form-control" style="width: 180px;">
                            </div>
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">图书名称：</label>
                                <input type="text" name="bookName" value="${param.bookName}" class="form-control" style="width: 200px;">
                            </div>
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">开始日期：</label>
                                <input type="date" name="startDate" value="${param.startDate}" class="form-control" style="width: 170px;">
                            </div>
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">结束日期：</label>
                                <input type="date" name="endDate" value="${param.endDate}" class="form-control" style="width: 170px;">
                            </div>
                            <button type="submit" class="btn btn-primary">查询</button>
                        </form>

                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>读者</th>
                                        <th>图书</th>
                                        <th>借阅日期</th>
                                        <th>应还日期</th>
                                        <th>归还日期</th>
                                        <th>状态</th>
                                        <th class="text-right">罚金</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="br" items="${list}">
                                        <tr>
                                            <td>${br.readerName} (${br.readerBarcode})</td>
                                            <td>${br.bookName} (${br.bookBarcode})</td>
                                            <td>${br.borrowDate}</td>
                                            <td>${br.dueDate}</td>
                                            <td>${br.returnDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${br.status == 1}"><span class="badge badge-blue">借阅中</span></c:when>
                                                    <c:when test="${br.status == 2}"><span class="badge badge-success">已归还</span></c:when>
                                                    <c:when test="${br.status == 3}"><span class="badge badge-danger">已超期</span></c:when>
                                                </c:choose>
                                            </td>
                                            <td class="text-right">${br.fine}</td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="7" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                暂无借阅记录
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
