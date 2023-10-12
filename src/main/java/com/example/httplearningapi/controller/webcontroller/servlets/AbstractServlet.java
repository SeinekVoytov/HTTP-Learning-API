package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.service.Service;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class AbstractServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        this.handleRequest((service) -> service.handleGet(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.handleRequest((service) -> service.handlePost(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        this.handleRequest((service) -> service.handlePut(req.getPathInfo(), req, resp), resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        this.handleRequest((service) -> service.handleDelete(req.getPathInfo(), req, resp), resp);
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

    abstract void handleRequest(HandleProcessor<Service<?>> handleProcessor, HttpServletResponse resp);
}
