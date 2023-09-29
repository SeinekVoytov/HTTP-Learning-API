package extensions;

import com.example.httplearningapi.util.HibernateUtil;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class HibernateUtilSetupExtension implements BeforeAllCallback, ExtensionContext.Store.CloseableResource {

    private static boolean started = false;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!started) {
            started = true;
            HibernateUtil.init();
            extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put("extensions.HibernateUtilSetupExtension", this);
        }
    }

    @Override
    public void close() {
        HibernateUtil.destroy();
    }
}
