package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.entities.user.Post;
import com.example.httplearningapi.model.service.AbstractService;
import com.example.httplearningapi.model.service.PostService;
import com.example.httplearningapi.util.ExceptionHandleUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/posts", "/posts/*"})
public class PostsServlet extends AbstractServlet {

    @Override
    void handleRequest(HandleProcessor<AbstractService<?>> handleProcessor, HttpServletResponse resp) throws IOException {
        try {
            AbstractService<Post> service = new PostService();
            handleProcessor.process(service);
        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }
}
