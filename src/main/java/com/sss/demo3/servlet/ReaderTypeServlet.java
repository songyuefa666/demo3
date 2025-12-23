package com.sss.demo3.servlet;

import com.sss.demo3.dao.ReaderTypeDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.entity.SystemLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReaderTypeServlet", urlPatterns = "/reader/type")
public class ReaderTypeServlet extends BaseServlet {

    private ReaderTypeDao readerTypeDao = new ReaderTypeDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ReaderType> list = readerTypeDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_type_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_type_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            ReaderType rt = readerTypeDao.findById(Integer.parseInt(idStr));
            req.setAttribute("rt", rt);
        }
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_type_form.jsp").forward(req, resp);
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String typeName = req.getParameter("typeName");
        String maxBorrowNum = req.getParameter("maxBorrowNum");
        String maxBorrowDays = req.getParameter("maxBorrowDays");
        String finePerDay = req.getParameter("finePerDay");

        ReaderType rt = new ReaderType();
        rt.setTypeName(typeName);
        rt.setMaxBorrowNum(Integer.parseInt(maxBorrowNum));
        rt.setMaxBorrowDays(Integer.parseInt(maxBorrowDays));
        rt.setFinePerDay(Double.parseDouble(finePerDay));

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            readerTypeDao.insert(rt);
            log(currentAdmin, "INSERT", "Added Reader Type: " + typeName);
        } else {
            rt.setTypeId(Integer.parseInt(idStr));
            readerTypeDao.update(rt);
            log(currentAdmin, "UPDATE", "Updated Reader Type: " + typeName);
        }
        resp.sendRedirect("type");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            // Should check if any reader uses this type before delete.
            // Simplified: Just delete (DB constraints might fail if FK exists)
            try {
                readerTypeDao.delete(id);
                HttpSession session = req.getSession();
                Admin currentAdmin = (Admin) session.getAttribute("admin");
                log(currentAdmin, "DELETE", "Deleted Reader Type id: " + id);
            } catch (Exception e) {
                // If FK constraint fails
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