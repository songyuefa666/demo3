package com.sss.demo3.servlet;

import com.sss.demo3.dao.BorrowRecordDao;
import com.sss.demo3.dao.ReaderDao;
import com.sss.demo3.dao.ReaderTypeDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.BorrowRecord;
import com.sss.demo3.entity.Reader;
import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.service.BorrowService;
import com.sss.demo3.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BorrowServlet", urlPatterns = "/borrow")
public class BorrowServlet extends BaseServlet {

    private BorrowService borrowService = new BorrowService();
    private ReaderDao readerDao = new ReaderDao();
    private ReaderTypeDao readerTypeDao = new ReaderTypeDao();
    private BorrowRecordDao borrowRecordDao = new BorrowRecordDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Show Borrow Form
        req.getRequestDispatcher("/WEB-INF/views/borrow/borrow_form.jsp").forward(req, resp);
    }

    public void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Sync overdue status first (Graceful degradation)
        try {
            borrowService.syncOverdueStatus();
        } catch (Exception e) {
            e.printStackTrace(); // Log error but allow search to proceed
            req.setAttribute("warning", "Status sync failed: " + e.getMessage());
        }

        String readerBarcode = req.getParameter("readerBarcode");
        if (readerBarcode != null && !readerBarcode.isEmpty()) {
            Reader reader = readerDao.findByBarcode(readerBarcode);
            if (reader != null) {
                req.setAttribute("reader", reader);
                
                // Fetch ReaderType for limits display (safer than relying on transient fields)
                ReaderType rt = readerTypeDao.findById(reader.getTypeId());
                req.setAttribute("readerType", rt);

                // Load current borrowing records
                List<BorrowRecord> list = borrowRecordDao.findActiveByReaderId(reader.getReaderId());
                req.setAttribute("list", list);
            } else {
                req.setAttribute("error", "Reader not found");
            }
        }
        req.getRequestDispatcher("/WEB-INF/views/borrow/borrow_form.jsp").forward(req, resp);
    }

    public void borrow(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String readerBarcode = req.getParameter("readerBarcode");
        String bookBarcode = req.getParameter("bookBarcode");
        
        if (!ValidationUtils.isValidBarcode(readerBarcode)) {
             req.setAttribute("error", "Invalid reader barcode format");
             search(req, resp); // Reload info
             return;
        }
        if (!ValidationUtils.isValidBarcode(bookBarcode)) {
             req.setAttribute("error", "Invalid book barcode format");
             search(req, resp);
             return;
        }

        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        try {
            borrowService.borrowBook(readerBarcode, bookBarcode, admin);
            req.setAttribute("message", "Borrow Success!");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        
        // Reload info
        search(req, resp);
    }

    public void returnBook(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        long id = ValidationUtils.parseLong(idStr, 0);
        
        if (id <= 0) {
             req.setAttribute("error", "Invalid Borrow ID");
             search(req, resp);
             return;
        }

        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        try {
            borrowService.returnBook(id, admin);
            req.setAttribute("message", "Return Success!");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        
        // Reload info (need readerBarcode)
        String readerBarcode = req.getParameter("readerBarcode");
        if (readerBarcode != null) {
             search(req, resp);
        } else {
             // If called from elsewhere, maybe just redirect to index
             index(req, resp);
        }
    }

    public void renew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        try {
            borrowService.renewBook(Long.parseLong(idStr), admin);
            req.setAttribute("message", "Renew Success!");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
        }
        
        String readerBarcode = req.getParameter("readerBarcode");
        if (readerBarcode != null) {
             search(req, resp);
        } else {
             index(req, resp);
        }
    }
}