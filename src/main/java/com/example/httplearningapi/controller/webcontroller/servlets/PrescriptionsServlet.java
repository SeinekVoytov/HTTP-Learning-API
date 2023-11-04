package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.entities.prescription.Prescription;
import com.example.httplearningapi.model.service.PrescriptionService;
import com.example.httplearningapi.model.service.AbstractService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(urlPatterns = {"/prescriptions", "/prescriptions/*"})
public class PrescriptionsServlet extends AbstractServlet {

    @Override
    void handleRequest(HandleProcessor<AbstractService<?>> handleProcessor, HttpServletResponse resp) throws IOException {
        try {
            AbstractService<Prescription> service = new PrescriptionService();
            handleProcessor.process(service);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }
}
