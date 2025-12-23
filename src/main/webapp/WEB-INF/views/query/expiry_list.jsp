<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>借阅到期提醒</title>
</head>
<body>
    <h2>借阅到期提醒 (3天内到期 或 已超期)</h2>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
    <hr/>
    <table border="1" cellpadding="5" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>读者</th>
                <th>图书</th>
                <th>借阅日期</th>
                <th>应还日期</th>
                <th>状态</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="br" items="${list}">
                <tr style="${br.status == 3 ? 'background-color: #ffcccc;' : ''}">
                    <td>${br.readerName} (${br.readerBarcode})</td>
                    <td>${br.bookName} (${br.bookBarcode})</td>
                    <td>${br.borrowDate}</td>
                    <td>${br.dueDate}</td>
                    <td>
                        <c:choose>
                            <c:when test="${br.status == 1}">即将到期</c:when>
                            <c:when test="${br.status == 3}">已超期</c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty list}">
                <tr><td colspan="5">无即将到期记录</td></tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>