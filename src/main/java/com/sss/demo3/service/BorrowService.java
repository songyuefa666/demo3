package com.sss.demo3.service;

import com.sss.demo3.dao.BookDao;
import com.sss.demo3.dao.BorrowRecordDao;
import com.sss.demo3.dao.ReaderDao;
import com.sss.demo3.dao.ReaderTypeDao;
import com.sss.demo3.dao.SysParamDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.Book;
import com.sss.demo3.entity.BorrowRecord;
import com.sss.demo3.entity.Reader;
import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.entity.SysParam;
import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.DBUtil;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BorrowService {

    private BookDao bookDao = new BookDao();
    private ReaderDao readerDao = new ReaderDao();
    private ReaderTypeDao readerTypeDao = new ReaderTypeDao();
    private BorrowRecordDao borrowRecordDao = new BorrowRecordDao();
    private SysParamDao sysParamDao = new SysParamDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    public void syncOverdueStatus() {
        borrowRecordDao.updateOverdueStatus();
    }

    public void borrowBook(String readerBarcode, String bookBarcode, Admin operator) throws Exception {
        try {
            DBUtil.beginTransaction();

            // 1. Check Reader
            Reader reader = readerDao.findByBarcode(readerBarcode);
            if (reader == null) throw new Exception("Reader not found");
            if (reader.getStatus() != 1) throw new Exception("Reader is invalid");

            // 2. Check Book
            Book book = bookDao.findByBarcode(bookBarcode);
            if (book == null) throw new Exception("Book not found");
            if (book.getStock() <= 0) throw new Exception("Book out of stock");

            // 3. Check Overdue
            int overdueCount = borrowRecordDao.countOverdueByReaderId(reader.getReaderId());
            if (overdueCount > 0) throw new Exception("Reader has overdue books");

            // 4. Check Borrow Limit
            ReaderType rt = readerTypeDao.findById(reader.getTypeId());
            int maxBorrowNum = rt.getMaxBorrowNum();
            
            // System param limit
            List<SysParam> params = sysParamDao.getAll();
            int sysMaxBorrowNum = Integer.MAX_VALUE;
            for (SysParam p : params) {
                if ("max_borrow_num".equals(p.getParamName())) {
                    sysMaxBorrowNum = Integer.parseInt(p.getParamValue());
                    break;
                }
            }
            int limit = Math.min(maxBorrowNum, sysMaxBorrowNum);
            
            int currentBorrowCount = borrowRecordDao.countActiveByReaderId(reader.getReaderId());
            if (currentBorrowCount >= limit) throw new Exception("Borrow limit exceeded (Max: " + limit + ")");

            // 5. Create Borrow Record
            BorrowRecord br = new BorrowRecord();
            br.setReaderId(reader.getReaderId());
            br.setBookId(book.getBookId());
            br.setBorrowDate(new Date());
            
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, rt.getMaxBorrowDays());
            br.setDueDate(cal.getTime());
            
            br.setRenewTimes(0);
            br.setStatus(1); // Borrowing
            br.setOperatorId(operator.getAdminId());
            
            borrowRecordDao.insert(br);

            // 6. Update Stock
            bookDao.updateStock(book.getBookId(), -1);

            // 7. Log
            SystemLog log = new SystemLog();
            log.setOperatorId(operator.getAdminId());
            log.setOperationType("BORROW");
            log.setContent("Reader " + readerBarcode + " borrowed " + bookBarcode);
            systemLogDao.insert(log);

            DBUtil.commitTransaction();
        } catch (Exception e) {
            DBUtil.rollbackTransaction();
            throw e;
        }
    }

    public void returnBook(Long borrowId, Admin operator) throws Exception {
        try {
            DBUtil.beginTransaction();

            BorrowRecord br = borrowRecordDao.findById(borrowId);
            if (br == null) throw new Exception("Record not found");
            if (br.getStatus() == 2) throw new Exception("Book already returned");

            // Calculate Fine
            Date now = new Date();
            br.setReturnDate(now);
            br.setStatus(2); // Returned

            double fine = 0;
            if (now.after(br.getDueDate())) {
                long diff = now.getTime() - br.getDueDate().getTime();
                long days = diff / (1000 * 60 * 60 * 24);
                if (days > 0) {
                    Reader reader = readerDao.findById(br.getReaderId());
                    ReaderType rt = readerTypeDao.findById(reader.getTypeId());
                    fine = days * rt.getFinePerDay();
                }
            }
            br.setFine(fine);

            borrowRecordDao.update(br);
            bookDao.updateStock(br.getBookId(), 1);

            SystemLog log = new SystemLog();
            log.setOperatorId(operator.getAdminId());
            log.setOperationType("RETURN");
            log.setContent("Returned borrow_id: " + borrowId + ", fine: " + fine);
            systemLogDao.insert(log);

            DBUtil.commitTransaction();
        } catch (Exception e) {
            DBUtil.rollbackTransaction();
            throw e;
        }
    }

    public void renewBook(Long borrowId, Admin operator) throws Exception {
        try {
            DBUtil.beginTransaction();

            BorrowRecord br = borrowRecordDao.findById(borrowId);
            if (br == null) throw new Exception("Record not found");
            if (br.getStatus() != 1) throw new Exception("Only borrowing books can be renewed");
            
            Date now = new Date();
            if (now.after(br.getDueDate())) throw new Exception("Overdue books cannot be renewed");

            // Check Max Renew Times
            List<SysParam> params = sysParamDao.getAll();
            int maxRenewTimes = 0;
            for (SysParam p : params) {
                if ("max_renew_count".equals(p.getParamName())) {
                    maxRenewTimes = Integer.parseInt(p.getParamValue());
                    break;
                }
            }
            
            if (br.getRenewTimes() >= maxRenewTimes) throw new Exception("Max renew times exceeded");

            // Update Due Date
            Reader reader = readerDao.findById(br.getReaderId());
            ReaderType rt = readerTypeDao.findById(reader.getTypeId());
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(br.getDueDate());
            cal.add(Calendar.DAY_OF_YEAR, rt.getMaxBorrowDays()); // Extend by max borrow days
            br.setDueDate(cal.getTime());
            
            br.setRenewTimes(br.getRenewTimes() + 1);
            
            borrowRecordDao.update(br);

            SystemLog log = new SystemLog();
            log.setOperatorId(operator.getAdminId());
            log.setOperationType("RENEW");
            log.setContent("Renewed borrow_id: " + borrowId);
            systemLogDao.insert(log);

            DBUtil.commitTransaction();
        } catch (Exception e) {
            DBUtil.rollbackTransaction();
            throw e;
        }
    }
}