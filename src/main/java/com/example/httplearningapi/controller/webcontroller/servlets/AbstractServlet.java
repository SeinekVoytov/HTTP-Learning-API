package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.service.AbstractService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.handleRequest((service) -> service.handleGet(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.handleRequest((service) -> service.handlePost(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.handleRequest((service) -> service.handlePut(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        this.handleRequest((service) -> service.handleDelete(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            super.doHead(req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            super.doOptions(req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            super.doTrace(req, resp);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    abstract void handleRequest(HandleProcessor<AbstractService<?>> handleProcessor, HttpServletResponse resp);

}
