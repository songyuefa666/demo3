package com.sss.demo3.dao;

import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderTypeDao {

    public List<ReaderType> findAll() {
        List<ReaderType> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reader_type";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ReaderType rt = new ReaderType();
                rt.setTypeId(rs.getInt("type_id"));
                rt.setTypeName(rs.getString("type_name"));
                rt.setMaxBorrowNum(rs.getInt("max_borrow_num"));
                rt.setMaxBorrowDays(rs.getInt("max_borrow_days"));
                rt.setFinePerDay(rs.getDouble("fine_per_day"));
                list.add(rt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public ReaderType findById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ReaderType rt = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM reader_type WHERE type_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                rt = new ReaderType();
                rt.setTypeId(rs.getInt("type_id"));
                rt.setTypeName(rs.getString("type_name"));
                rt.setMaxBorrowNum(rs.getInt("max_borrow_num"));
                rt.setMaxBorrowDays(rs.getInt("max_borrow_days"));
                rt.setFinePerDay(rs.getDouble("fine_per_day"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return rt;
    }

    public void insert(ReaderType rt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO reader_type (type_name, max_borrow_num, max_borrow_days, fine_per_day) VALUES (?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, rt.getTypeName());
            ps.setInt(2, rt.getMaxBorrowNum());
            ps.setInt(3, rt.getMaxBorrowDays());
            ps.setDouble(4, rt.getFinePerDay());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(ReaderType rt) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE reader_type SET type_name=?, max_borrow_num=?, max_borrow_days=?, fine_per_day=? WHERE type_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, rt.getTypeName());
            ps.setInt(2, rt.getMaxBorrowNum());
            ps.setInt(3, rt.getMaxBorrowDays());
            ps.setDouble(4, rt.getFinePerDay());
            ps.setInt(5, rt.getTypeId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void delete(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM reader_type WHERE type_id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }
}