package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.prescription.Prescription;
import com.example.httplearningapi.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PrescriptionDao implements Dao<Prescription> {
    @Override
    public Optional<Prescription> getById(int id) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        Prescription prescription = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            prescription = session.get(Prescription.class, id);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return Optional.ofNullable(prescription);
    }

    @Override
    public List<Prescription> getAll() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction transaction = null;
        List<Prescription> prescriptions = null;

        try (Session session = sf.openSession()) {
            transaction = session.beginTransaction();
            prescriptions = session.createQuery("from Prescription ", Prescription.class).list();
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return prescriptions;
    }
}
