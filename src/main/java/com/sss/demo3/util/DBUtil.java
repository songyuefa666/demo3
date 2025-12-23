package com.sss.demo3.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
    private static Properties props = new Properties();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    static {
        try (InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in == null) {
                throw new RuntimeException("db.properties not found in classpath");
            }
            props.load(in);
            Class.forName(props.getProperty("driver"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Load db.properties failed: " + e.getMessage(), e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn == null || conn.isClosed()) {
            String url = props.getProperty("url");
            String user = props.getProperty("username");
            String pass = props.getProperty("password");
            
            if (url == null) throw new SQLException("DB url property is missing");
            
            conn = DriverManager.getConnection(url, user, pass);
            if (conn == null) {
                throw new SQLException("DriverManager returned null connection");
            }
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        if (conn != null) {
            conn.setAutoCommit(false);
        }
    }

    public static void commitTransaction() throws SQLException {
        Connection conn = threadLocal.get();
        if (conn != null) {
            conn.commit();
            conn.close();
            threadLocal.remove();
        }
    }

    public static void rollbackTransaction() {
        Connection conn = threadLocal.get();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                threadLocal.remove();
            }
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            // Do not close connection if it's in transaction (managed by commit/rollback)
            // But if it was a simple query without transaction, we might want to close it?
            // For this simple implementation:
            // If we are using threadLocal for everything, we should be careful.
            // Requirement says "Get connection, close resources" + "Simple transaction control".
            // Let's assume if autoCommit is true, we close it. If false (transaction), we don't.
            if (conn != null) {
                if (conn.getAutoCommit()) {
                     conn.close();
                     threadLocal.remove();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Explicit close for non-transactional usage if needed, 
    // but the above close() handles standard usage.
}