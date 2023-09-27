package utiltest;

import com.example.httplearningapi.util.HibernateUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateUtilTest {

    @Test
    public void sessionFactoryInitializationTest() {
        assertNotNull(HibernateUtil.getSessionFactory(), "SessionFactory shouldn't be null");
    }
}
