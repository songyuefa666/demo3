package com.sss.demo3.dao;

import com.sss.demo3.entity.Admin;
import com.sss.demo3.util.DBUtil;
import com.sss.demo3.util.SecurityUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    public Admin findByUsername(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM admin WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                admin = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return admin;
    }

    // Deprecated: Use findByUsername + SecurityUtils.verify
    public Admin findByUsernameAndPassword(String username, String password) {
        // Fallback for legacy code if any
        Admin admin = findByUsername(username);
        if (admin != null && SecurityUtils.verify(password, admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public java.util.List<Admin> findAll() {
        java.util.List<Admin> list = new java.util.ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM admin";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public Admin findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM admin WHERE admin_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                admin = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return admin;
    }

    public void insert(Admin admin) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO admin (username, password, real_name, phone, role, register_date) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getRealName());
            ps.setString(4, admin.getPhone());
            ps.setString(5, admin.getRole());
            ps.setTimestamp(6, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Admin admin) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE admin SET username=?, password=?, real_name=?, phone=?, role=? WHERE admin_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getUsername());
            ps.setString(2, admin.getPassword());
            ps.setString(3, admin.getRealName());
            ps.setString(4, admin.getPhone());
            ps.setString(5, admin.getRole());
            ps.setLong(6, admin.getAdminId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void delete(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM admin WHERE admin_id=?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public int countSysAdmins() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM admin WHERE role = 'SYS_ADMIN'";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return count;
    }

    public void updatePassword(Long id, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE admin SET password=? WHERE admin_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, SecurityUtils.encrypt(password)); // Encrypt here!
            ps.setLong(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    private Admin mapRow(ResultSet rs) throws SQLException {
        Admin admin = new Admin();
        admin.setAdminId(rs.getLong("admin_id"));
        admin.setUsername(rs.getString("username"));
        admin.setPassword(rs.getString("password"));
        admin.setRealName(rs.getString("real_name"));
        admin.setPhone(rs.getString("phone"));
        admin.setRegisterDate(rs.getTimestamp("register_date"));
        
        String role = rs.getString("role");
        if (role == null || role.isEmpty()) {
            role = "OPERATOR"; 
        }
        admin.setRole(role);
        return admin;
    }
}