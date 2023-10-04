package com.example.httplearningapi.util;

import jakarta.servlet.http.HttpServletResponse;

import java.util.NoSuchElementException;

public class ExceptionHandleUtil {

    public static void processException(Exception e, HttpServletResponse resp) {
        Class<? extends Exception> exceptionClass = e.getClass();
        if (exceptionClass == NumberFormatException.class) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        else if (exceptionClass == NoSuchElementException.class) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
