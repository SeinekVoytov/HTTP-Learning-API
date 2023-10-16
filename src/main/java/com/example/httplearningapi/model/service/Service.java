package com.example.httplearningapi.model.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Service<T> {
    public abstract void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    public abstract void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    public abstract void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    public abstract void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
    abstract Predicate<T> createPredicateForFilteringByQueryParams(HttpServletRequest req);
    int extractIdFromURI(String pathInfo) {
        return Integer.parseInt(pathInfo.substring(1).split("/")[0]);
    }

    List<T> filterByQueryParams(HttpServletRequest req, List<T> targetList) {
        return targetList.stream()
                .filter(createPredicateForFilteringByQueryParams(req))
                .collect(Collectors.toList());
    }
}
