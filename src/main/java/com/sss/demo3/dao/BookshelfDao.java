package com.sss.demo3.dao;

import com.sss.demo3.entity.Bookshelf;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookshelfDao {

    public List<Bookshelf> findAll() {
        List<Bookshelf> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM bookshelf";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Bookshelf b = new Bookshelf();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setLocation(rs.getString("location"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public Bookshelf findById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM bookshelf WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Bookshelf b = new Bookshelf();
                b.setId(rs.getInt("id"));
                b.setName(rs.getString("name"));
                b.setLocation(rs.getString("location"));
                return b;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return null;
    }

    public void insert(Bookshelf b) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO bookshelf (name, location) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setString(2, b.getLocation());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Bookshelf b) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE bookshelf SET name=?, location=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setString(2, b.getLocation());
            ps.setInt(3, b.getId());
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
            String sql = "DELETE FROM bookshelf WHERE id=?";
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