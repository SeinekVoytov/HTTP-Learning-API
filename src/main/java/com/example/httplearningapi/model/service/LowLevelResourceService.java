package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class LowLevelResourceService<T, P> extends AbstractService<T> {

    private final Dao<T> dao;

    public LowLevelResourceService(Dao<T> dao) {
        this.dao = dao;
    }

    abstract P getParentResourceAttribute(HttpServletRequest req);

    abstract List<T> getResourceList(P parentEntity);

    abstract void validateId(int id, P parentEntity);

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {

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

        if (pathInfo.matches("^/[^/]+/?$")) {
            JsonSerializationUtil.serializeObjectToJsonStream(entity, resp.getWriter());
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        simulateSuccessfulPostOperation(resp);
    }

    @Override
    public void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    public void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    void processPutOrDeleteRequest(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int entityId = extractIdFromURI(pathInfo);
        validateId(entityId, null);

        if (pathInfo.matches("^/\\d+/?$")) {
            switch (req.getMethod()) {
                case "PUT":
                    simulateSuccessfulPutOperation(resp, entityId);
                    break;
                case "DELETE":
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
            }
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}

