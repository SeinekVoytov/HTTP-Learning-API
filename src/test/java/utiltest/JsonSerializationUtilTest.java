package utiltest;

import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.model.user.User;
import com.example.httplearningapi.util.JsonSerializationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonSerializationUtilTest {

    private static final String USER1_JSON = "{\"id\":1,\"name\":\"Seinek Voytov\",\"email\":\"seinek@neuroverse.net\",\"address\":{\"country\":\"Belarus\",\"city\":\"Minsk\",\"street\":\"214 Independence Avenue\",\"geo\":{\"lat\":-27.9394,\"lon\":86.9083}},\"phone\":\"454-11-89390\",\"website\":\"dreamtechgenius.org\",\"company\":{\"name\":\"Tech Innovators\",\"catchPhrase\":\"Unlocking the Future\"}}";
    @Test
    public void jsonStringForRetrievedUserFromDBIsEqualToExpectedJsonStringTest() {
        UserController userController = new UserController();
        User user = userController.getUserById(1)
                        .orElseGet(Assertions::fail);

        try {
            String jsonSerializationResult = JsonSerializationUtil.serializeObjectToJsonString(user);
            assertEquals(USER1_JSON, jsonSerializationResult, "Serialization result is wrong");
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void jsonStringForRetrievedUserFromDBIsProperlyWrittenToOutputStreamTest() {
        UserController userController = new UserController();
        User user = userController.getUserById(1)
                .orElseGet(Assertions::fail);

        try {
            StringWriter writer = new StringWriter();
            JsonSerializationUtil.serializeObjectToJsonStream(user, writer);
            assertEquals(USER1_JSON, writer.toString(), "Serialization result is wrong");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
