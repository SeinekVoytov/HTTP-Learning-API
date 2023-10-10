package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.util.HibernateUtil;
import com.example.httplearningapi.model.entities.user.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {
    @Override
    public Optional<User> getById(int id) {
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

    @Override
    public List<User> getAll() {
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
