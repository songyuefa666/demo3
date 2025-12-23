-- ----------------------------
-- 1. 初始化数据库
-- ----------------------------
CREATE DATABASE IF NOT EXISTS library_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_db;

-- ----------------------------
-- 2. 删除旧表（如果有）
-- ----------------------------
DROP TABLE IF EXISTS borrow_record;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS reader;
DROP TABLE IF EXISTS reader_type;
DROP TABLE IF EXISTS book_type;
DROP TABLE IF EXISTS bookshelf;
DROP TABLE IF EXISTS sys_params;
DROP TABLE IF EXISTS library_info;
DROP TABLE IF EXISTS system_log;
DROP TABLE IF EXISTS admin;

-- ----------------------------
-- 3. 创建表结构
-- ----------------------------

-- 管理员表
CREATE TABLE admin (
    admin_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL, -- 实际项目应加密
    real_name VARCHAR(50),
    phone VARCHAR(20),
    register_date DATETIME,
    role VARCHAR(20) DEFAULT 'OPERATOR' -- SYS_ADMIN, OPERATOR
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 系统日志表
CREATE TABLE system_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operator_id BIGINT,
    operation_type VARCHAR(20), -- INSERT, UPDATE, DELETE, BORROW, RETURN...
    content TEXT,
    create_time DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 图书馆信息表
CREATE TABLE library_info (
    id INT AUTO_INCREMENT PRIMARY KEY,
    library_name VARCHAR(100),
    address VARCHAR(255),
    phone VARCHAR(20),
    open_hours VARCHAR(100),
    introduction TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 系统参数表
CREATE TABLE sys_params (
    id INT AUTO_INCREMENT PRIMARY KEY,
    param_name VARCHAR(50) UNIQUE,
    param_value VARCHAR(100),
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 书架表
CREATE TABLE bookshelf (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    location VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 图书类型表
CREATE TABLE book_type (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50),
    description VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 图书档案表
CREATE TABLE book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100),
    isbn VARCHAR(20),
    book_type_id INT,
    author VARCHAR(50),
    publisher VARCHAR(50),
    format VARCHAR(20), -- 开本
    binding VARCHAR(20), -- 装订
    edition VARCHAR(20), -- 版次
    price DECIMAL(10,2),
    stock INT,
    bookshelf_id INT,
    create_time DATETIME,
    FOREIGN KEY (book_type_id) REFERENCES book_type(id),
    FOREIGN KEY (bookshelf_id) REFERENCES bookshelf(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 读者类型表
CREATE TABLE reader_type (
    type_id INT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50),
    max_borrow_num INT,
    max_borrow_days INT,
    fine_per_day DECIMAL(10,2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 读者表
CREATE TABLE reader (
    reader_id INT AUTO_INCREMENT PRIMARY KEY,
    reader_barcode VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(50),
    gender VARCHAR(10),
    unit VARCHAR(100),
    phone VARCHAR(20),
    type_id INT,
    register_date DATETIME,
    status INT DEFAULT 1, -- 0:无效, 1:有效
    FOREIGN KEY (type_id) REFERENCES reader_type(type_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 借阅记录表
CREATE TABLE borrow_record (
    borrow_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    reader_id INT,
    book_id INT,
    borrow_date DATETIME,
    due_date DATETIME,
    return_date DATETIME,
    renew_times INT DEFAULT 0,
    status INT, -- 1:借阅中, 2:已归还, 3:已超期
    fine DECIMAL(10,2),
    operator_id BIGINT,
    FOREIGN KEY (reader_id) REFERENCES reader(reader_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- 4. 初始化数据
-- ----------------------------

-- 4.1 管理员数据
INSERT INTO admin (username, password, real_name, phone, register_date, role) VALUES 
('admin', 'admin123', '超级管理员', '13800138000', NOW(), 'SYS_ADMIN'),
('operator', 'op123', '图书操作员', '13900139000', NOW(), 'OPERATOR');

-- 4.2 图书馆信息
INSERT INTO library_info (library_name, address, phone, open_hours, introduction) VALUES 
('市中心图书馆', '建设大路100号', '010-88888888', '09:00 - 21:00', '这是一个拥有百万藏书的现代化图书馆，提供舒适的阅读环境和便捷的借阅服务。');

-- 4.3 系统参数
INSERT INTO sys_params (param_name, param_value, description) VALUES 
('max_borrow_days', '30', '系统最大借阅天数（兜底限制）'),
('max_borrow_num', '10', '系统最大借阅数量（兜底限制）'),
('fine_per_day', '0.5', '系统默认日罚金（元）'),
('max_renew_count', '1', '最大续借次数');

-- 4.4 书架数据
INSERT INTO bookshelf (name, location) VALUES 
('A区-计算机科学', '一楼东侧'),
('B区-文学小说', '二楼西侧'),
('C区-历史人文', '三楼北侧'),
('D区-自然科学', '三楼南侧');

-- 4.5 图书类型
INSERT INTO book_type (type_name, description) VALUES 
('计算机', '编程、算法、硬件等'),
('文学', '小说、散文、诗歌等'),
('历史', '中国史、世界史等'),
('科普', '自然科学普及读物');

-- 4.6 图书数据 (20条)
INSERT INTO book (barcode, name, isbn, book_type_id, author, publisher, format, binding, edition, price, stock, bookshelf_id, create_time) VALUES 
('BK001', 'Java编程思想', '9787111213826', 1, 'Bruce Eckel', '机械工业出版社', '16开', '平装', '第4版', 108.00, 5, 1, NOW()),
('BK002', 'Effective Java', '9787111576662', 1, 'Joshua Bloch', '机械工业出版社', '16开', '平装', '第3版', 119.00, 3, 1, NOW()),
('BK003', '算法导论', '9787111407010', 1, 'Thomas H.Cormen', '机械工业出版社', '16开', '精装', '第3版', 128.00, 2, 1, NOW()),
('BK004', '深入理解Java虚拟机', '9787111641247', 1, '周志明', '机械工业出版社', '16开', '平装', '第3版', 129.00, 4, 1, NOW()),
('BK005', '百年孤独', '9787544253994', 2, '加西亚·马尔克斯', '南海出版公司', '32开', '精装', '第1版', 55.00, 8, 2, NOW()),
('BK006', '活着', '9787506365437', 2, '余华', '作家出版社', '32开', '平装', '第1版', 28.00, 10, 2, NOW()),
('BK007', '三体全集', '9787229042066', 2, '刘慈欣', '重庆出版社', '16开', '平装', '第1版', 93.00, 6, 2, NOW()),
('BK008', '明朝那些事儿', '9787505737952', 3, '当年明月', '中国友谊出版公司', '16开', '平装', '增补版', 268.00, 3, 3, NOW()),
('BK009', '万历十五年', '9787101052039', 3, '黄仁宇', '中华书局', '32开', '精装', '增订本', 26.00, 5, 3, NOW()),
('BK010', '时间简史', '9787535732309', 4, '史蒂芬·霍金', '湖南科学技术出版社', '16开', '精装', '插图版', 48.00, 4, 4, NOW()),
('BK011', 'Head First Design Patterns', '9780596007126', 1, 'Eric Freeman', 'O\'Reilly', '16开', '平装', '1st', 98.00, 2, 1, NOW()),
('BK012', 'Clean Code', '9780132350884', 1, 'Robert C. Martin', 'Prentice Hall', '16开', '平装', '1st', 89.00, 3, 1, NOW()),
('BK013', '围城', '9787020125869', 2, '钱钟书', '人民文学出版社', '32开', '精装', '第1版', 39.00, 7, 2, NOW()),
('BK014', '红楼梦', '9787020002207', 2, '曹雪芹', '人民文学出版社', '32开', '平装', '第3版', 59.70, 12, 2, NOW()),
('BK015', '人类简史', '9787508647357', 3, '尤瓦尔·赫拉利', '中信出版社', '16开', '精装', '第1版', 68.00, 9, 3, NOW()),
('BK016', '枪炮、病菌与钢铁', '9787208063642', 3, '贾雷德·戴蒙德', '上海译文出版社', '16开', '平装', '第1版', 58.00, 4, 3, NOW()),
('BK017', '物种起源', '9787544648783', 4, '达尔文', '上海外语教育出版社', '16开', '平装', '第1版', 35.00, 5, 4, NOW()),
('BK018', '自私的基因', '9787508634159', 4, '理查德·道金斯', '中信出版社', '16开', '平装', '第1版', 68.00, 3, 4, NOW()),
('BK019', 'Spring实战', '9787115417305', 1, 'Craig Walls', '人民邮电出版社', '16开', '平装', '第4版', 89.00, 4, 1, NOW()),
('BK020', 'MySQL必知必会', '9787115191120', 1, 'Ben Forta', '人民邮电出版社', '32开', '平装', '第1版', 39.00, 6, 1, NOW());

-- 4.7 读者类型
INSERT INTO reader_type (type_name, max_borrow_num, max_borrow_days, fine_per_day) VALUES 
('学生', 5, 30, 0.10),
('教师', 10, 60, 0.05),
('校外人员', 3, 15, 0.50);

-- 4.8 读者数据
INSERT INTO reader (reader_barcode, name, gender, unit, phone, type_id, register_date, status) VALUES 
('R001', '张三', '男', '计算机学院', '13812345678', 1, NOW(), 1),
('R002', '李四', '女', '外语学院', '13987654321', 1, NOW(), 1),
('R003', '王五', '男', '数学系', '13700001111', 2, NOW(), 1),
('R004', '赵六', '女', '社会人士', '13666668888', 3, NOW(), 1),
('R005', '孙七', '男', '物理系', '13555554444', 1, NOW(), 0); -- 无效读者

-- 4.9 借阅记录 (包含正常、超期、已还)
-- 假设今天是 2025-12-22

-- 正常借阅中 (R001 借 BK001, 10天前借, 期限30天)
INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, status, operator_id) 
VALUES (1, 1, DATE_SUB(NOW(), INTERVAL 10 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 10 DAY), INTERVAL 30 DAY), 1, 2);

-- 即将到期 (R002 借 BK002, 28天前借, 期限30天, 剩2天)
INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, status, operator_id) 
VALUES (2, 2, DATE_SUB(NOW(), INTERVAL 28 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 28 DAY), INTERVAL 30 DAY), 1, 2);

-- 已超期 (R001 借 BK003, 40天前借, 期限30天, 超期10天)
INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, status, operator_id) 
VALUES (1, 3, DATE_SUB(NOW(), INTERVAL 40 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 40 DAY), INTERVAL 30 DAY), 3, 2);

-- 已归还 (R003 借 BK005, 正常归还)
INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, fine, operator_id) 
VALUES (3, 5, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 20 DAY), INTERVAL 60 DAY), NOW(), 2, 0, 2);

-- 已归还 (R004 借 BK006, 超期归还, 罚款)
INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, return_date, status, fine, operator_id) 
VALUES (4, 6, DATE_SUB(NOW(), INTERVAL 20 DAY), DATE_ADD(DATE_SUB(NOW(), INTERVAL 20 DAY), INTERVAL 15 DAY), NOW(), 2, 2.5, 2);

-- 更新库存 (手动扣减库存以匹配借阅记录)
UPDATE book SET stock = stock - 1 WHERE book_id IN (1, 2, 3);


-- TODO: 确保 admin 表密码字段够长（至少 100 字符）以存储加密后的 Hash
ALTER TABLE admin MODIFY COLUMN password VARCHAR(100);

-- TODO: 确保 book 表库存字段允许为 0 但不能为负（代码层已控制，数据库层可选）
-- ALTER TABLE book ADD CONSTRAINT check_stock CHECK (stock >= 0);