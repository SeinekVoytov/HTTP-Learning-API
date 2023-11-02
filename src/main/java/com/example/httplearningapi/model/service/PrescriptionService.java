package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.PrescriptionDao;
import com.example.httplearningapi.model.entities.prescription.Prescription;
import com.example.httplearningapi.model.entities.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class PrescriptionService extends LowLevelResourceService<Prescription, User> {

    public PrescriptionService() {
        super(new PrescriptionDao());
    }

    @Override
    User getParentResourceAttribute(HttpServletRequest req) {
        return (User) req.getAttribute("parentResource");
    }

    @Override
    List<Prescription> getResourceList(User parentEntity) {
        return parentEntity.getPrescriptions();
    }

    @Override
    void validateId(int id, User parentEntity) {

        boolean isIdValidAccordingToParent = true;

        if (parentEntity != null) {
            int allowedLowId = (parentEntity.getId() - 1) * 10 + 1;
            isIdValidAccordingToParent = id >= allowedLowId && id <= allowedLowId + 9;
        }

        if (!(id > 0 && id <= 100 && isIdValidAccordingToParent)) {
            throw new NoSuchElementException();
        }
    }

    @Override
    void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 101}");
    }

    @Override
    void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
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
