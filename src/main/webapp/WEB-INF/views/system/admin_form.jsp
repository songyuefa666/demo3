<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${empty admin ? '添加管理员' : '编辑管理员'} - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="system"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>${empty admin ? '添加管理员' : '编辑管理员'}</h2>
                </div>

                <div class="card">
                    <div class="card-body">
                        <jsp:include page="/WEB-INF/views/common/message.jsp" />
                        
                        <form action="admin?action=save" method="post" style="max-width: 600px;">
                            <input type="hidden" name="id" value="${admin.adminId}">
                            
                            <h4 style="margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid var(--system-gray6);">账号信息</h4>
                            
                            <div class="form-group">
                                <label class="form-label">用户名 <span class="text-danger">*</span></label>
                                <input type="text" name="username" value="${admin.username}" class="form-control" required>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">密码 ${not empty admin ? '<span class="text-secondary" style="font-weight: normal;">(留空表示不修改)</span>' : '<span class="text-danger">*</span>'}</label>
                                <input type="password" name="password" value="" class="form-control" ${empty admin ? 'required' : ''}>
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">角色</label>
                                <select name="role" class="form-control">
                                    <option value="OPERATOR" ${admin.role == 'OPERATOR' ? 'selected' : ''}>操作员 (OPERATOR)</option>
                                    <option value="SYS_ADMIN" ${admin.role == 'SYS_ADMIN' ? 'selected' : ''}>超级管理员 (SYS_ADMIN)</option>
                                </select>
                            </div>
                            
                            <h4 style="margin-top: 30px; margin-bottom: 20px; padding-bottom: 10px; border-bottom: 1px solid var(--system-gray6);">个人信息</h4>
                            
                            <div class="form-group">
                                <label class="form-label">真实姓名</label>
                                <input type="text" name="realName" value="${admin.realName}" class="form-control">
                            </div>
                            
                            <div class="form-group">
                                <label class="form-label">电话</label>
                                <input type="text" name="phone" value="${admin.phone}" class="form-control">
                            </div>
                            
                            <div style="margin-top: 40px; padding-top: 20px; border-top: 1px solid var(--border-color); display: flex; gap: 12px;">
                                <button type="submit" class="btn btn-primary btn-lg">保存更改</button>
                                <a href="admin" class="btn btn-secondary btn-lg">取消</a>
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
