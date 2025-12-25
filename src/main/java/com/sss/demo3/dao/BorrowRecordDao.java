package com.sss.demo3.dao;

import com.sss.demo3.dto.BookRanking;
import com.sss.demo3.entity.BorrowRecord;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDao {

    public List<BorrowRecord> findExpiringOrOverdue(int days) {
        return findExpiringOrOverdue(days, null, null, null);
    }

    public List<BorrowRecord> findExpiringOrOverdue(int days, Integer readerTypeId, String startDate, String endDate) {
        List<BorrowRecord> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE br.status = 1 AND (br.due_date < NOW() OR DATEDIFF(br.due_date, NOW()) <= ?) ");

            if (readerTypeId != null) {
                sql.append("AND r.type_id = ? ");
            }
            if (startDate != null && !startDate.isEmpty()) {
                sql.append("AND br.borrow_date >= ? ");
            }
            if (endDate != null && !endDate.isEmpty()) {
                sql.append("AND br.borrow_date <= ? ");
            }

            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            ps.setInt(idx++, days);
            if (readerTypeId != null) {
                ps.setInt(idx++, readerTypeId);
            }
            if (startDate != null && !startDate.isEmpty()) {
                ps.setString(idx++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.isEmpty()) {
                ps.setString(idx++, endDate + " 23:59:59");
            }
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

    public List<BorrowRecord> searchHistory(String readerBarcode, String bookName, String startDate, String endDate) {
        List<BorrowRecord> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE 1=1 ");
            
            if (readerBarcode != null && !readerBarcode.isEmpty()) {
                sql.append("AND r.reader_barcode = ? ");
            }
            if (bookName != null && !bookName.isEmpty()) {
                sql.append("AND b.name LIKE ? ");
            }
            if (startDate != null && !startDate.isEmpty()) {
                sql.append("AND br.borrow_date >= ? ");
            }
            if (endDate != null && !endDate.isEmpty()) {
                sql.append("AND br.borrow_date <= ? ");
            }
            sql.append("ORDER BY br.borrow_id DESC");

            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (readerBarcode != null && !readerBarcode.isEmpty()) {
                ps.setString(idx++, readerBarcode);
            }
            if (bookName != null && !bookName.isEmpty()) {
                ps.setString(idx++, "%" + bookName + "%");
            }
            if (startDate != null && !startDate.isEmpty()) {
                ps.setString(idx++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.isEmpty()) {
                ps.setString(idx++, endDate + " 23:59:59");
            }
            
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

    public List<BookRanking> getBookRanking(String startDate, String endDate) {
        List<BookRanking> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            StringBuilder sql = new StringBuilder("SELECT b.name, b.isbn, b.author, b.publisher, COUNT(br.borrow_id) as borrow_count " +
                         "FROM borrow_record br " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE 1=1 ");
            
            if (startDate != null && !startDate.isEmpty()) {
                sql.append("AND br.borrow_date >= ? ");
            }
            if (endDate != null && !endDate.isEmpty()) {
                sql.append("AND br.borrow_date <= ? ");
            }
            sql.append("GROUP BY b.book_id ORDER BY borrow_count DESC");

            ps = conn.prepareStatement(sql.toString());
            int idx = 1;
            if (startDate != null && !startDate.isEmpty()) {
                ps.setString(idx++, startDate + " 00:00:00");
            }
            if (endDate != null && !endDate.isEmpty()) {
                ps.setString(idx++, endDate + " 23:59:59");
            }
            
            rs = ps.executeQuery();
            while (rs.next()) {
                BookRanking br = new BookRanking();
                br.setBookName(rs.getString("name"));
                br.setIsbn(rs.getString("isbn"));
                br.setAuthor(rs.getString("author"));
                br.setPublisher(rs.getString("publisher"));
                br.setBorrowCount(rs.getInt("borrow_count"));
                list.add(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    public List<BorrowRecord> findByReaderId(Integer readerId) {
        List<BorrowRecord> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE br.reader_id = ? ORDER BY br.borrow_id DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
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

    public List<BorrowRecord> findActiveByReaderId(Integer readerId) {
        List<BorrowRecord> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE br.reader_id = ? AND br.status IN (1, 3) ORDER BY br.borrow_id DESC";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
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

    public BorrowRecord findActiveByBookBarcode(String bookBarcode) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BorrowRecord br = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE b.barcode = ? AND br.status IN (1, 3) " +
                         "ORDER BY br.borrow_id DESC LIMIT 1";
            ps = conn.prepareStatement(sql);
            ps.setString(1, bookBarcode);
            rs = ps.executeQuery();
            if (rs.next()) {
                br = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return br;
    }

    public BorrowRecord findById(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        BorrowRecord br = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT br.*, r.reader_barcode, r.name as reader_name, b.barcode as book_barcode, b.name as book_name " +
                         "FROM borrow_record br " +
                         "JOIN reader r ON br.reader_id = r.reader_id " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE br.borrow_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                br = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return br;
    }

    public int countActiveByReaderId(Integer readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM borrow_record WHERE reader_id = ? AND status IN (1, 3)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
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

    public int countOverdueByReaderId(Integer readerId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM borrow_record WHERE reader_id = ? AND status = 3";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, readerId);
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

    public void insert(BorrowRecord br) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO borrow_record (reader_id, book_id, borrow_date, due_date, renew_times, status, operator_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, br.getReaderId());
            ps.setInt(2, br.getBookId());
            ps.setTimestamp(3, new Timestamp(br.getBorrowDate().getTime()));
            ps.setTimestamp(4, new Timestamp(br.getDueDate().getTime()));
            ps.setInt(5, br.getRenewTimes());
            ps.setInt(6, br.getStatus());
            ps.setLong(7, br.getOperatorId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(BorrowRecord br) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE borrow_record SET due_date=?, return_date=?, renew_times=?, status=?, fine=? WHERE borrow_id=?";
            ps = conn.prepareStatement(sql);
            ps.setTimestamp(1, new Timestamp(br.getDueDate().getTime()));
            if (br.getReturnDate() != null) {
                ps.setTimestamp(2, new Timestamp(br.getReturnDate().getTime()));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }
            ps.setInt(3, br.getRenewTimes());
            ps.setInt(4, br.getStatus());
            if (br.getFine() != null) {
                ps.setDouble(5, br.getFine());
            } else {
                ps.setNull(5, java.sql.Types.DOUBLE);
            }
            ps.setLong(6, br.getBorrowId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void updateOverdueStatus() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE borrow_record SET status = 3 WHERE status = 1 AND due_date < NOW()";
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    private BorrowRecord mapRow(ResultSet rs) throws SQLException {
        BorrowRecord br = new BorrowRecord();
        br.setBorrowId(rs.getLong("borrow_id"));
        br.setReaderId(rs.getInt("reader_id"));
        br.setReaderBarcode(rs.getString("reader_barcode"));
        br.setReaderName(rs.getString("reader_name"));
        br.setBookId(rs.getInt("book_id"));
        br.setBookBarcode(rs.getString("book_barcode"));
        br.setBookName(rs.getString("book_name"));
        br.setBorrowDate(rs.getTimestamp("borrow_date"));
        br.setDueDate(rs.getTimestamp("due_date"));
        br.setReturnDate(rs.getTimestamp("return_date"));
        br.setRenewTimes(rs.getInt("renew_times"));
        br.setStatus(rs.getInt("status"));
        br.setFine(rs.getDouble("fine"));
        br.setOperatorId(rs.getLong("operator_id"));
        return br;
    }

    public List<BookRanking> findRanking(String startDate, String endDate) {
        List<BookRanking> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.name, b.isbn, b.author, b.publisher, COUNT(br.borrow_id) as cnt " +
                         "FROM borrow_record br " +
                         "JOIN book b ON br.book_id = b.book_id " +
                         "WHERE 1=1 ";
            
            if (startDate != null && !startDate.isEmpty()) {
                sql += "AND br.borrow_date >= ? ";
            }
            if (endDate != null && !endDate.isEmpty()) {
                sql += "AND br.borrow_date <= ? ";
            }
            
            sql += "GROUP BY b.book_id ORDER BY cnt DESC LIMIT 20";
            
            ps = conn.prepareStatement(sql);
            int idx = 1;
            if (startDate != null && !startDate.isEmpty()) {
                ps.setString(idx++, startDate);
            }
            if (endDate != null && !endDate.isEmpty()) {
                ps.setString(idx++, endDate + " 23:59:59");
            }
            
            rs = ps.executeQuery();
            while (rs.next()) {
                BookRanking br = new BookRanking();
                br.setBookName(rs.getString("name"));
                br.setIsbn(rs.getString("isbn"));
                br.setAuthor(rs.getString("author"));
                br.setPublisher(rs.getString("publisher"));
                br.setBorrowCount(rs.getInt("cnt"));
                list.add(br);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }
}
