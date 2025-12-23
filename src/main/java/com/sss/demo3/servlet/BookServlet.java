package com.sss.demo3.servlet;

import com.sss.demo3.dao.BookDao;
import com.sss.demo3.dao.BookTypeDao;
import com.sss.demo3.dao.BookshelfDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.Book;
import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookServlet", urlPatterns = "/book/list")
public class BookServlet extends BaseServlet {

    private BookDao bookDao = new BookDao();
    private BookTypeDao bookTypeDao = new BookTypeDao();
    private BookshelfDao bookshelfDao = new BookshelfDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = ValidationUtils.parseInt(req.getParameter("page"), 1);
        int pageSize = 10;
        String keyword = req.getParameter("keyword");
        
        List<Book> list = bookDao.findByPage(page, pageSize, keyword);
        int total = bookDao.count(keyword);
        int totalPage = (int) Math.ceil((double) total / pageSize);

        req.setAttribute("list", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPage", totalPage);
        req.setAttribute("keyword", keyword);
        req.getRequestDispatcher("/WEB-INF/views/book/book_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareForm(req);
        req.getRequestDispatcher("/WEB-INF/views/book/book_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = ValidationUtils.parseInt(idStr, 0);
        if (id > 0) {
            Book book = bookDao.findById(id);
            req.setAttribute("book", book);
        }
        prepareForm(req);
        req.getRequestDispatcher("/WEB-INF/views/book/book_form.jsp").forward(req, resp);
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        Book b = new Book();
        
        // Basic Validation using ValidationUtils
        try {
            String barcode = req.getParameter("barcode");
            if (!ValidationUtils.isValidBarcode(barcode)) throw new IllegalArgumentException("Invalid barcode format");
            b.setBarcode(barcode);
            
            String name = req.getParameter("name");
            if (!ValidationUtils.isNotEmpty(name)) throw new IllegalArgumentException("Name cannot be empty");
            b.setName(name);

            b.setIsbn(req.getParameter("isbn"));
            b.setBookTypeId(Integer.parseInt(req.getParameter("bookTypeId")));
            b.setAuthor(req.getParameter("author"));
            b.setPublisher(req.getParameter("publisher"));
            b.setFormat(req.getParameter("format"));
            b.setBinding(req.getParameter("binding"));
            b.setEdition(req.getParameter("edition"));
            
            String priceStr = req.getParameter("price");
            if (!ValidationUtils.isNonNegativeDouble(priceStr)) throw new IllegalArgumentException("Price must be non-negative");
            b.setPrice(Double.parseDouble(priceStr));
            
            String stockStr = req.getParameter("stock");
            if (!ValidationUtils.isPositiveInt(stockStr) && !stockStr.equals("0")) throw new IllegalArgumentException("Stock must be non-negative integer");
            b.setStock(Integer.parseInt(stockStr));
            
            b.setBookshelfId(Integer.parseInt(req.getParameter("bookshelfId")));
        } catch (NumberFormatException e) {
             req.setAttribute("error", "Invalid number format");
             prepareForm(req);
             req.getRequestDispatcher("/WEB-INF/views/book/book_form.jsp").forward(req, resp);
             return;
        } catch (IllegalArgumentException e) {
             req.setAttribute("error", e.getMessage());
             prepareForm(req);
             req.getRequestDispatcher("/WEB-INF/views/book/book_form.jsp").forward(req, resp);
             return;
        }

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            // Duplicate Barcode Check (Simplified: just let DB throw exception or check here)
            // Let's rely on DB unique constraint -> will throw exception -> caught by 500 or Dao
            // Or better, check it.
            Book existing = bookDao.findByBarcode(b.getBarcode());
            if (existing != null) {
                 req.setAttribute("error", "Barcode already exists");
                 prepareForm(req);
                 req.getRequestDispatcher("/WEB-INF/views/book/book_form.jsp").forward(req, resp);
                 return;
            }

            bookDao.insert(b);
            log(currentAdmin, "INSERT", "Added Book: " + b.getName());
        } else {
            b.setBookId(Integer.parseInt(idStr));
            bookDao.update(b);
            log(currentAdmin, "UPDATE", "Updated Book: " + b.getName());
        }
        resp.sendRedirect("list");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = ValidationUtils.parseInt(idStr, 0);
        if (id > 0) {
            bookDao.delete(id);
            HttpSession session = req.getSession();
            Admin currentAdmin = (Admin) session.getAttribute("admin");
            log(currentAdmin, "DELETE", "Deleted Book id: " + id);
        }
        resp.sendRedirect("list");
    }

    private void prepareForm(HttpServletRequest req) {
        req.setAttribute("types", bookTypeDao.findAll());
        req.setAttribute("bookshelves", bookshelfDao.findAll());
    }

    private void log(Admin operator, String type, String content) {
        if (operator != null) {
            SystemLog log = new SystemLog();
            log.setOperatorId(operator.getAdminId());
            log.setOperationType(type);
            log.setContent(content);
            systemLogDao.insert(log);
        }
    }
}