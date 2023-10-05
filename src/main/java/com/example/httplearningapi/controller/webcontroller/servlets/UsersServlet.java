package com.example.httplearningapi.controller.webcontroller.servlets;

import com.example.httplearningapi.model.user.User;
import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.util.ExceptionHandleUtil;
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
                    users = this.filterUsersByQueryParams(req, users);
                }

                JsonSerializationUtil.serializeObjectToJsonStream(users, resp.getWriter());
                resp.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            int userId = Integer.parseInt(pathInfo.split("/")[1]);

            if (pathInfo.matches("^/.+/recipes.*$")) {
                req.setAttribute("userId", userId);
                // forward to another servlet
                return;
            }

            if (pathInfo.matches("^/.+/?$")) {
                User requestedUser = userController.getUserById(userId).orElseThrow();
                JsonSerializationUtil.serializeObjectToJsonStream(requestedUser, resp.getWriter());
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo != null && !pathInfo.equals("/") && !pathInfo.matches("^/[^/]+/recipes$")) { //
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            if (pathInfo == null || pathInfo.equals("/")) {
                this.simulateSuccessfulPostOperation(resp);
                return;
            }

            int userId = Integer.parseInt(pathInfo.substring(1, pathInfo.indexOf('/', 1)));
            req.setAttribute("userId", userId);
            // forward to the recipes servlet

        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        processPutOrDeleteRequest(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        processPutOrDeleteRequest(req, resp);
;    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void processPutOrDeleteRequest(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            int userId = Integer.parseInt(pathInfo.substring(1).split("/")[0]);

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

        } catch (Exception e) {
            ExceptionHandleUtil.processException(e, resp);
        }
    }


    private void simulateSuccessfulPostOperation(HttpServletResponse resp) throws IOException {
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().println("{\"id\" : 11}");
    }

    private void simulateSuccessfulPutOperation(HttpServletResponse resp, int id) throws IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(String.format("{\"id\" : %d}", id));
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
}
