package com.sss.demo3.filter;

import com.sss.demo3.entity.Admin;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RoleFilter", urlPatterns = "/*")
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        
        // White list for static resources and login
        if (uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith(".jpg") || uri.endsWith(".png") 
            || uri.contains("/login") || uri.contains("/hello-servlet") || uri.contains("/error/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Admin admin = (session != null) ? (Admin) session.getAttribute("admin") : null;

        if (admin == null) {
            // Allow access to index.jsp (which redirects) or root, but safer to just redirect to login for any business path
            // If user accesses /demo3/, container serves index.jsp.
            // If uri is exactly context path or context path + /, allow.
            String ctx = req.getContextPath();
            if (uri.equals(ctx) || uri.equals(ctx + "/") || uri.endsWith("index.jsp")) {
                 chain.doFilter(request, response);
                 return;
            }
            
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Role check
        // Requirement: SYS_ADMIN can enter system/*, OPERATOR cannot
        if (uri.contains("/system/")) {
            if (!"SYS_ADMIN".equals(admin.getRole())) {
                // Forward to 403 page instead of sendError for better UX
                req.getRequestDispatcher("/WEB-INF/views/error/403.jsp").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}