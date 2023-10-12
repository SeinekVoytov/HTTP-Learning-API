package com.example.httplearningapi.model.service;

import com.example.httplearningapi.model.dao.UserDao;
import com.example.httplearningapi.model.entities.user.User;
import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserService implements Service {

    private final Dao<User> userDao = new UserDao();

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (pathInfo == null || pathInfo.equals("/")) {

            List<User> users = userDao.getAll();

            String queryString = req.getQueryString();
            if (queryString != null) {
                users = this.filterUsersByQueryParams(req, users);
            }

            JsonSerializationUtil.serializeObjectToJsonStream(users, resp.getWriter());
            return;
        }

        int userId = extractUserIdFromURI(pathInfo);
        User user = userDao.getById(userId).orElseThrow();

        if (pathInfo.matches("^/[^/]+/recipes.*$")) {
            req.setAttribute("user", user);
            this.forwardToPrescriptionsServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/[^/]+/?$")) {
            JsonSerializationUtil.serializeObjectToJsonStream(user, resp.getWriter());
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (pathInfo != null && !pathInfo.equals("/") && !pathInfo.matches("^/[^/]+/recipes$")) { //
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (pathInfo == null || pathInfo.equals("/")) {
            this.simulateSuccessfulPostOperation(resp);
            return;
        }

        int userId = extractUserIdFromURI(pathInfo);
        User user = userDao.getById(userId).orElseThrow();

        req.setAttribute("user", user);
        this.forwardToPrescriptionsServlet(req, resp);
    }

    @Override
    public void handlePut(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    @Override
    public void handleDelete(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPutOrDeleteRequest(pathInfo, req, resp);
    }

    private void processPutOrDeleteRequest(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int userId = extractUserIdFromURI(pathInfo);

        User user = userDao.getById(userId).orElseThrow();

        if (pathInfo.matches("^/[^/]+/recipes/[^/]+$")) {
            req.setAttribute("user", user);
            this.forwardToPrescriptionsServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/[^/]+/?$")) {
            switch (req.getMethod()) {
                case "PUT":
                    simulateSuccessfulPutOperation(resp, userId);
                    break;
                case "DELETE":
                    resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    break;
            }
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    private void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
    }

    private void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 11}");
    }

    private Predicate<User> createPredicateForFilteringUsersByQueryParams(HttpServletRequest req) {
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

    private List<User> filterUsersByQueryParams(HttpServletRequest req, List<User> targetList) {
        return targetList.stream()
                .filter(this.createPredicateForFilteringUsersByQueryParams(req))
                .collect(Collectors.toList());
    }

    private int extractUserIdFromURI(String pathInfo) {
        return Integer.parseInt(pathInfo.substring(1).split("/")[0]);
    }

    private void forwardToPrescriptionsServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/prescriptions");
        dispatcher.forward(req, resp);
    }
}
