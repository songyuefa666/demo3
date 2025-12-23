package com.sss.demo3.dao;

import com.sss.demo3.entity.BookType;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookTypeDao {

    public List<BookType> findAll() {
        List<BookType> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM book_type";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                BookType t = new BookType();
                t.setId(rs.getInt("id"));
                t.setTypeName(rs.getString("type_name"));
                t.setDescription(rs.getString("description"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public BookType findById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BookType t = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT * FROM book_type WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                t = new BookType();
                t.setId(rs.getInt("id"));
                t.setTypeName(rs.getString("type_name"));
                t.setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return t;
    }

    public void insert(BookType t) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO book_type (type_name, description) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, t.getTypeName());
            ps.setString(2, t.getDescription());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(BookType t) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE book_type SET type_name=?, description=? WHERE id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, t.getTypeName());
            ps.setString(2, t.getDescription());
            ps.setInt(3, t.getId());
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
            String sql = "DELETE FROM book_type WHERE id=?";
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