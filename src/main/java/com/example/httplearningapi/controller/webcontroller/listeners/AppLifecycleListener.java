package com.example.httplearningapi.controller.webcontroller.listeners;

import com.example.httplearningapi.util.HibernateUtil;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppLifecycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HibernateUtil.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HibernateUtil.destroy();
    }
}
