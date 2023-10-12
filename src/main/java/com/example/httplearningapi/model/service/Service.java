package com.example.httplearningapi.model.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface Service {
    void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
