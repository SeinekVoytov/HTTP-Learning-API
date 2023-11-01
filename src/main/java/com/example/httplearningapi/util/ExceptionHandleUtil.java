package com.example.httplearningapi.util;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.NoSuchElementException;

public class ExceptionHandleUtil {

    public static void processException(Exception e, HttpServletResponse resp) throws IOException {
        Class<? extends Exception> exceptionClass = e.getClass();
        if (exceptionClass == NumberFormatException.class) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else if (exceptionClass == NoSuchElementException.class) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
