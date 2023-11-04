package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.entities.comment.Comment;
import com.example.httplearningapi.model.service.AbstractService;
import com.example.httplearningapi.model.service.CommentService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/comments", "/comments/*"})
public class CommentServlet extends AbstractServlet {
    @Override
    void handleRequest(HandleProcessor<AbstractService<?>> handleProcessor, HttpServletResponse resp) throws IOException {
        try {
            AbstractService<Comment> service = new CommentService();
            handleProcessor.process(service);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }
}
