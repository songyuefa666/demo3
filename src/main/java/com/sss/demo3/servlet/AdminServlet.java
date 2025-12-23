package com.sss.demo3.servlet;

import com.sss.demo3.dao.AdminDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.SystemLog;
import com.sss.demo3.util.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", urlPatterns = "/system/admin")
public class AdminServlet extends BaseServlet {

    private AdminDao adminDao = new AdminDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Admin> list = adminDao.findAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/system/admin_list.jsp").forward(req, resp);
    }

    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/system/admin_form.jsp").forward(req, resp);
    }

    public void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Admin admin = adminDao.findById(Long.parseLong(idStr));
            req.setAttribute("admin", admin);
        }
        req.getRequestDispatcher("/WEB-INF/views/system/admin_form.jsp").forward(req, resp);
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String username = req.getParameter("username");
        String password = req.getParameter("password"); 
        String realName = req.getParameter("realName");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setRealName(realName);
        admin.setPhone(phone);
        admin.setRole(role);

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");

        if (idStr == null || idStr.isEmpty()) {
            // Add - Encrypt Password
            if (password == null || password.isEmpty()) {
                 // Force a default or error? 
                 // Let's assume required in UI.
                 password = "123"; // fallback
            }
            admin.setPassword(SecurityUtils.encrypt(password));
            adminDao.insert(admin);
            log(currentAdmin, "INSERT", "Added admin: " + username);
        } else {
            // Update
            Long id = Long.parseLong(idStr);
            admin.setAdminId(id);
            
            if (password != null && !password.trim().isEmpty()) {
                admin.setPassword(SecurityUtils.encrypt(password));
            } else {
                // Keep old password
                Admin old = adminDao.findById(id);
                admin.setPassword(old.getPassword());
            }
            
            adminDao.update(admin);
            log(currentAdmin, "UPDATE", "Updated admin: " + username);
        }
        resp.sendRedirect("admin");
    }

    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            Admin target = adminDao.findById(id);
            if (target != null) {
                // Check constraint: Cannot delete last SYS_ADMIN
                if ("SYS_ADMIN".equals(target.getRole())) {
                    int count = adminDao.countSysAdmins();
                    if (count <= 1) {
                        req.setAttribute("error", "Cannot delete the last SYS_ADMIN!");
                        index(req, resp); // Go back to list with error
                        return;
                    }
                }
                adminDao.delete(id);
                HttpSession session = req.getSession();
                Admin currentAdmin = (Admin) session.getAttribute("admin");
                log(currentAdmin, "DELETE", "Deleted admin id: " + id);
            }
        }
        resp.sendRedirect("admin");
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