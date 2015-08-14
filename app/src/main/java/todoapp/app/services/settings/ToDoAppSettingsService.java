/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package todoapp.app.services.settings;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;

import org.isisaddons.module.settings.dom.ApplicationSettingsServiceRW;
import org.isisaddons.module.settings.dom.UserSettingsServiceRW;
import org.isisaddons.module.settings.dom.jdo.ApplicationSettingJdo;
import org.isisaddons.module.settings.dom.jdo.UserSettingJdo;

/**
 * A wrapper around {@link org.isisaddons.module.settings.dom.UserSettingsService}.
 */
@DomainService(nature = NatureOfService.VIEW_MENU_ONLY)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.TERTIARY,
        named = "Settings",
        menuOrder = "500"
)
public class ToDoAppSettingsService {

    //region > listAllSettings (for application)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            named = "Application Settings",
            cssClassFa = "fa-cog"
    )
    @MemberOrder(sequence = "10")
    public List<ApplicationSettingJdo> listAllSettings() {
        // downcast using raw list
        final List applicationSettings = applicationSettingsService.listAll();
        return applicationSettings;
    }
    //endregion

    //region > listAllSettings (for user); programmatic
    @Programmatic
    public List<UserSettingJdo> listAllSettings(final String user) {
        // downcast using raw list
        final List userSettings = userSettingsService.listAllFor(user);
        return userSettings;
    }
    //endregion

    //region > behaviour
    @Programmatic
    public <T extends Enum<T>> T get(final Class<T> enumCls) {
        final ApplicationSettingJdo setting = findSetting(enumCls);
        if (setting == null) {
            return null;
        }
        final String valueAsString = setting.getValueAsString();
        final T[] enumConstants = enumCls.getEnumConstants();
        for (final T enumConstant : enumConstants) {
            if(enumConstant.name().equals(valueAsString)) {
                return enumConstant;
            }
        }
        return null;
    }

    @Programmatic
    public <T extends Enum<T>> void set(final Class<T> enumCls, final T value) {
        final ApplicationSettingJdo setting = findSetting(enumCls);
        if(setting == null) {
            applicationSettingsService.newString(enumCls.getCanonicalName(), enumCls.getSimpleName(), value.name());
        } else {
            setting.updateAsString(value.name());
        }
    }

    protected <T extends Enum<T>> ApplicationSettingJdo findSetting(final Class<T> enumCls) {
        return (ApplicationSettingJdo) applicationSettingsService.find(enumCls.getCanonicalName());
    }
    //endregion



    //region > injected services
    @javax.inject.Inject
    private ApplicationSettingsServiceRW applicationSettingsService;

    @javax.inject.Inject
    private UserSettingsServiceRW userSettingsService;

    //endregion

}
