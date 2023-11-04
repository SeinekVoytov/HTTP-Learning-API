package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> implements Dao<T> {

    private final Class<T> targetClass;

    public AbstractDao(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Optional<T> getById(int id) {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        T entity = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            entity = session.get(targetClass, id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> getAll() {

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        List<T> entities = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            entities = session.createQuery("from " + targetClass.getSimpleName(), targetClass).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return entities;
    }
}
