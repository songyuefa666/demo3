<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>404 - 页面未找到</title>
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
        <div class="error-code">404</div>
        <div class="error-title">页面未找到</div>
        <div class="error-desc">您请求的页面不存在或已被移除。</div>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">返回首页</a>
    </div>
</body>
</html>
