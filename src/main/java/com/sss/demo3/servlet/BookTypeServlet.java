package com.sss.demo3.servlet;

import com.sss.demo3.dao.BookTypeDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.BookType;
import com.sss.demo3.entity.SystemLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookTypeServlet", urlPatterns = "/book/type")
public class BookTypeServlet extends BaseServlet {

    private BookTypeDao bookTypeDao = new BookTypeDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BookType> list = bookTypeDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/book/book_type_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/book/book_type_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            BookType t = bookTypeDao.findById(Integer.parseInt(idStr));
            req.setAttribute("t", t);
        }
        req.getRequestDispatcher("/WEB-INF/views/book/book_type_form.jsp").forward(req, resp);
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String typeName = req.getParameter("typeName");
        String description = req.getParameter("description");

        BookType t = new BookType();
        t.setTypeName(typeName);
        t.setDescription(description);

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            bookTypeDao.insert(t);
            log(currentAdmin, "INSERT", "Added Book Type: " + typeName);
        } else {
            t.setId(Integer.parseInt(idStr));
            bookTypeDao.update(t);
            log(currentAdmin, "UPDATE", "Updated Book Type: " + typeName);
        }
        resp.sendRedirect("type");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            try {
                bookTypeDao.delete(id);
                HttpSession session = req.getSession();
                Admin currentAdmin = (Admin) session.getAttribute("admin");
                log(currentAdmin, "DELETE", "Deleted Book Type id: " + id);
            } catch (Exception e) {
                req.setAttribute("error", "Cannot delete: Type is in use.");
                index(req, resp);
                return;
            }
        }
        resp.sendRedirect("type");
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