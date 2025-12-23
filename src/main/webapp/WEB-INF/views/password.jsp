<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
</head>
<body>
    <h2>修改密码</h2>
    <a href="${pageContext.request.contextPath}/home">返回首页</a>
    <hr/>
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <div style="color: red; margin-bottom: 10px;"><%=error%></div>
    <%
        }
    %>
    <form action="login?action=updatePassword" method="post">
        <div style="margin-bottom: 10px;">
            <label>旧密码：</label>
            <input type="password" name="oldPassword" required>
        </div>
        <div style="margin-bottom: 10px;">
            <label>新密码：</label>
            <input type="password" name="newPassword" required>
        </div>
        <div style="margin-bottom: 10px;">
            <label>确认新密码：</label>
            <input type="password" name="confirmPassword" required>
        </div>
        <button type="submit">确认修改</button>
    </form>
</body>
</html>