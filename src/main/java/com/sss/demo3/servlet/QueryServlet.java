package com.sss.demo3.servlet;

import com.sss.demo3.dao.BookDao;
import com.sss.demo3.dao.BorrowRecordDao;
import com.sss.demo3.dao.ReaderTypeDao;
import com.sss.demo3.dto.BookRanking;
import com.sss.demo3.entity.Book;
import com.sss.demo3.entity.BorrowRecord;
import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "QueryServlet", urlPatterns = "/query/*")
public class QueryServlet extends BaseServlet {

    private BorrowRecordDao borrowRecordDao = new BorrowRecordDao();
    private BookDao bookDao = new BookDao();
    private ReaderTypeDao readerTypeDao = new ReaderTypeDao();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        // Sync overdue status before any query
        try {
            borrowRecordDao.updateOverdueStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String uri = req.getRequestURI();
        String action = uri.substring(uri.lastIndexOf("/") + 1);
        
        if ("expiry".equals(action)) {
            expiry(req, resp);
        } else if ("history".equals(action)) {
            history(req, resp);
        } else if ("ranking".equals(action)) {
            ranking(req, resp);
        } else if ("book_query".equals(action)) {
            bookQuery(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    public void bookQuery(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = ValidationUtils.parseInt(req.getParameter("page"), 1);
        int pageSize = 10;
        String keyword = req.getParameter("keyword");
        
        // Reuse BookDao but display in read-only view
        List<Book> list = bookDao.findByPage(page, pageSize, keyword);
        int total = bookDao.count(keyword);
        int totalPage = (int) Math.ceil((double) total / pageSize);

        req.setAttribute("list", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/query/book_list.jsp").forward(req, resp);
    }

    public void expiry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Default 3 days
        String readerTypeIdStr = req.getParameter("readerTypeId");
        int typeIdVal = ValidationUtils.parseInt(readerTypeIdStr, 0);
        Integer readerTypeId = typeIdVal > 0 ? typeIdVal : null;
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        List<BorrowRecord> list = borrowRecordDao.findExpiringOrOverdue(3, readerTypeId, startDate, endDate);
        req.setAttribute("list", list);
        List<ReaderType> types = readerTypeDao.findAll();
        req.setAttribute("types", types);
        req.getRequestDispatcher("/WEB-INF/views/query/expiry_list.jsp").forward(req, resp);
    }

    public void history(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String readerBarcode = req.getParameter("readerBarcode");
        String bookName = req.getParameter("bookName");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        List<BorrowRecord> list = borrowRecordDao.searchHistory(readerBarcode, bookName, startDate, endDate);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/query/history_list.jsp").forward(req, resp);
    }

    public void ranking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        List<BookRanking> list = borrowRecordDao.findRanking(startDate, endDate);
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/query/ranking_list.jsp").forward(req, resp);
    }
}
