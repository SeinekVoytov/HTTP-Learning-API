package com.example.httplearningapi.model.dao;

import com.example.httplearningapi.model.entities.prescription.Prescription;

public class PrescriptionDao extends AbstractDao<Prescription> {

    public PrescriptionDao() {
        super(Prescription.class);
    }
}
