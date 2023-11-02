package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.CommentDao;
import com.example.httplearningapi.model.entities.comment.Comment;
import com.example.httplearningapi.model.entities.post.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class CommentService extends LowLevelResourceService<Comment, Post> {

    public CommentService() {
        super(new CommentDao());
    }

    @Override
    List<Comment> getResourceList(Post parentEntity) {
        return parentEntity.getComments();
    }

    @Override
    void validateId(int id, Post parentEntity) {

        boolean isIdValidAccordingToParent = true;

        if (parentEntity != null) {
            int allowedLowId = (parentEntity.getId() - 1) * 5 + 1;
            isIdValidAccordingToParent = id >= allowedLowId && id <= allowedLowId + 4;
        }

        if (!(id > 0 && id <= 500 && isIdValidAccordingToParent)) {
            throw new NoSuchElementException();
        }
    }

    @Override
    Post getParentResourceAttribute(HttpServletRequest req) {
        return (Post) req.getAttribute("parentResource");
    }

    @Override
    void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 501}");
    }

    @Override
    void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
    }

    @Override
    Predicate<Comment> createPredicateForFilteringByQueryParams(HttpServletRequest req) {
        String id = req.getParameter("id");
        return post ->
                (id == null || id.equals(String.valueOf(post.getId())));
    }
}
