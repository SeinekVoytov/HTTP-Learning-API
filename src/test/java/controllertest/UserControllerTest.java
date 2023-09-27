package controllertest;

import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.model.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private static final int NUMBER_OF_USERS_IN_DATABASE = 10;
    private static final UserController USER_CONTROLLER = new UserController();

    @Test
    public void testGetUserById() {
        Optional<User> testUserOptional = USER_CONTROLLER.getUserById(1);
        if (testUserOptional.isEmpty()) {
            fail("User is not retrieved from Database");
        }

        User testUser = testUserOptional.get();
        assertTrue(testUser.getAddress() != null &&
                testUser.getAddress().getGeo() != null &&
                testUser.getCompany() != null, "User fields are null");
    }

    @Test
    public void testGetUsers() {
        List<User> users = USER_CONTROLLER.getUsers();
        assertEquals(NUMBER_OF_USERS_IN_DATABASE, users.size(),
                "Actual number of Users in Database is not equal to Expected number");
    }

}