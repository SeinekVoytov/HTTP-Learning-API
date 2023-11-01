package daotest;

import com.example.httplearningapi.model.dao.Dao;
import com.example.httplearningapi.model.dao.PostDao;
import com.example.httplearningapi.model.entities.user.Post;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HibernateUtilSetupExtension.class)
public class PostDaoTest implements DaoTest {

    private static final int NUMBER_OF_PRESCRIPTIONS_IN_DATABASE = 100;
    private static final Dao<Post> PRESCRIPTION_DAO = new PostDao();

    @Test
    @Override
    public void retrievedEntityFromDBIsNotNullTest() {
        Optional<Post> testPrescriptionOptional = PRESCRIPTION_DAO.getById(1);
        if (testPrescriptionOptional.isEmpty()) {
            fail("User is not retrieved from Database");
        }

        Post testPrescription = testPrescriptionOptional.get();
        assertTrue(testPrescription.getAuthor() != null &&
                testPrescription.getBody() != null &&
                testPrescription.getTitle() != null, "Post fields are null");
    }

    @Test
    @Override
    public void numberOfUsersRetrievedFromDBIsEqualToExpectedNumberTest() {
        List<Post> prescriptions = PRESCRIPTION_DAO.getAll();
        assertEquals(NUMBER_OF_PRESCRIPTIONS_IN_DATABASE, prescriptions.size(),
                "Actual number of Users in Database is not equal to Expected number");
    }
}
