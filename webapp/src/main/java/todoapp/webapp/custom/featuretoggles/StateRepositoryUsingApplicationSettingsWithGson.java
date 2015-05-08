package todoapp.webapp.custom.featuretoggles;

import com.google.common.base.Strings;
import com.google.gson.Gson;

import org.togglz.core.Feature;
import org.togglz.core.repository.FeatureState;
import org.togglz.core.repository.StateRepository;

import org.apache.isis.core.runtime.system.context.IsisContext;

import org.isisaddons.module.settings.dom.ApplicationSetting;
import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo;

import todoapp.dom.module.featuretoggles.FeatureToggleService;

class StateRepositoryUsingApplicationSettingsWithGson implements StateRepository {

    private final Gson gson;

    public StateRepositoryUsingApplicationSettingsWithGson() {
        gson = new Gson();
    }

    @Override
    public FeatureState getFeatureState(final Feature feature) {
        return IsisContext.doInSession(() -> {
            final ApplicationSetting setting = findSetting(feature);
            if (setting == null) {
                return null;
            }

            final String json = setting.valueAsString();
            if(Strings.isNullOrEmpty(json)) {
                return null;
            }
            final FeatureStateJson featureStateJson =
                    gson.fromJson(json, FeatureStateJson.class);
            return featureStateJson.asFeatureState();
        });
    }

    @Override
    public void setFeatureState(final FeatureState featureState) {

        IsisContext.doInSession(() -> {
            final ApplicationSettingJdo setting = findSettingAutocreate(featureState);

            final FeatureStateJson featureStateJson = new FeatureStateJson(featureState);
            final String json = gson.toJson(featureStateJson);

            setting.updateAsString(json);
        });
    }

    private ApplicationSetting findSetting(final Feature feature) {
        final String featureSettingKey = settingKeyFor(feature);
        final ApplicationSettingsServiceRW applicationSettingsService =
                lookupService(ApplicationSettingsServiceRW.class);

        final ApplicationSetting setting = applicationSettingsService.find(featureSettingKey);
        if (setting == null) {
            return null;
        }
        return setting;
    }

    private ApplicationSettingJdo findSettingAutocreate(final FeatureState featureState) {
        final String featureSettingKey = settingKeyFor(featureState.getFeature());

        final ApplicationSettingsServiceRW applicationSettingsService =
                lookupService(ApplicationSettingsServiceRW.class);

        ApplicationSettingJdo setting =
                (ApplicationSettingJdo) applicationSettingsService.find(featureSettingKey);
        if (setting == null) {
            setting = (ApplicationSettingJdo) applicationSettingsService.newString(featureSettingKey, "", "");
        }
        return setting;
    }

    private String settingKeyFor(final Feature feature) {
        return FeatureToggleService.class.getName() + "." + feature.name();
    }

    private <T> T lookupService(final Class<T> serviceClass) {
        return Util.lookupService(serviceClass);
    }

}
