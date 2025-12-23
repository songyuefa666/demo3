package com.sss.demo3.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Encoding is handled by EncodingFilter, but for safety:
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String methodName = req.getParameter("action");
        if (methodName == null || methodName.trim().isEmpty()) {
            methodName = "index"; // Default method
        }

        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Action not found: " + methodName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Dispatch failed for action: " + methodName, e);
        }
    }

    // Default action if no action parameter is provided
    public void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("BaseServlet: No action specified and index() not overridden.");
    }
}