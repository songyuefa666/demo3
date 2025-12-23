<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>图书借阅查询</title>
</head>
<body>
    <h2>图书借阅查询</h2>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
    <hr/>
    <form action="${pageContext.request.contextPath}/query/history" method="get">
        <label>读者条码：</label>
        <input type="text" name="readerBarcode" value="${param.readerBarcode}">
        <label>图书名称：</label>
        <input type="text" name="bookName" value="${param.bookName}">
        <label>开始日期：</label>
        <input type="date" name="startDate" value="${param.startDate}">
        <label>结束日期：</label>
        <input type="date" name="endDate" value="${param.endDate}">
        <button type="submit">查询</button>
    </form>
    <br/>
    <table border="1" cellpadding="5" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>读者</th>
                <th>图书</th>
                <th>借阅日期</th>
                <th>应还日期</th>
                <th>归还日期</th>
                <th>状态</th>
                <th>罚金</th>
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
                            <c:when test="${br.status == 1}">借阅中</c:when>
                            <c:when test="${br.status == 2}">已归还</c:when>
                            <c:when test="${br.status == 3}">已超期</c:when>
                        </c:choose>
                    </td>
                    <td>${br.fine}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>