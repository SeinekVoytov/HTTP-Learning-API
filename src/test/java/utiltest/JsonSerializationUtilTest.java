package utiltest;

import com.example.httplearningapi.controller.UserController;
import com.example.httplearningapi.model.user.User;
import com.example.httplearningapi.util.JsonSerializationUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(HibernateUtilSetupExtension.class)
public class JsonSerializationUtilTest {

    private static final String USER1_JSON = "{\n" +
            "  \"id\" : 1,\n" +
            "  \"name\" : \"Seinek Voytov\",\n" +
            "  \"email\" : \"seinek@neuroverse.net\",\n" +
            "  \"address\" : {\n" +
            "    \"country\" : \"Belarus\",\n" +
            "    \"city\" : \"Minsk\",\n" +
            "    \"street\" : \"214 Independence Avenue\",\n" +
            "    \"geo\" : {\n" +
            "      \"lat\" : -27.9394,\n" +
            "      \"lon\" : 86.9083\n" +
            "    }\n" +
            "  },\n" +
            "  \"phone\" : \"454-11-89390\",\n" +
            "  \"website\" : \"dreamtechgenius.org\",\n" +
            "  \"company\" : {\n" +
            "    \"name\" : \"Tech Innovators\",\n" +
            "    \"catchPhrase\" : \"Unlocking the Future\"\n" +
            "  }\n" +
            "}";
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

    @Test
    public void testUserJsonDeserializationAndEqualityWithDBUser() {
        UserController userController = new UserController();
        User expectedUser = userController.getUserById(1)
                .orElseGet(Assertions::fail);

        try {
            StringReader reader = new StringReader(USER1_JSON);
            User deserializedUser = JsonSerializationUtil.deserializeObjectFromJson(reader, User.class);
            assertEquals(expectedUser, deserializedUser, "Deserialized and expected objects are not equal");
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
