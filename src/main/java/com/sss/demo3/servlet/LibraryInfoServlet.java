package com.sss.demo3.servlet;

import com.sss.demo3.dao.LibraryInfoDao;
import com.sss.demo3.dao.SystemLogDao;
import com.sss.demo3.entity.Admin;
import com.sss.demo3.entity.LibraryInfo;
import com.sss.demo3.entity.SystemLog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LibraryInfoServlet", urlPatterns = "/system/library")
public class LibraryInfoServlet extends BaseServlet {

    private LibraryInfoDao libraryInfoDao = new LibraryInfoDao();
    private SystemLogDao systemLogDao = new SystemLogDao();

    @Override
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LibraryInfo info = libraryInfoDao.get();
        if (info == null) {
            info = new LibraryInfo(); // Avoid null pointer in JSP
        }
        req.setAttribute("info", info);
        req.getRequestDispatcher("/WEB-INF/views/system/library_info.jsp").forward(req, resp);
    }

    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("libraryName");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String openHours = req.getParameter("openHours");
        String introduction = req.getParameter("introduction");

        LibraryInfo info = new LibraryInfo();
        info.setLibraryName(name);
        info.setAddress(address);
        info.setPhone(phone);
        info.setOpenHours(openHours);
        info.setIntroduction(introduction);

        libraryInfoDao.update(info);

        HttpSession session = req.getSession();
        Admin currentAdmin = (Admin) session.getAttribute("admin");
        
        SystemLog log = new SystemLog();
        log.setOperatorId(currentAdmin.getAdminId());
        log.setOperationType("UPDATE_LIBRARY");
        log.setContent("Updated library info");
        systemLogDao.insert(log);

        req.setAttribute("message", "Library info updated successfully");
        index(req, resp);
    }
}