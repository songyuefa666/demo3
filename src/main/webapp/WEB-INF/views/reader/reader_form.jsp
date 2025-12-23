<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty reader ? '添加读者' : '编辑读者'} - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="reader"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>${empty reader ? '添加读者' : '编辑读者'}</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="list?action=save" method="post" style="max-width: 600px;">
                            <input type="hidden" name="id" value="${reader.readerId}">
                            
                            <h4 style="margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid var(--system-gray6);">基本信息</h4>
                            
                            <div class="form-group">
                                <label class="form-label">读者条形码 <span class="text-danger">*</span></label>
                                <input type="text" name="readerBarcode" value="${reader.readerBarcode}" class="form-control" placeholder="扫描或输入条码" required>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">姓名 <span class="text-danger">*</span></label>
                                <input type="text" name="name" value="${reader.name}" class="form-control" required>
                            </div>
                            
                            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 24px;">
                                <div class="form-group">
                                    <label class="form-label">性别</label>
                                    <select name="gender" class="form-control">
                                        <option value="男" ${reader.gender == '男' ? 'selected' : ''}>男</option>
                                        <option value="女" ${reader.gender == '女' ? 'selected' : ''}>女</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="form-label">状态</label>
                                    <select name="status" class="form-control">
                                        <option value="1" ${reader.status == 1 ? 'selected' : ''}>有效</option>
                                        <option value="0" ${reader.status == 0 ? 'selected' : ''}>无效</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">单位</label>
                                <input type="text" name="unit" value="${reader.unit}" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">电话</label>
                                <input type="text" name="phone" value="${reader.phone}" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">读者类型</label>
                                <select name="typeId" class="form-control">
                                    <c:forEach var="t" items="${types}">
                                        <option value="${t.typeId}" ${reader.typeId == t.typeId ? 'selected' : ''}>${t.typeName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color); display: flex; gap: 12px;">
                                <button type="submit" class="btn btn-primary btn-lg">保存更改</button>
                                <a href="list" class="btn btn-secondary btn-lg">取消</a>
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
