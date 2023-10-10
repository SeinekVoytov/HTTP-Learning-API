package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.service.Service;
import com.example.httplearningapi.model.service.UserService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "Users Servlet", urlPatterns = {"/users", "/users/*"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Service service = new UserService();
            service.handleGet(req.getPathInfo(), req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Service service = new UserService();
            service.handlePost(req.getPathInfo(), req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Service service = new UserService();
            service.handlePut(req.getPathInfo(), req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Service service = new UserService();
            service.handleDelete(req.getPathInfo(), req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doHead(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doOptions(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doTrace(req, resp);
    }
}
