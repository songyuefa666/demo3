package com.sss.demo3.dao;

import com.sss.demo3.entity.Book;
import com.sss.demo3.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public List<Book> findByPage(int page, int pageSize, String keyword) {
        List<Book> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.*, bt.type_name, bs.name as bookshelf_name FROM book b " +
                         "LEFT JOIN book_type bt ON b.book_type_id = bt.id " +
                         "LEFT JOIN bookshelf bs ON b.bookshelf_id = bs.id ";
            
            if (keyword != null && !keyword.isEmpty()) {
                sql += "WHERE b.name LIKE ? OR b.isbn LIKE ? OR b.author LIKE ? OR b.publisher LIKE ? ";
            }
            
            sql += "ORDER BY b.book_id DESC LIMIT ?, ?";
            
            ps = conn.prepareStatement(sql);
            int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
            }
            ps.setInt(idx++, (page - 1) * pageSize);
            ps.setInt(idx++, pageSize);
            
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

    public int count(String keyword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM book b ";
             if (keyword != null && !keyword.isEmpty()) {
                sql += "WHERE b.name LIKE ? OR b.isbn LIKE ? OR b.author LIKE ? OR b.publisher LIKE ? ";
            }
            ps = conn.prepareStatement(sql);
             int idx = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String kw = "%" + keyword + "%";
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
            }
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

    public Book findById(Integer id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.*, bt.type_name, bs.name as bookshelf_name FROM book b " +
                         "LEFT JOIN book_type bt ON b.book_type_id = bt.id " +
                         "LEFT JOIN bookshelf bs ON b.bookshelf_id = bs.id " +
                         "WHERE b.book_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                book = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return book;
    }

    public Book findByBarcode(String barcode) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Book book = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "SELECT b.*, bt.type_name, bs.name as bookshelf_name FROM book b " +
                         "LEFT JOIN book_type bt ON b.book_type_id = bt.id " +
                         "LEFT JOIN bookshelf bs ON b.bookshelf_id = bs.id " +
                         "WHERE b.barcode = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, barcode);
            rs = ps.executeQuery();
            if (rs.next()) {
                book = mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, rs);
        }
        return book;
    }

    public void updateStock(Integer bookId, int delta) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql;
            if (delta < 0) {
                // Borrow: Ensure stock doesn't go below 0
                sql = "UPDATE book SET stock = stock + ? WHERE book_id = ? AND stock + ? >= 0";
            } else {
                // Return: Always allowed
                sql = "UPDATE book SET stock = stock + ? WHERE book_id = ?";
            }
            
            ps = conn.prepareStatement(sql);
            ps.setInt(1, delta);
            ps.setInt(2, bookId);
            if (delta < 0) {
                ps.setInt(3, delta);
            }
            
            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Stock update failed (Insufficient stock or Book ID not found)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void insert(Book b) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO book (barcode, name, isbn, book_type_id, author, publisher, format, binding, edition, price, stock, bookshelf_id, create_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getBarcode());
            ps.setString(2, b.getName());
            ps.setString(3, b.getIsbn());
            ps.setInt(4, b.getBookTypeId());
            ps.setString(5, b.getAuthor());
            ps.setString(6, b.getPublisher());
            ps.setString(7, b.getFormat());
            ps.setString(8, b.getBinding());
            ps.setString(9, b.getEdition());
            ps.setDouble(10, b.getPrice());
            ps.setInt(11, b.getStock());
            ps.setInt(12, b.getBookshelfId());
            ps.setTimestamp(13, new java.sql.Timestamp(new java.util.Date().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("DB Error", e);
        } finally {
            DBUtil.close(conn, ps, null);
        }
    }

    public void update(Book b) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE book SET barcode=?, name=?, isbn=?, book_type_id=?, author=?, publisher=?, format=?, binding=?, edition=?, price=?, stock=?, bookshelf_id=? WHERE book_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, b.getBarcode());
            ps.setString(2, b.getName());
            ps.setString(3, b.getIsbn());
            ps.setInt(4, b.getBookTypeId());
            ps.setString(5, b.getAuthor());
            ps.setString(6, b.getPublisher());
            ps.setString(7, b.getFormat());
            ps.setString(8, b.getBinding());
            ps.setString(9, b.getEdition());
            ps.setDouble(10, b.getPrice());
            ps.setInt(11, b.getStock());
            ps.setInt(12, b.getBookshelfId());
            ps.setInt(13, b.getBookId());
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
            String sql = "DELETE FROM book WHERE book_id=?";
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

    private Book mapRow(ResultSet rs) throws SQLException {
        Book b = new Book();
        b.setBookId(rs.getInt("book_id"));
        b.setBarcode(rs.getString("barcode"));
        b.setName(rs.getString("name"));
        b.setIsbn(rs.getString("isbn"));
        b.setBookTypeId(rs.getInt("book_type_id"));
        b.setBookTypeName(rs.getString("type_name"));
        b.setAuthor(rs.getString("author"));
        b.setPublisher(rs.getString("publisher"));
        b.setFormat(rs.getString("format"));
        b.setBinding(rs.getString("binding"));
        b.setEdition(rs.getString("edition"));
        b.setPrice(rs.getDouble("price"));
        b.setStock(rs.getInt("stock"));
        b.setBookshelfId(rs.getInt("bookshelf_id"));
        b.setBookshelfName(rs.getString("bookshelf_name"));
        b.setCreateTime(rs.getTimestamp("create_time"));
        return b;
    }
}