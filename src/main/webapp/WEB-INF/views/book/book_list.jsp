<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>图书档案管理 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="book"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>图书档案</h2>
                    <a href="list?action=add" class="btn btn-primary">
                        <span style="font-size: 16px; margin-right: 4px;">+</span> 新增图书
                    </a>
                </div>

                <div class="card">
                    <div class="card-body">
                        <!-- Search -->
                        <div class="d-flex justify-between align-center mb-4">
                            <form action="list" method="get" class="d-flex gap-2" style="flex: 1; max-width: 400px;">
                                <input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="搜索书名 / ISBN / 作者 / 出版社...">
                                <button type="submit" class="btn btn-secondary">搜索</button>
                            </form>
                        </div>
                        
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />

                        <div class="table-container">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>条码</th>
                                        <th>书名</th>
                                        <th>ISBN</th>
                                        <th>分类</th>
                                        <th>作者</th>
                                        <th>出版社</th>
                                        <th>价格</th>
                                        <th>库存</th>
                                        <th>书架</th>
                                        <th class="text-right">操作</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="b" items="${list}">
                                        <tr>
                                            <td style="font-family: monospace;"><c:out value="${b.barcode}"/></td>
                                            <td style="font-weight: 500;"><c:out value="${b.name}"/></td>
                                            <td><c:out value="${b.isbn}"/></td>
                                            <td><span class="badge badge-gray"><c:out value="${b.bookTypeName}"/></span></td>
                                            <td><c:out value="${b.author}"/></td>
                                            <td><c:out value="${b.publisher}"/></td>
                                            <td>¥${b.price}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${b.stock > 0}">${b.stock}</c:when>
                                                    <c:otherwise><span class="badge badge-danger">无货</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${b.bookshelfName}</td>
                                            <td class="text-right">
                                                <a href="list?action=edit&id=${b.bookId}" class="btn btn-ghost btn-sm">编辑</a>
                                                <a href="javascript:void(0);" onclick="confirmDelete('list?action=delete&id=${b.bookId}')" class="btn btn-ghost btn-sm text-danger">删除</a>
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
                                    <a class="page-link" href="list?page=${currentPage - 1}&keyword=${keyword}">‹</a>
                                </li>
                                <li class="page-item ${currentPage >= totalPage ? 'disabled' : ''}">
                                    <a class="page-link" href="list?page=${currentPage + 1}&keyword=${keyword}">›</a>
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
