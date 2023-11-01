package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class TopLevelResourceService<T> extends AbstractService<T> {

    private final Dao<T> dao;

    public TopLevelResourceService(Dao<T> dao) {
        this.dao = dao;
    }

    abstract void validateId(int id);

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {

            List<T> entities = dao.getAll();

            if (req.getQueryString() != null) {
                entities = filterByQueryParams(req, entities);
            }

            JsonSerializationUtil.serializeObjectToJsonStream(entities, resp.getWriter());
            return;
        }

        int entityId = extractIdFromURI(pathInfo);
        T entity = dao.getById(entityId).orElseThrow();

        if (pathInfo.matches("^/\\d+/[^/]+.*$")) {
            req.setAttribute("parentResource", entity);
            forwardToNestedResourceServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/\\d+/?$")) {
            JsonSerializationUtil.serializeObjectToJsonStream(entity, resp.getWriter());
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {
            simulateSuccessfulPostOperation(resp);
            return;
        }

        if (!pathInfo.matches("^/\\d+/[^/]+/?$")) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int entityId = extractIdFromURI(pathInfo);
        validateId(entityId);

        forwardToNestedResourceServlet(req, resp);
    }

    @Override
    public void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    public void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    void processPutOrDeleteRequest(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int id = extractIdFromURI(pathInfo);
        T entity = dao.getById(id).orElseThrow();

        if (pathInfo.matches("^/\\d+/[^/]+/\\d+/?$")) {
            req.setAttribute("parentResource", entity);
            forwardToNestedResourceServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/\\d+/?$")) {
            switch (req.getMethod()) {
                case "PUT":
                    simulateSuccessfulPutOperation(resp, id);
                    break;
                case "DELETE":
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
            }
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void forwardToNestedResourceServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        int nestedResURIPartIndex = pathInfo.indexOf('/', pathInfo.indexOf('/') + 1);
        RequestDispatcher dispatcher = req.getRequestDispatcher(pathInfo.substring(nestedResURIPartIndex));
        dispatcher.forward(req, resp);
    }
}
