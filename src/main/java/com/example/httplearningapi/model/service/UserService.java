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
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class UserService extends Service<User> {

    private final Dao<User> userDao = new UserDao();

    @Override
    public void handleGet(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (isPathInfoNullOrEmpty(pathInfo)) {

            List<User> users = userDao.getAll();

            String queryString = req.getQueryString();
            if (queryString != null) {
                users = this.filterByQueryParams(req, users);
            }

            JsonSerializationUtil.serializeObjectToJsonStream(users, resp.getWriter());
            return;
        }

        int userId = extractIdFromURI(pathInfo);
        User user = userDao.getById(userId).orElseThrow();

        if (pathInfo.matches("^/\\d+/prescriptions.*$")) {
            req.setAttribute("user", user);
            this.forwardToPrescriptionsServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/\\d+/?$")) {
            JsonSerializationUtil.serializeObjectToJsonStream(user, resp.getWriter());
            return;
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void handlePost(String pathInfo, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (pathInfo != null && !pathInfo.equals("/") && !pathInfo.matches("^/\\d+/prescriptions/?$")) { //
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        if (isPathInfoNullOrEmpty(pathInfo)) {
            this.simulateSuccessfulPostOperation(resp);
            return;
        }

        int userId = extractIdFromURI(pathInfo);
        if (userId <= 0 || userId >= 10) {
            throw new NoSuchElementException();
        }

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

        if (isPathInfoNullOrEmpty(pathInfo)) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int userId = extractIdFromURI(pathInfo);

        User user = userDao.getById(userId).orElseThrow();

        if (pathInfo.matches("^/\\d+/prescriptions/\\d+/?$")) {
            req.setAttribute("user", user);
            this.forwardToPrescriptionsServlet(req, resp);
            return;
        }

        if (pathInfo.matches("^/\\d+/?$")) {
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

    private void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(String.format("{\"id\" : %d}", id));
    }

    private void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().print("{\"id\" : 11}");
    }

    private void forwardToPrescriptionsServlet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        int prescriptionsURIPartIndex = requestURI.indexOf("/prescriptions");
        RequestDispatcher dispatcher = req.getRequestDispatcher(requestURI.substring(prescriptionsURIPartIndex));
        dispatcher.forward(req, resp);
    }
}
