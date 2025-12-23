package com.sss.demo3.servlet;

import com.sss.demo3.dao.ReaderDao;
import com.sss.demo3.dao.ReaderTypeDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.Reader;
import com.sss.demo3.entity.ReaderType;
import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.ValidationUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ReaderServlet", urlPatterns = "/reader/list")
public class ReaderServlet extends BaseServlet {

    private ReaderDao readerDao = new ReaderDao();
    private ReaderTypeDao readerTypeDao = new ReaderTypeDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = ValidationUtils.parseInt(req.getParameter("page"), 1);
        int pageSize = 10;

        List<Reader> list = readerDao.findByPage(page, pageSize);
        int total = readerDao.count();
        int totalPage = (int) Math.ceil((double) total / pageSize);

        req.setAttribute("list", list);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPage", totalPage);
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ReaderType> types = readerTypeDao.findAll();
        req.setAttribute("types", types);
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = ValidationUtils.parseInt(idStr, 0);
        if (id > 0) {
            Reader reader = readerDao.findById(id);
            req.setAttribute("reader", reader);
        }
        List<ReaderType> types = readerTypeDao.findAll();
        req.setAttribute("types", types);
        req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String barcode = req.getParameter("readerBarcode");
        String name = req.getParameter("name");
        String gender = req.getParameter("gender");
        String unit = req.getParameter("unit");
        String phone = req.getParameter("phone");
        String typeId = req.getParameter("typeId");
        String status = req.getParameter("status");

        // Validations using ValidationUtils
        // 1. Phone regex
        if (!ValidationUtils.isValidPhone(phone)) {
            req.setAttribute("error", "Invalid phone number format (11 digits required)");
            prepareForm(req, idStr);
            req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
            return;
        }

        // 2. Barcode check
        if (!ValidationUtils.isValidBarcode(barcode)) {
            req.setAttribute("error", "Invalid barcode format");
            prepareForm(req, idStr);
            req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
            return;
        }

        // 3. Status check
        if (!"0".equals(status) && !"1".equals(status)) {
            req.setAttribute("error", "Invalid status");
             prepareForm(req, idStr);
             req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
             return;
        }
        
        // 3. Unique Barcode
        Integer excludeId = (idStr != null && !idStr.isEmpty()) ? Integer.parseInt(idStr) : null;
        if (readerDao.isBarcodeExists(barcode, excludeId)) {
            req.setAttribute("error", "Reader Barcode already exists!");
            prepareForm(req, idStr);
            req.getRequestDispatcher("/WEB-INF/views/reader/reader_form.jsp").forward(req, resp);
            return;
        }

        Reader reader = new Reader();
        reader.setReaderBarcode(barcode);
        reader.setName(name);
        reader.setGender(gender);
        reader.setUnit(unit);
        reader.setPhone(phone);
        reader.setTypeId(ValidationUtils.parseInt(typeId, 0));
        reader.setStatus(ValidationUtils.parseInt(status, 0));

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            readerDao.insert(reader);
            log(currentAdmin, "INSERT", "Added Reader: " + name + " (" + barcode + ")");
        } else {
            reader.setReaderId(ValidationUtils.parseInt(idStr, 0));
            readerDao.update(reader);
            log(currentAdmin, "UPDATE", "Updated Reader: " + name + " (" + barcode + ")");
        }
        resp.sendRedirect("list");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        int id = ValidationUtils.parseInt(idStr, 0);
        if (id > 0) {
            // Should check if reader has borrowed books.
            // Simplified: Just delete
            readerDao.delete(id);
            HttpSession session = req.getSession();
            Admin currentAdmin = (Admin) session.getAttribute("admin");
            log(currentAdmin, "DELETE", "Deleted Reader id: " + id);
        }
        resp.sendRedirect("list");
    }

    private void prepareForm(HttpServletRequest req, String idStr) {
        int id = ValidationUtils.parseInt(idStr, 0);
        if (id > 0) {
            Reader formReader = new Reader();
            formReader.setReaderId(id);
            formReader.setReaderBarcode(req.getParameter("readerBarcode"));
            formReader.setName(req.getParameter("name"));
            // ... set others
            req.setAttribute("reader", formReader);
        }
        req.setAttribute("types", readerTypeDao.findAll());
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