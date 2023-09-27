package com.example.httplearningapi.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration().configure();
            sessionFactory = configuration.buildSessionFactory();
        } catch (HibernateException e) {
            System.err.println("Error creating Session: " + e);
            throw new ExceptionInInitializerError();
        }
    }

    private HibernateUtil() {}

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
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
