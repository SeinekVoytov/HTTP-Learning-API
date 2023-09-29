package com.example.httplearningapi.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private HibernateUtil() {}

    public static void init() {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Error creating Session Factory: " + e);
            throw new ExceptionInInitializerError();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void destroy() {
        if (sessionFactory != null) {
            try {
                sessionFactory.close();
            }
            catch (HibernateException e) {
                System.err.println("Couldn't close SessionFactory");
            }
        }
    }

}
