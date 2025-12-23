package com.sss.demo3.dao;

import com.sss.demo3.entity.Reader;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderDao {

    public List<Reader> findByPage(int page, int pageSize) {
        List<Reader> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            // Join with reader_type to get type name
            String sql = "SELECT r.*, rt.type_name FROM reader r LEFT JOIN reader_type rt ON r.type_id = rt.type_id ORDER BY r.reader_id DESC LIMIT ?, ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
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

    public int count() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM reader";
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

    public Reader findById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT r.*, rt.type_name FROM reader r LEFT JOIN reader_type rt ON r.type_id = rt.type_id WHERE r.reader_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                reader = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return reader;
    }

    public boolean isBarcodeExists(String barcode, Integer excludeId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM reader WHERE reader_barcode = ?";
            if (excludeId != null) {
                sql += " AND reader_id != ?";
            }
            ps = conn.prepareStatement(sql);
            ps.setString(1, barcode);
            if (excludeId != null) {
                ps.setInt(2, excludeId);
            }
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return false;
    }

    public Reader findByBarcode(String barcode) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Reader reader = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT r.*, rt.type_name, rt.max_borrow_num, rt.max_borrow_days, rt.fine_per_day " +
                         "FROM reader r LEFT JOIN reader_type rt ON r.type_id = rt.type_id WHERE r.reader_barcode = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, barcode);
            rs = ps.executeQuery();
            if (rs.next()) {
                reader = mapRowWithExtra(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return reader;
    }

    private Reader mapRowWithExtra(ResultSet rs) throws SQLException {
        Reader r = mapRow(rs);
        r.setMaxBorrowNum(rs.getInt("max_borrow_num"));
        r.setMaxBorrowDays(rs.getInt("max_borrow_days"));
        r.setFinePerDay(rs.getDouble("fine_per_day"));
        return r;
    }

    public void insert(Reader reader) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO reader (reader_barcode, name, gender, unit, phone, type_id, status, register_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reader.getReaderBarcode());
            ps.setString(2, reader.getName());
            ps.setString(3, reader.getGender());
            ps.setString(4, reader.getUnit());
            ps.setString(5, reader.getPhone());
            ps.setInt(6, reader.getTypeId());
            ps.setInt(7, reader.getStatus());
            ps.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Reader reader) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE reader SET reader_barcode=?, name=?, gender=?, unit=?, phone=?, type_id=?, status=? WHERE reader_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, reader.getReaderBarcode());
            ps.setString(2, reader.getName());
            ps.setString(3, reader.getGender());
            ps.setString(4, reader.getUnit());
            ps.setString(5, reader.getPhone());
            ps.setInt(6, reader.getTypeId());
            ps.setInt(7, reader.getStatus());
            ps.setInt(8, reader.getReaderId());
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
            String sql = "DELETE FROM reader WHERE reader_id=?";
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

    private Reader mapRow(ResultSet rs) throws SQLException {
        Reader r = new Reader();
        r.setReaderId(rs.getInt("reader_id"));
        r.setReaderBarcode(rs.getString("reader_barcode"));
        r.setName(rs.getString("name"));
        r.setGender(rs.getString("gender"));
        r.setUnit(rs.getString("unit"));
        r.setPhone(rs.getString("phone"));
        r.setTypeId(rs.getInt("type_id"));
        r.setTypeName(rs.getString("type_name"));
        r.setRegisterDate(rs.getTimestamp("register_date"));
        r.setStatus(rs.getInt("status"));
        return r;
    }
}