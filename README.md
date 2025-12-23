# 图书管理系统 (Library Management System)

## 1. 环境要求
- **操作系统**: Windows / Linux / macOS
- **JDK**: JDK 1.8 或更高版本 (推荐 JDK 11/17/21)
- **Web服务器**: Apache Tomcat 9.0 (必须支持 Servlet 4.0)
- **数据库**: MySQL 5.7 或 8.0
- **构建工具**: Maven 3.6+

## 2. 数据库配置
请使用 DataGrip 或其他数据库客户端连接 MySQL，并执行 `src/main/resources/init.sql` 脚本。

### 需要的表清单 (脚本已包含):
1.  `admin` (管理员)
2.  `book` (图书档案)
3.  `book_type` (图书类型)
4.  `bookshelf` (书架)
5.  `borrow_record` (借阅记录)
6.  `library_info` (图书馆信息)
7.  `reader` (读者档案)
8.  `reader_type` (读者类型)
9.  `sys_params` (系统参数)
10. `system_log` (系统日志)

## 3. 部署与启动
1.  使用 Maven 构建项目: `mvn clean package`
2.  将生成的 `demo3.war` (或目录) 部署到 Tomcat 的 `webapps` 目录下。
3.  启动 Tomcat。
4.  修改 `src/main/resources/db.properties` 中的数据库账号密码（如果不是 root/123456）。

## 4. 访问入口
- **首页 URL**: `http://localhost:8080/demo3/`
- **默认账号**:
    - **超级管理员**: 用户名 `admin` / 密码 `admin123`
    - **操作员**: 用户名 `operator` / 密码 `op123`

## 5. 功能模块
- **登录/注销**: 权限控制 (RoleFilter)
- **系统设置**: 图书馆信息、管理员管理、参数配置 (仅限 SYS_ADMIN)
- **读者管理**: 读者类型、读者档案 (CRUD, 校验)
- **图书管理**: 图书类型、书架、图书档案 (CRUD, 库存管理)
- **借阅管理**: 借书、还书、续借 (核心业务, 事务控制, 超期罚款)
- **查询统计**: 到期提醒、借阅历史查询、借阅排行榜

## 6. 注意事项
- 系统日志会自动记录关键操作 (INSERT/UPDATE/DELETE/BORROW/RETURN)。
- 借阅规则受“读者类型”和“系统参数”双重限制，取较严格者。
- 删除数据前请确认未被引用（如类型被图书引用则无法删除）。
