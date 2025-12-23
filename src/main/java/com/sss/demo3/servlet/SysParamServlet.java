package com.sss.demo3.servlet;

import com.sss.demo3.dao.SysParamDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.SysParam;
import com.sss.demo3.entity.SystemLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SysParamServlet", urlPatterns = "/system/params")
public class SysParamServlet extends BaseServlet {

    private SysParamDao sysParamDao = new SysParamDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<SysParam> list = sysParamDao.getAll();
        req.setAttribute("list", list);
        req.getRequestDispatcher("/WEB-INF/views/system/params.jsp").forward(req, resp);
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String maxBorrowNum = req.getParameter("max_borrow_num");
        String maxBorrowDays = req.getParameter("max_borrow_days"); // Note: This is usually per ReaderType, but assume global override exists or logic is confusing
        // Wait, ReaderType has max_borrow_days. SysParam usually has global switches or default limits.
        // Audit showed BorrowService uses "max_borrow_num" from SysParam.
        // Let's iterate parameters and update them.
        
        List<SysParam> list = sysParamDao.getAll();
        for (SysParam p : list) {
            String val = req.getParameter(p.getParamName());
            if (val != null) {
                sysParamDao.updateValue(p.getParamName(), val);
            }
        }

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        
        SystemLog log = new SystemLog();
        log.setOperatorId(currentAdmin.getAdminId());
        log.setOperationType("UPDATE_PARAMS");
        log.setContent("Updated system parameters");
        systemLogDao.insert(log);

        req.setAttribute("message", "Parameters updated successfully");
        index(req, resp);
    }
}