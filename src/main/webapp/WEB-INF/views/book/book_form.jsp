<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty book ? '添加图书' : '编辑图书'} - 图书管理系统</title>
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
                    <h2>${empty book ? '添加图书' : '编辑图书'}</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="list?action=save" method="post" style="max-width: 800px;">
                            <input type="hidden" name="id" value="${book.bookId}">
                            
                            <h4 style="margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid var(--system-gray6);">基本信息</h4>
                            
                            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
                                <div class="form-group">
                                    <label class="form-label">图书条码 <span class="text-danger">*</span></label>
                                    <input type="text" name="barcode" value="${book.barcode}" class="form-control" placeholder="扫描或输入条码" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">ISBN <span class="text-danger">*</span></label>
                                    <input type="text" name="isbn" value="${book.isbn}" class="form-control" placeholder="978-..." required>
                                </div>
                                
                                <div class="form-group" style="grid-column: span 2;">
                                    <label class="form-label">书名 <span class="text-danger">*</span></label>
                                    <input type="text" name="name" value="${book.name}" class="form-control" style="font-weight: 500;" required>
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label">作者 <span class="text-danger">*</span></label>
                                    <input type="text" name="author" value="${book.author}" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">出版社 <span class="text-danger">*</span></label>
                                    <input type="text" name="publisher" value="${book.publisher}" class="form-control" required>
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label">图书分类</label>
                                    <select name="bookTypeId" class="form-control">
                                        <c:forEach var="t" items="${types}">
                                            <option value="${t.id}" ${book.bookTypeId == t.id ? 'selected' : ''}>${t.typeName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">所在书架</label>
                                    <select name="bookshelfId" class="form-control">
                                        <c:forEach var="bs" items="${bookshelves}">
                                            <option value="${bs.id}" ${book.bookshelfId == bs.id ? 'selected' : ''}>${bs.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            
                            <h4 style="margin-top: 30px; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid var(--system-gray6);">出版详情</h4>
                            
                            <div style="display: grid; grid-template-columns: 1fr 1fr 1fr; gap: 24px;">
                                <div class="form-group">
                                    <label class="form-label">价格 <span class="text-danger">*</span></label>
                                    <div class="d-flex align-center">
                                        <span style="margin-right: 8px; color: var(--text-secondary);">¥</span>
                                        <input type="number" step="0.01" name="price" value="${book.price}" class="form-control" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">库存 <span class="text-danger">*</span></label>
                                    <input type="number" name="stock" value="${book.stock}" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">版次</label>
                                    <input type="text" name="edition" value="${book.edition}" class="form-control" placeholder="第1版">
                                </div>
                                
                                <div class="form-group">
                                    <label class="form-label">开本</label>
                                    <input type="text" name="format" value="${book.format}" class="form-control" placeholder="16开">
                                </div>
                                <div class="form-group">
                                    <label class="form-label">装订</label>
                                    <input type="text" name="binding" value="${book.binding}" class="form-control" placeholder="平装">
                                </div>
                            </div>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color); display: flex; gap: 12px;">
                                <button type="submit" class="btn btn-primary btn-lg">保存更改</button>
                                <a href="list" class="btn btn-secondary btn-lg">取消</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </div>
</body>
</html>
