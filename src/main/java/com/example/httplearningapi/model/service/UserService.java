package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.UserDao;
import com.example.httplearningapi.model.entities.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class UserService extends TopLevelResourceService<User> {

    public UserService() {
        super(new UserDao());
    }

    @Override
    void validateId(int id) {
        if (id <= 0 || id > 10) {
            throw new NoSuchElementException();
        }
    }

    @Override
    void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
    }

    @Override
    void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 11}");
    }

    @Override
    Predicate<User> createPredicateForFilteringByQueryParams(HttpServletRequest req) {
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String website = req.getParameter("website");
        return user ->
                (id == null || id.equals(String.valueOf(user.getId()))) &&
                        (name == null || name.equals(user.getName())) &&
                        (email == null || email.equals(user.getEmail())) &&
                        (phone == null || phone.equals(user.getPhone())) &&
                        (website == null || website.equals(user.getWebsite()));
    }
}
