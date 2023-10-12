package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.entities.user.Prescription;
import com.example.httplearningapi.model.service.PrescriptionService;
import com.example.httplearningapi.model.service.Service;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/prescriptions", "/prescriptions/*"})
public class PrescriptionsServlet extends AbstractServlet {

    @Override
    void handleRequest(HandleProcessor<Service<?>> handleProcessor, HttpServletResponse resp) {
        try {
            Service<Prescription> service = new PrescriptionService();
            handleProcessor.process(service);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }
}
