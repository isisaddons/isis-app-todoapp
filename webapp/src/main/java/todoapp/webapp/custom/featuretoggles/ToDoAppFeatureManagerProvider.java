package todoapp.webapp.custom.featuretoggles;

import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.spi.FeatureManagerProvider;

import todoapp.dom.module.featuretoggles.ToDoAppFeature;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class ToDoAppFeatureManagerProvider implements FeatureManagerProvider {

    private static FeatureManager featureManager;

    @Override
    public int priority() {
        return 25;
    }

    @Override
    public synchronized FeatureManager getFeatureManager() {

        if (featureManager == null) {
            featureManager = new FeatureManagerBuilder()
                    .featureEnum(ToDoAppFeature.class)
                    .stateRepository(new StateRepositoryUsingApplicationSettingsWithGson())
                    .userProvider(new UserProviderUsingServletPrincipal())
                    .build();
        }

        return featureManager;
    }
}