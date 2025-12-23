<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${empty rt ? '添加读者类型' : '编辑读者类型'}</title>
</head>
<body>
    <h2>${empty rt ? '添加读者类型' : '编辑读者类型'}</h2>
    <a href="type">返回列表</a>
    <hr/>
    <form action="type?action=save" method="post">
        <input type="hidden" name="id" value="${rt.typeId}">
        <div>
            <label>类型名称：</label>
            <input type="text" name="typeName" value="${rt.typeName}" required>
        </div>
        <div>
            <label>最大借阅数：</label>
            <input type="number" name="maxBorrowNum" value="${rt.maxBorrowNum}" required>
        </div>
        <div>
            <label>最长借阅天数：</label>
            <input type="number" name="maxBorrowDays" value="${rt.maxBorrowDays}" required>
        </div>
        <div>
            <label>日罚金：</label>
            <input type="text" name="finePerDay" value="${rt.finePerDay}" required>
        </div>
        <button type="submit">保存</button>
    </form>
</body>
</html>