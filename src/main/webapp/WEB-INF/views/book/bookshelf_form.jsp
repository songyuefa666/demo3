<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty bookshelf ? '新增书架' : '编辑书架'} - 图书管理系统</title>
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
                    <h2>${empty bookshelf ? '新增书架' : '编辑书架'}</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="bookshelf?action=save" method="post" style="max-width: 600px;">
                            <input type="hidden" name="id" value="${bookshelf.id}">
                            
                            <div class="form-group">
                                <label class="form-label">书架名称 <span class="text-danger">*</span></label>
                                <input type="text" name="name" value="${bookshelf.name}" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">位置/区域</label>
                                <input type="text" name="location" value="${bookshelf.location}" class="form-control">
                            </div>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color); display: flex; gap: 12px;">
                                <button type="submit" class="btn btn-primary btn-lg">保存</button>
                                <a href="bookshelf" class="btn btn-secondary btn-lg">取消</a>
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
