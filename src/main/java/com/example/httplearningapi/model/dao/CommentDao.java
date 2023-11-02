package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.comment.Comment;
import com.example.httplearningapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class CommentDao implements Dao<Comment> {
    @Override
    public Optional<Comment> getById(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Comment comment = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            comment = session.get(Comment.class, id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> getAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        List<Comment> comments = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            comments = session.createQuery("from Comment", Comment.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return comments;
    }
}
