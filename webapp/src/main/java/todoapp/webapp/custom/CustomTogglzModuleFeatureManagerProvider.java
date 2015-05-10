package todoapp.webapp.custom;

import org.isisaddons.module.togglz.glue.spi.TogglzModuleFeatureManagerProviderAbstract;

import todoapp.dom.module.featuretoggles.ToDoAppFeature;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class CustomTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {

    public CustomTogglzModuleFeatureManagerProvider() {
        super(ToDoAppFeature.class);
    }

}