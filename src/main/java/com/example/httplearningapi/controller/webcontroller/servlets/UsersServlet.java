package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.user.User;
import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet(name = "Users Servlet", urlPatterns = {"/users", "/users/*"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            final UserController userController = new UserController();
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {

                List<User> users = userController.getUsers();

                String queryString = req.getQueryString();
                if (queryString != null) {
                    users = users.stream()
                            .filter(this.createPredicateForFilteringUsersByQueryParams(req))
                            .collect(Collectors.toList());
                }

                JsonSerializationUtil.serializeObjectToJsonStream(users, resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            int userId;

            try {
                userId = Integer.parseInt(pathInfo.split("/")[1]);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (pathInfo.matches("^/.+/recipes.*$")) {
                req.setAttribute("userId", userId);
                // forward to another servlet
                return;
            }

            if (pathInfo.matches("^/.+/?$")) {
                try {
                    User requestedUser = userController.getUserById(userId).orElseThrow();
                    JsonSerializationUtil.serializeObjectToJsonStream(requestedUser, resp.getWriter());
                } catch (NoSuchElementException e) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo != null && !pathInfo.equals("/") && !pathInfo.matches("^/.+/recipes$")) { //
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (pathInfo == null || pathInfo.equals("/")) {
                // Simulate successful POST operation
                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.getWriter().println("{\"id\" : 11}");
                return;
            }

            try {
                int userId = Integer.parseInt(pathInfo.substring(1, pathInfo.indexOf('/', 1)));
                req.setAttribute("userId", userId);
                // forward to the recipes servlet
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {

            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int userId;

            try {
                userId = Integer.parseInt(pathInfo.substring(1).split("/")[0]);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (userId <= 0 || userId > 10) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (pathInfo.matches("^/[^/]+/recipes/[^/]+$")) {
                req.setAttribute("userId", userId);
                // forward
                return;
            }

            if (pathInfo.matches("^/[^/]+/?$")) {
                // simulating successful PUT operation
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().println(String.format("{\"id\" : %d}", userId));
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int userId;

            try {
                userId = Integer.parseInt(pathInfo.substring(1).split("/")[0]);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            if (userId <= 0 || userId > 10) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (pathInfo.matches("^/[^/]+/recipes/[^/]+$")) {
                req.setAttribute("userId", userId);
                // forward to another servlet
                return;
            }

            if (pathInfo.matches("^/[^/]+/?$")) {
                // simulating successful DELETE operation
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) {

        try {


        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
}
