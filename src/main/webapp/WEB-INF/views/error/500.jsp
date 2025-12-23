<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>500 - 系统错误</title>
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
            max-width: 800px;
            width: 100%;
        }
        .error-code {
            font-size: 80px;
            font-weight: 700;
            color: var(--system-red);
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
        .error-trace {
            text-align: left;
            background: #fff;
            padding: 20px;
            border-radius: 12px;
            border: 1px solid var(--border-color);
            max-height: 300px;
            overflow: auto;
            margin-bottom: 30px;
            box-shadow: var(--shadow-sm);
        }
    </style>
</head>
<body>
    <div class="error-card">
        <div class="error-code">500</div>
        <div class="error-title">服务器内部错误</div>
        <div class="error-desc">系统发生异常，请联系管理员。</div>
        
        <div class="error-trace">
            <div style="color: var(--system-red); font-weight: 600; margin-bottom: 10px;">
                <%= exception != null ? exception.getMessage() : "Unknown Error" %>
            </div>
            <% 
                if (exception != null) { 
                    java.io.StringWriter sw = new java.io.StringWriter();
                    java.io.PrintWriter pw = new java.io.PrintWriter(sw);
                    exception.printStackTrace(pw);
            %>
            <pre style="font-size: 12px; margin: 0; white-space: pre-wrap; font-family: SF Mono, Menlo, Monaco, Consolas, monospace; color: var(--text-secondary);"><%= sw.toString() %></pre>
            <% 
                } 
            %>
        </div>
        
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">返回首页</a>
    </div>
</body>
</html>
