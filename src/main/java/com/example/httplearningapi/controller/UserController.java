package com.example.httplearningapi.controller;

import com.example.httplearningapi.HibernateUtil;
import com.example.httplearningapi.model.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserController {
    public Optional<User> getUserById(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        User user = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.ofNullable(user);
    }

    public List<User> getUsers() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        List<User> users = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return users;
    }
}
