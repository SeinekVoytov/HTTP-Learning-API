package com.example.httplearningapi.servlets;

import com.example.httplearningapi.model.user.User;
import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.util.JsonSerializationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@WebServlet(name = "Users Servlet", urlPatterns = {"/users", "/users/*"})
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo != null && pathInfo.length() > 1) {
                String[] pathSegments = pathInfo.substring(1).split("/");
                int userId = Integer.parseInt(pathSegments[0]);

                if (pathSegments.length > 1) {
                    req.setAttribute("userId", userId);
                    // forward to another servlet
                } else {
                    UserController userController = new UserController();
                    User requestedUser = userController.getUserById(userId).orElseThrow();
                    JsonSerializationUtil.serializeObjectToJsonStream(requestedUser, resp.getWriter());
                }
            } else {
                UserController userController = new UserController();
                List<User> users = userController.getUsers();

                String queryString = req.getQueryString();
                if (queryString != null) {

                    Predicate<User> predicateForFiltering = createPredicateForFilteringUsersByQueryParams(
                            req.getParameter("id"),
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("phone"),
                            req.getParameter("website")
                    );

                    users = users.stream()
                            .filter(predicateForFiltering)
                            .collect(Collectors.toList());
                }

                JsonSerializationUtil.serializeObjectToJsonStream(users, resp.getWriter());
            }

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/") || pathInfo.matches("/?\\d+/recipes")) {

                if (pathInfo != null && !pathInfo.equals("/"))
                    processUserIdWithForwardToRecipesServlet(req, resp, pathInfo);
                else
                    processPostRequest(req, resp);

            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        String pathInfo = req.getPathInfo();

        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String[] pathSegments = pathInfo.substring(1).split("/");
        try {
            int userId = Integer.parseInt(pathSegments[0]);
            if (pathSegments.length > 1) {
                req.setAttribute("userId", userId);
                // forward to another servlet if next parts of uri are correct
            } else {
                // simulating successful DELETE operation
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private Predicate<User> createPredicateForFilteringUsersByQueryParams(String id,
                                                                          String name,
                                                                          String email,
                                                                          String phone,
                                                                          String website) {
        return user ->
                (id == null || id.equals(String.valueOf(user.getId()))) &&
                        (name == null || name.equals(user.getName())) &&
                        (email == null || email.equals(user.getEmail())) &&
                        (phone == null || phone.equals(user.getPhone())) &&
                        (website == null || website.equals(user.getWebsite()));
    }

    private void processUserIdWithForwardToRecipesServlet(HttpServletRequest req, HttpServletResponse resp, String pathInfo) {
        String[] pathSegments = pathInfo.substring(1).split("/");
        try {
            int userId = Integer.parseInt(pathSegments[0]);
            req.setAttribute("userId", userId);
            // forward to the recipes servlet
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void processPostRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Reader reader = req.getReader();
        try {
            User deserializedUser = JsonSerializationUtil.deserializeObjectFromJson(reader, User.class);
            int userId = deserializedUser.getId();
            if (userId <= 10) {
                throw new IOException();
            }
            // Simulate successful POST operation
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().println(String.format("{\"id\" : %d}", userId));
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
