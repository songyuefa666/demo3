<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>系统登录 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
    <style>
        body {
            background-color: #f5f5f7;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .login-card {
            width: 100%;
            max-width: 380px;
            background: #fff;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.08);
        }
        .login-logo {
            font-size: 48px;
            color: var(--system-blue);
            margin-bottom: 10px;
            text-align: center;
        }
        .login-title {
            text-align: center;
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 30px;
            color: #1d1d1f;
        }
    </style>
</head>
<body>
    <div class="login-card">
        <div class="login-logo"></div>
        <div class="login-title">图书管理系统</div>
        
        <jsp:include page="/WEB-INF/views/common/message.jsp" />
        
        <form action="login?action=login" method="post">
            <div class="form-group">
                <label class="form-label">账号</label>
                <input type="text" name="username" class="form-control" placeholder="请输入管理员账号" required autofocus>
            </div>
            <div class="form-group">
                <label class="form-label">密码</label>
                <input type="password" name="password" class="form-control" placeholder="请输入密码" required>
            </div>
            <div class="form-group" style="margin-top: 30px;">
                <button type="submit" class="btn btn-primary w-100 btn-lg">登 录</button>
            </div>
        </form>
    </div>
</body>
</html>
