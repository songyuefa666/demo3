<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>图书借阅/归还 - 图书管理系统</title>
    <jsp:include page="/WEB-INF/views/common/head.jsp" />
</head>
<body>
    <div class="app-layout">
        <jsp:include page="/WEB-INF/views/common/nav.jsp">
            <jsp:param name="active" value="borrow"/>
        </jsp:include>
        
        <div class="app-main">
            <jsp:include page="/WEB-INF/views/common/top.jsp" />
            
            <div class="app-content">
                <div class="d-flex justify-between align-center mb-4">
                    <h2>图书借阅</h2>
                </div>
                
                <jsp:include page="/WEB-INF/views/common/message.jsp" />

                <!-- Search Card -->
                <div class="card">
                    <div class="card-body">
                        <form action="borrow?action=search" method="post" class="d-flex align-center gap-4">
                            <div style="flex: 1;">
                                <label class="form-label">读者条形码</label>
                                <input type="text" name="readerBarcode" value="${reader.readerBarcode != null ? reader.readerBarcode : param.readerBarcode}" class="form-control" style="font-size: 16px; padding: 12px;" placeholder="请扫描或输入读者条码..." required autofocus>
                            </div>
                            <button type="submit" class="btn btn-primary btn-lg" style="margin-top: 24px;">查询读者</button>
                        </form>
                    </div>
                </div>

                <c:if test="${not empty reader}">
                    <div style="display: grid; grid-template-columns: 300px 1fr; gap: 24px; margin-bottom: 24px;">
                        <!-- Reader Info Card -->
                        <div class="card" style="margin-bottom: 0;">
                            <div class="card-header">
                                读者信息
                            </div>
                            <div class="card-body">
                                <div style="text-align: center; margin-bottom: 20px;">
                                    <div style="width: 80px; height: 80px; background: var(--system-gray6); border-radius: 50%; margin: 0 auto 10px; display: flex; align-items: center; justify-content: center; font-size: 32px;">
                                        ${reader.gender == '女' ? '👩' : '👨'}
                                    </div>
                                    <h3 style="margin-bottom: 4px;">${reader.name}</h3>
                                    <div class="text-secondary">${reader.typeName}</div>
                                </div>
                                
                                <div style="border-top: 1px solid var(--system-gray6); padding-top: 16px;">
                                    <div class="d-flex justify-between mb-2">
                                        <span class="text-secondary">状态</span>
                                        <c:choose>
                                            <c:when test="${reader.status == 1}"><span class="badge badge-success">有效</span></c:when>
                                            <c:otherwise><span class="badge badge-danger">无效</span></c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="d-flex justify-between mb-2">
                                        <span class="text-secondary">限借数量</span>
                                        <span style="font-weight: 500;">${readerType.maxBorrowNum} 本</span>
                                    </div>
                                    <div class="d-flex justify-between mb-2">
                                        <span class="text-secondary">限借天数</span>
                                        <span style="font-weight: 500;">${readerType.maxBorrowDays} 天</span>
                                    </div>
                                    <div class="d-flex justify-between">
                                        <span class="text-secondary">日罚金</span>
                                        <span style="font-weight: 500;">¥${readerType.finePerDay}</span>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- Borrow Action Card -->
                        <div class="card" style="margin-bottom: 0;">
                            <div class="card-header">
                                借阅操作
                            </div>
                            <div class="card-body">
                                <form action="borrow?action=borrow" method="post">
                                    <input type="hidden" name="readerBarcode" value="${reader.readerBarcode}">
                                    <div class="form-group">
                                        <label class="form-label">图书条形码</label>
                                        <div class="d-flex gap-2">
                                            <input type="text" name="bookBarcode" class="form-control" style="font-size: 16px; padding: 12px;" placeholder="请扫描或输入图书条码..." required>
                                            <button type="submit" class="btn btn-primary btn-lg">确认借阅</button>
                                        </div>
                                    </div>
                                </form>
                                
                                <div style="margin-top: 30px;">
                                    <h4 style="font-size: 14px; text-transform: uppercase; color: var(--text-secondary); margin-bottom: 12px;">快捷说明</h4>
                                    <ul style="padding-left: 20px; color: var(--text-secondary); font-size: 13px; line-height: 1.6;">
                                        <li>请确保图书条码清晰可读。</li>
                                        <li>借阅成功后，列表会自动刷新。</li>
                                        <li>若图书有逾期未还，可能无法继续借阅。</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
            
                    <!-- Borrow List -->
                    <div class="card">
                        <div class="card-header">
                            当前在借列表
                        </div>
                        <div class="card-body" style="padding: 0;">
                            <div class="table-container" style="border: none; border-radius: 0;">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>图书名称</th>
                                            <th>条形码</th>
                                            <th>借阅日期</th>
                                            <th>应还日期</th>
                                            <th>续借</th>
                                            <th>状态</th>
                                            <th class="text-right">操作</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="br" items="${list}">
                                            <tr style="${br.status == 3 ? 'background-color: rgba(255,59,48,0.05);' : ''}">
                                                <td style="font-weight: 500;">${br.bookName}</td>
                                                <td style="font-family: monospace;">${br.bookBarcode}</td>
                                                <td>${br.borrowDate}</td>
                                                <td style="${br.status == 3 ? 'color: var(--system-red); font-weight: 600;' : ''}">${br.dueDate}</td>
                                                <td>${br.renewTimes}次</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${br.status == 1}"><span class="badge badge-blue">借阅中</span></c:when>
                                                        <c:when test="${br.status == 3}"><span class="badge badge-danger">已超期</span></c:when>
                                                    </c:choose>
                                                </td>
                                                <td class="text-right">
                                                    <a href="borrow?action=returnBook&id=${br.borrowId}&readerBarcode=${reader.readerBarcode}" onclick="return confirm('确定归还《${br.bookName}》吗？')" class="btn btn-secondary btn-sm">归还</a>
                                                    <c:if test="${br.status == 1}">
                                                        <a href="borrow?action=renew&id=${br.borrowId}&readerBarcode=${reader.readerBarcode}" class="btn btn-ghost btn-sm">续借</a>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:if test="${empty list}">
                                            <tr><td colspan="7" class="text-center" style="padding: 40px; color: var(--text-secondary);">当前无在借记录</td></tr>
                                        </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </c:if>
            </div>
            
            <jsp:include page="/WEB-INF/views/common/footer.jsp" />
        </div>
    </div>
</body>
</html>
