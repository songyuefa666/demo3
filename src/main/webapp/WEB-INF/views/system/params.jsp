<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统参数设置 - 图书管理系统</title>
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
                    <h2>系统参数设置</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="params?action=update" method="post" style="max-width: 600px;">
                            <c:forEach var="p" items="${list}">
                                <div class="form-group">
                                    <label class="form-label">${p.description} (${p.paramName})</label>
                                    <input type="text" name="${p.paramName}" value="${p.paramValue}" class="form-control" required>
                                </div>
                            </c:forEach>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color);">
                                <button type="submit" class="btn btn-primary btn-lg">保存设置</button>
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
