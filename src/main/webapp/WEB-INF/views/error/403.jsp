<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>403 - 禁止访问</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
    <style>
        body {
            background-color: #f5f5f7;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .error-card {
            text-align: center;
            padding: 40px;
        }
        .error-code {
            font-size: 80px;
            font-weight: 700;
            color: var(--system-gray);
            margin-bottom: 20px;
            line-height: 1;
        }
        .error-title {
            font-size: 24px;
            font-weight: 600;
            margin-bottom: 12px;
            color: var(--text-primary);
        }
        .error-desc {
            color: var(--text-secondary);
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="error-card">
        <div class="error-code">403</div>
        <div class="error-title">禁止访问</div>
        <div class="error-desc">您没有权限访问该资源，请联系管理员。</div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">返回首页</a>
    </div>
</body>
</html>
