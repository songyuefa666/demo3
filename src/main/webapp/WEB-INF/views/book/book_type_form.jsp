<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${empty t ? '添加图书类型' : '编辑图书类型'}</title>
</head>
<body>
    <h2>${empty t ? '添加图书类型' : '编辑图书类型'}</h2>
    <a href="type">返回列表</a>
    <hr/>
    <form action="type?action=save" method="post">
        <input type="hidden" name="id" value="${t.id}">
        <div>
            <label>类型名称：</label>
            <input type="text" name="typeName" value="${t.typeName}" required>
        </div>
        <div>
            <label>描述：</label>
            <textarea name="description">${t.description}</textarea>
        </div>
        <button type="submit">保存</button>
    </form>
</body>
</html>