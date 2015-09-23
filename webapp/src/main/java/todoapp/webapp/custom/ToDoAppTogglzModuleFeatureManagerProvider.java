package todoapp.webapp.custom;

import org.isisaddons.module.togglz.glue.spi.TogglzModuleFeatureManagerProviderAbstract;

import todoapp.dom.ToDoAppFeature;

/**
 * Registered in META-INF/services, as per http://www.togglz.org/documentation/advanced-config.html
 */
public class ToDoAppTogglzModuleFeatureManagerProvider extends TogglzModuleFeatureManagerProviderAbstract {

    public ToDoAppTogglzModuleFeatureManagerProvider() {
        super(ToDoAppFeature.class);
    }

}