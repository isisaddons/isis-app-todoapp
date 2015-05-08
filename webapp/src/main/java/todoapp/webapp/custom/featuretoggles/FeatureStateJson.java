package todoapp.webapp.custom.featuretoggles;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import org.togglz.core.repository.FeatureState;

import todoapp.dom.module.featuretoggles.ToDoAppFeature;

/**
 * Intermediary between Togglz's {@link FeatureState} and the JSON that is stored as an {@link org.isisaddons.module.settings.dom.ApplicationSetting} using the {@link org.isisaddons.module.settings.dom.ApplicationSettingsService}.
 */
class FeatureStateJson {

    String name;
    String strategyId;
    boolean enabled;
    Map<String,String> parameters = new LinkedHashMap<>();

    /**
     * For GSON to deserialize from json.
     */
    public FeatureStateJson() {
    }

    /**
     * Convert from existing {@link FeatureState}.
     */
    FeatureStateJson(final FeatureState featureState) {
        name = featureState.getFeature().name();
        enabled = featureState.isEnabled();
        strategyId = featureState.getStrategyId();
        parameters = Maps.newLinkedHashMap(featureState.getParameterMap());
    }

    /**
     * Convert this json into a Togglz' {@link FeatureState}.
     */
    FeatureState asFeatureState() {
        final ToDoAppFeature toDoAppFeature = ToDoAppFeature.valueOf(name);
        final FeatureState featureState = new FeatureState(toDoAppFeature);
        featureState.setEnabled(enabled);
        for (Map.Entry<String, String> keyValue : parameters.entrySet()) {
            featureState.setParameter(keyValue.getKey(), keyValue.getValue());
        }
        featureState.setStrategyId(strategyId);
        return featureState;
    }
}
