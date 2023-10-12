package daotest;

import com.example.httplearningapi.model.dao.UserDao;
import com.example.httplearningapi.model.entities.user.User;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HibernateUtilSetupExtension.class)
public class UserDaoTest {

    private static final int NUMBER_OF_USERS_IN_DATABASE = 10;
    private static final UserDao USER_DAO = new UserDao();

    @Test
    public void retrievedUserFromDBIsNotNullTest() {
        Optional<User> testUserOptional = USER_DAO.getById(1);
        if (testUserOptional.isEmpty()) {
            fail("User is not retrieved from Database");
        }

        User testUser = testUserOptional.get();
        assertTrue(testUser.getAddress() != null &&
                testUser.getAddress().getGeo() != null &&
                testUser.getCompany() != null, "User fields are null");
    }

    @Test
    public void numberOfUsersRetrievedFromDBIsEqualToExpectedNumberTest() {
        List<User> users = USER_DAO.getAll();
        assertEquals(NUMBER_OF_USERS_IN_DATABASE, users.size(),
                "Actual number of Users in Database is not equal to Expected number");
    }

}
