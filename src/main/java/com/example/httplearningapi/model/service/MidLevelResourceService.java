package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class MidLevelResourceService<T, P> extends AbstractService<T> {

    private final Dao<T> dao;

    public MidLevelResourceService(Dao<T> dao) {
        this.dao = dao;
    }

    abstract P getParentResourceAttribute(HttpServletRequest req);

    abstract List<T> getResourceList(P parentEntity);

    abstract void validateId(int id, P parentEntity);

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        P parentEntity = getParentResourceAttribute(req);

        if (isPathInfoNullOrEmpty(pathInfo)) {

            List<T> entities = (parentEntity == null) ?
                    dao.getAll() : getResourceList(parentEntity);

            if (req.getQueryString() != null) {
                entities = filterByQueryParams(req, entities);
            }

            JsonSerializationUtil.serializeObjectToJsonStream(entities, resp.getWriter());
            return;
        }

        int entityId = extractIdFromURI(pathInfo);
        validateId(entityId, parentEntity);
        T entity = dao.getById(entityId).orElseThrow();

        if (pathInfo.matches("^/\\d+/[^/]+.*$")) {
            req.setAttribute("parentResource", entity);
            forwardToNestedResourceServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/[^/]+/?$")) {
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

        P parentEntity = getParentResourceAttribute(req);
        int entityId = extractIdFromURI(pathInfo);
        validateId(entityId, parentEntity);

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

        P parentEntity = getParentResourceAttribute(req);
        int id = extractIdFromURI(pathInfo);
        validateId(id, parentEntity);
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
