package com.sss.demo3.dao;

import com.sss.demo3.entity.LibraryInfo;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryInfoDao {

    public LibraryInfo get() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM library_info LIMIT 1";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                LibraryInfo info = new LibraryInfo();
                info.setId(rs.getInt("id"));
                info.setLibraryName(rs.getString("library_name"));
                info.setAddress(rs.getString("address"));
                info.setPhone(rs.getString("phone"));
                info.setOpenHours(rs.getString("open_hours"));
                info.setIntroduction(rs.getString("introduction"));
                return info;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public void update(LibraryInfo info) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            // Check if exists
            LibraryInfo exist = get();
            if (exist == null) {
                String sql = "INSERT INTO library_info (library_name, address, phone, open_hours, introduction) VALUES (?, ?, ?, ?, ?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, info.getLibraryName());
                ps.setString(2, info.getAddress());
                ps.setString(3, info.getPhone());
                ps.setString(4, info.getOpenHours());
                ps.setString(5, info.getIntroduction());
                ps.executeUpdate();
            } else {
                String sql = "UPDATE library_info SET library_name=?, address=?, phone=?, open_hours=?, introduction=? WHERE id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, info.getLibraryName());
                ps.setString(2, info.getAddress());
                ps.setString(3, info.getPhone());
                ps.setString(4, info.getOpenHours());
                ps.setString(5, info.getIntroduction());
                ps.setInt(6, exist.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}