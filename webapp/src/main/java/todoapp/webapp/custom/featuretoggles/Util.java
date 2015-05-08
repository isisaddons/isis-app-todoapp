package todoapp.webapp.custom.featuretoggles;

import org.apache.isis.core.metamodel.services.ServicesInjectorSpi;
import org.apache.isis.core.runtime.system.context.IsisContext;
import org.apache.isis.core.runtime.system.persistence.PersistenceSession;

class Util {

    static <T> T lookupService(final Class<T> serviceClass) {
        return getServicesInjector().lookupService(serviceClass);
    }

    private static ServicesInjectorSpi getServicesInjector() {
        return getPersistenceSession().getServicesInjector();
    }

    static PersistenceSession getPersistenceSession() {
        return IsisContext.getPersistenceSession();
    }

}
