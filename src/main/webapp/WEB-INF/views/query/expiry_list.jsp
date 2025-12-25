<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>借阅到期提醒 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="expiry"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>借阅到期提醒</h2>
                    <div class="text-secondary">包含 3 天内到期或已超期的记录</div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/query/expiry" method="get" class="d-flex gap-3 align-center flex-wrap mb-4">
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">读者类型：</label>
                                <select name="readerTypeId" class="form-control" style="width: 180px;">
                                    <option value="">全部</option>
                                    <c:forEach var="t" items="${types}">
                                        <option value="${t.typeId}" ${param.readerTypeId == t.typeId ? 'selected' : ''}>${t.typeName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">借阅开始日期：</label>
                                <input type="date" name="startDate" value="${param.startDate}" class="form-control" style="width: 170px;">
                            </div>
                            <div class="d-flex align-center gap-2">
                                <label class="text-secondary">借阅结束日期：</label>
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
                                        <th>状态</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="br" items="${list}">
                                        <tr style="${br.status == 3 ? 'background-color: #fff3f3;' : ''}">
                                            <td>${br.readerName} (${br.readerBarcode})</td>
                                            <td>${br.bookName} (${br.bookBarcode})</td>
                                            <td>${br.borrowDate}</td>
                                            <td>${br.dueDate}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${br.status == 1}"><span class="badge badge-warning">即将到期</span></c:when>
                                                    <c:when test="${br.status == 3}"><span class="badge badge-danger">已超期</span></c:when>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="5" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                无即将到期记录
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
