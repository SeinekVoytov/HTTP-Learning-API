package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.entities.user.User;
import com.example.httplearningapi.model.service.Service;
import com.example.httplearningapi.model.service.UserService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/users", "/users/*"})
public class UsersServlet extends AbstractServlet {

    @Override
    void handleRequest(HandleProcessor<Service<?>> handleProcessor, HttpServletResponse resp) {
        try {
            Service<User> service = new UserService();
            handleProcessor.process(service);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }
}
