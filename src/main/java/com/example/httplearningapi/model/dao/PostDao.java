package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.user.Post;
import com.example.httplearningapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PostDao implements Dao<Post> {
    @Override
    public Optional<Post> getById(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Post post = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            post = session.get(Post.class, id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> getAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        List<Post> posts = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            posts = session.createQuery("from Post", Post.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return posts;
    }
}
