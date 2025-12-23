<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>修改密码 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp" />
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>修改密码</h2>
                </div>

                <div class="card" style="max-width: 600px; margin: 0 auto;">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />

                        <%
                            String error = (String) request.getAttribute("error");
                            if (error != null) {
                        %>
                            <div class="alert alert-danger mb-4"><%=error%></div>
                        <%
                            }
                        %>

                        <form action="${pageContext.request.contextPath}/login?action=updatePassword" method="post">
                            <div class="form-group">
                                <label class="form-label">当前旧密码</label>
                                <input type="password" name="oldPassword" class="form-control" required placeholder="请输入当前使用的密码">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">新密码</label>
                                <input type="password" name="newPassword" class="form-control" required placeholder="请输入新密码">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">确认新密码</label>
                                <input type="password" name="confirmPassword" class="form-control" required placeholder="请再次输入新密码">
                            </div>

                            <div class="d-flex justify-end gap-2 mt-4">
                                <button type="reset" class="btn btn-ghost">重置</button>
                                <button type="submit" class="btn btn-primary">确认修改</button>
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