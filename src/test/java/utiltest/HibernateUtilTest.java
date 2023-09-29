package utiltest;

import com.example.httplearningapi.util.HibernateUtil;
import extensions.HibernateUtilSetupExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(HibernateUtilSetupExtension.class)
public class HibernateUtilTest {

    @Test
    public void sessionFactoryInitializationTest() {
        assertNotNull(HibernateUtil.getSessionFactory(), "SessionFactory shouldn't be null");
    }
}
