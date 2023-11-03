package daotest;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.model.dao.PrescriptionDao;
import com.example.httplearningapi.model.entities.prescription.Prescription;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HibernateUtilSetupExtension.class)
public class PrescriptionDaoTest implements DaoTest {

    private static final int NUMBER_OF_PRESCRIPTIONS_IN_DATABASE = 100;
    private static final Dao<Prescription> PRESCRIPTION_DAO = new PrescriptionDao();

    @Test
    @Override
    public void retrievedEntityFromDBIsNotNullTest() {
        Optional<Prescription> testPrescriptionOptional = PRESCRIPTION_DAO.getById(1);
        if (testPrescriptionOptional.isEmpty()) {
            fail("Prescription is not retrieved from Database");
        }

        Prescription testPrescription = testPrescriptionOptional.get();
        assertTrue(testPrescription.getPatient() != null &&
                testPrescription.getMedicationName() != null &&
                testPrescription.getExpiryDate() != null, "Prescription fields are null");
    }

    @Test
    @Override
    public void numberOfEntitiesRetrievedFromDBIsEqualToExpectedNumberTest() {
        List<Prescription> prescriptions = PRESCRIPTION_DAO.getAll();
        assertEquals(NUMBER_OF_PRESCRIPTIONS_IN_DATABASE, prescriptions.size(),
                "Actual number of Prescriptions in Database is not equal to Expected number");
    }

}
