package com.example.httplearningapi.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            SESSION_FACTORY = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Error creating Session Factory: " + e);
            throw new ExceptionInInitializerError();
        }
    }

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void closeSessionFactory() {
        if (SESSION_FACTORY != null) {
            try {
                SESSION_FACTORY.close();
            }
            catch (HibernateException e) {
                System.err.println("Couldn't close SessionFactory");
            }
        }
    }

}
