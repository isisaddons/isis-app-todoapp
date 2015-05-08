package todoapp.dom.module.featuretoggles;

import org.togglz.core.manager.FeatureManager;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

/**
 * Simply exposes the feature enum API as a service.
 *
 * <p>
 *
 * </p>
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class FeatureToggleService {

    private FeatureManager manager;

    @Programmatic
    public boolean isActive(final ToDoAppFeature feature) {
        return feature.isActive();
    }

}
