<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>读者档案管理 - 图书管理系统</title>
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
                    <h2>读者档案</h2>
                    <a href="list?action=add" class="btn btn-primary">
                        <span style="font-size: 16px; margin-right: 4px;">+</span> 新增读者
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
                                        <th>条形码</th>
                                        <th>姓名</th>
                                        <th>性别</th>
                                        <th>单位</th>
                                        <th>电话</th>
                                        <th>类型</th>
                                        <th>状态</th>
                                        <th>注册日期</th>
                                        <th class="text-right">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="r" items="${list}">
                                        <tr>
                                            <td>${r.readerId}</td>
                                            <td style="font-family: monospace;"><c:out value="${r.readerBarcode}"/></td>
                                            <td style="font-weight: 500;"><c:out value="${r.name}"/></td>
                                            <td><c:out value="${r.gender}"/></td>
                                            <td><c:out value="${r.unit}"/></td>
                                            <td><c:out value="${r.phone}"/></td>
                                            <td><span class="badge badge-gray"><c:out value="${r.typeName}"/></span></td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${r.status == 1}"><span class="badge badge-success">有效</span></c:when>
                                                    <c:otherwise><span class="badge badge-danger">无效</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${r.registerDate}</td>
                                            <td class="text-right">
                                                <a href="list?action=edit&id=${r.readerId}" class="btn btn-ghost btn-sm">编辑</a>
                                                <a href="javascript:void(0);" onclick="confirmDelete('list?action=delete&id=${r.readerId}')" class="btn btn-ghost btn-sm text-danger">删除</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty list}">
                                        <tr>
                                            <td colspan="10" class="text-center" style="padding: 40px; color: var(--text-secondary);">
                                                没有找到相关记录
                                            </td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Pagination -->
                        <div class="d-flex justify-between align-center mt-4">
                            <div class="text-secondary" style="font-size: 13px;">
                                第 ${currentPage} / ${totalPage} 页
                            </div>
                            <ul class="pagination">
                                <li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
                                    <a class="page-link" href="list?page=${currentPage - 1}">‹</a>
                                </li>
                                <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                                    <a class="page-link" href="list?page=${currentPage + 1}">›</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </div>
</body>
</html>
