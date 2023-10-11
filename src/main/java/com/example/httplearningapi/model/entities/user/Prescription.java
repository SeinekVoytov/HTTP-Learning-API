package com.example.httplearningapi.model.entities.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Prescriptions")
@JsonAutoDetect
public class Prescription {

    @Id
    private int id;

    @Column(name = "patient_id", insertable = false, updatable = false)
    private int patient_id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private User patient;

    @Column(name = "pharmacist_name")
    private String pharmacistName;

    @Column(name = "medication_name")
    private String medicationName;

    @Column(name = "expiry_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    private Date expiryDate;

    public Prescription() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public String getPharmacistName() {
        return pharmacistName;
    }

    public void setPharmacistName(String pharmacistName) {
        this.pharmacistName = pharmacistName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prescription that = (Prescription) o;
        return id == that.id && Objects.equals(patient, that.patient) && Objects.equals(pharmacistName, that.pharmacistName) && Objects.equals(medicationName, that.medicationName) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patient, pharmacistName, medicationName, expiryDate);
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "id=" + id +
                ", patient=" + patient +
                ", pharmacistName='" + pharmacistName + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
