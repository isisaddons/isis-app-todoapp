package todoapp.dom;

import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum ToDoAppFeature implements org.togglz.core.Feature {

    /**
     * Use typesafe queries (rather than JDOQL).
     */
    @Label("Use DataNucleus type-safe queries to query database rather than JDOQL")
    @EnabledByDefault
    useTypeSafeQueries;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
