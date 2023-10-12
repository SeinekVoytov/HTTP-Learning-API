package com.example.httplearningapi.controller.webcontroller.servlets;

import jakarta.servlet.ServletException;

import java.io.IOException;

@FunctionalInterface
interface HandleProcessor<T> {
    void process(T t) throws ServletException, IOException;
}
