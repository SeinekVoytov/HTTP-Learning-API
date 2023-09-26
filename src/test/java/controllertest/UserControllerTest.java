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
        Optional<User> testUser = USER_CONTROLLER.getUserById(1);
        assertTrue(testUser.isPresent());
    }

    @Test
    public void testGetUsers() {
        List<User> users = USER_CONTROLLER.getUsers();
        assertEquals(NUMBER_OF_USERS_IN_DATABASE, users.size());
    }

}
