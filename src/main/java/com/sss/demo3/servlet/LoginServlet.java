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

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends BaseServlet {

    private AdminDao adminDao = new AdminDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        
        // Basic Lockout Check
        Integer failCount = (Integer) session.getAttribute("login_fail_count");
        if (failCount == null) failCount = 0;
        if (failCount >= 5) {
            Long lockoutTime = (Long) session.getAttribute("lockout_time");
            if (lockoutTime == null) {
                lockoutTime = System.currentTimeMillis();
                session.setAttribute("lockout_time", lockoutTime);
            }
            if (System.currentTimeMillis() - lockoutTime < 5 * 60 * 1000) { // 5 minutes
                req.setAttribute("error", "登录失败次数过多，请5分钟后再试");
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            } else {
                // Reset
                session.removeAttribute("login_fail_count");
                session.removeAttribute("lockout_time");
                failCount = 0;
            }
        }

        String u = req.getParameter("username");
        String p = req.getParameter("password");

        Admin admin = adminDao.findByUsername(u);
        if (admin != null && SecurityUtils.verify(p, admin.getPassword())) {
            // Regenerate session to mitigate fixation and clear fail counters
            session.invalidate();
            HttpSession newSession = req.getSession(true);
            newSession.removeAttribute("login_fail_count");
            newSession.removeAttribute("lockout_time");
            newSession.setAttribute("admin", admin);
            
            // Log login success
            SystemLog log = new SystemLog();
            log.setOperatorId(admin.getAdminId());
            log.setOperationType("LOGIN");
            log.setContent("Login success (IP: " + req.getRemoteAddr() + ")");
            new SystemLogDao().insert(log);
            
            resp.sendRedirect("home");
            return;
        } else {
            session.setAttribute("login_fail_count", failCount + 1);
            req.setAttribute("error", "用户名或密码错误");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
    
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect("login");
    }

    public void password(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/password.jsp").forward(req, resp);
    }

    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPass = req.getParameter("oldPassword");
        String newPass = req.getParameter("newPassword");
        String confirmPass = req.getParameter("confirmPassword");

        HttpSession session = req.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        // Reload admin to check old password
        Admin currentDbAdmin = adminDao.findById(admin.getAdminId());
        
        // Verify using SecurityUtils
        if (!SecurityUtils.verify(oldPass, currentDbAdmin.getPassword())) {
            req.setAttribute("error", "旧密码错误");
            req.getRequestDispatcher("/WEB-INF/views/password.jsp").forward(req, resp);
            return;
        }

        if (!newPass.equals(confirmPass)) {
            req.setAttribute("error", "两次新密码输入不一致");
            req.getRequestDispatcher("/WEB-INF/views/password.jsp").forward(req, resp);
            return;
        }

        adminDao.updatePassword(admin.getAdminId(), newPass);
        
        session.invalidate();
        req.setAttribute("error", "密码修改成功，请重新登录");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
