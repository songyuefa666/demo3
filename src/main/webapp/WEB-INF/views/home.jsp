<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="home"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="card">
                    <div class="card-body" style="padding: 60px; text-align: center;">
                        <div style="font-size: 64px; margin-bottom: 20px;">👋</div>
                        <h2 style="font-size: 32px; margin-bottom: 10px;">欢迎回来，${sessionScope.admin.realName}</h2>
                        <p class="text-secondary" style="font-size: 16px; margin-bottom: 40px;">今天是美好的工作日，准备好开始管理图书了吗？</p>
                        
                        <div class="d-flex justify-content-center gap-4" style="justify-content: center;">
                            <a href="${pageContext.request.contextPath}/borrow?action=index" class="card" style="width: 200px; padding: 30px; text-align: center; box-shadow: var(--shadow-md); transition: transform 0.2s; display: block; color: var(--text-primary);">
                                <div style="font-size: 32px; margin-bottom: 10px; color: var(--system-blue);">📖</div>
                                <div style="font-weight: 600;">图书借还</div>
                                <div style="font-size: 12px; color: var(--text-secondary); margin-top: 5px;">快速办理借阅归还</div>
                            </a>
                            
                            <a href="${pageContext.request.contextPath}/book/list" class="card" style="width: 200px; padding: 30px; text-align: center; box-shadow: var(--shadow-md); transition: transform 0.2s; display: block; color: var(--text-primary);">
                                <div style="font-size: 32px; margin-bottom: 10px; color: var(--system-green);">📚</div>
                                <div style="font-weight: 600;">图书管理</div>
                                <div style="font-size: 12px; color: var(--text-secondary); margin-top: 5px;">查阅与编辑馆藏</div>
                            </a>
                            
                            <a href="${pageContext.request.contextPath}/reader/list" class="card" style="width: 200px; padding: 30px; text-align: center; box-shadow: var(--shadow-md); transition: transform 0.2s; display: block; color: var(--text-primary);">
                                <div style="font-size: 32px; margin-bottom: 10px; color: var(--system-orange, #ff9500);">👥</div>
                                <div style="font-weight: 600;">读者管理</div>
                                <div style="font-size: 12px; color: var(--text-secondary); margin-top: 5px;">维护读者档案</div>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </div>
</body>
</html>
