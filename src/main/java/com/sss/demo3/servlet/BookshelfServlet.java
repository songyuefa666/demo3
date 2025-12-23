package com.sss.demo3.servlet;

import com.sss.demo3.dao.BookshelfDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.Bookshelf;
import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "BookshelfServlet", urlPatterns = "/book/bookshelf")
public class BookshelfServlet extends BaseServlet {

    private BookshelfDao bookshelfDao = new BookshelfDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Bookshelf> list = bookshelfDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/book/bookshelf_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/book/bookshelf_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Bookshelf b = bookshelfDao.findById(Integer.parseInt(idStr));
            req.setAttribute("bookshelf", b);
            req.getRequestDispatcher("/WEB-INF/views/book/bookshelf_form.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("bookshelf");
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String location = req.getParameter("location");

        if (!ValidationUtils.isNotEmpty(name)) {
            req.setAttribute("error", "Name cannot be empty");
            if (idStr != null && !idStr.isEmpty()) {
                Bookshelf b = new Bookshelf();
                b.setId(Integer.parseInt(idStr));
                b.setName(name);
                b.setLocation(location);
                req.setAttribute("bookshelf", b);
            }
            req.getRequestDispatcher("/WEB-INF/views/book/bookshelf_form.jsp").forward(req, resp);
            return;
        }

        Bookshelf b = new Bookshelf();
        b.setName(name);
        b.setLocation(location);

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            bookshelfDao.insert(b);
            log(currentAdmin, "INSERT", "Added Bookshelf: " + name);
        } else {
            b.setId(Integer.parseInt(idStr));
            bookshelfDao.update(b);
            log(currentAdmin, "UPDATE", "Updated Bookshelf: " + name);
        }
        resp.sendRedirect("bookshelf");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            try {
                bookshelfDao.delete(Integer.parseInt(idStr));
                HttpSession session = req.getSession();
                Admin currentAdmin = (Admin) session.getAttribute("admin");
                log(currentAdmin, "DELETE", "Deleted Bookshelf id: " + idStr);
            } catch (Exception e) {
                req.setAttribute("error", "Cannot delete: Bookshelf is in use");
                index(req, resp);
                return;
            }
        }
        resp.sendRedirect("bookshelf");
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