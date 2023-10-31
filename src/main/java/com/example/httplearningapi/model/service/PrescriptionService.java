package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.model.dao.PrescriptionDao;
import com.example.httplearningapi.model.entities.user.Prescription;
import com.example.httplearningapi.model.entities.user.User;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class PrescriptionService extends AbstractService<Prescription> {

    private final Dao<Prescription> prescriptionDao = new PrescriptionDao();

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {

            User userAttribute = (User) req.getAttribute("user");
            List<Prescription> prescriptions = (userAttribute == null) ?
                    prescriptionDao.getAll() : userAttribute.getPrescriptions();

            String queryString = req.getQueryString();
            if (queryString != null) {
                prescriptions = this.filterByQueryParams(req, prescriptions);
            }

            JsonSerializationUtil.serializeObjectToJsonStream(prescriptions, resp.getWriter());
            return;
        }

        int prescriptionId = extractIdFromURI(pathInfo);
        Prescription prescription = prescriptionDao.getById(prescriptionId).orElseThrow();

        if (pathInfo.matches("^/[^/]+/?$")) {
            JsonSerializationUtil.serializeObjectToJsonStream(prescription, resp.getWriter());
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

        this.simulateSuccessfulPostOperation(resp);
    }

    @Override
    public void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    public void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    private void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 101}");
    }

    private void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
    }

    private void processPutOrDeleteRequest(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int prescriptionId = extractIdFromURI(pathInfo);
        if (prescriptionId <= 0 || prescriptionId > 100) {
            throw new NoSuchElementException();
        }

        if (pathInfo.matches("^/\\d+/?$")) {
            switch (req.getMethod()) {
                case "PUT":
                    simulateSuccessfulPutOperation(resp, prescriptionId);
                    break;
                case "DELETE":
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
            }
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    Predicate<Prescription> createPredicateForFilteringByQueryParams(HttpServletRequest req) {
        String id = req.getParameter("id");
        String medicationName = req.getParameter("medication");
        return prescription ->
                (id == null || id.equals(String.valueOf(prescription.getId()))) &&
                        (medicationName == null || medicationName.equals(prescription.getMedicationName()));
    }
}
