<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>图书馆信息 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="system"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>图书馆信息设置</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="library?action=update" method="post" style="max-width: 600px;">
                            <div class="form-group">
                                <label class="form-label">图书馆名称</label>
                                <input type="text" name="libraryName" value="${info.libraryName}" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">地址</label>
                                <input type="text" name="address" value="${info.address}" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">联系电话</label>
                                <input type="text" name="phone" value="${info.phone}" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">开放时间</label>
                                <input type="text" name="openHours" value="${info.openHours}" class="form-control" placeholder="例如：周一至周日 8:00 - 22:00">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">简介</label>
                                <textarea name="introduction" class="form-control" rows="5">${info.introduction}</textarea>
                            </div>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color);">
                                <button type="submit" class="btn btn-primary btn-lg">保存信息</button>
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
