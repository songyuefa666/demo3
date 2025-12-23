package com.sss.demo3.dao;

import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SystemLogDao {

    public void insert(SystemLog log) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO system_log (operator_id, operation_type, content, create_time) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, log.getOperatorId());
            ps.setString(2, log.getOperationType());
            ps.setString(3, log.getContent());
            ps.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            // Note: If transaction is active, DBUtil.close won't close conn.
            DBUtil.close(conn, ps, null);
        }
    }
}