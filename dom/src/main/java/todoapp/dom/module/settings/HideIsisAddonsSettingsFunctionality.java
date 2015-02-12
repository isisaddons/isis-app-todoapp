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
package todoapp.dom.module.settings;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import com.google.common.eventbus.Subscribe;
import org.isisaddons.module.settings.SettingsModule;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.AbstractDomainEvent;
import org.apache.isis.applib.services.eventbus.EventBusService;

/**
 * This service simply permanently hides the menus from the {@link org.isisaddons.module.settings.dom.ApplicationSettingsService} and
 * {@link org.isisaddons.module.settings.dom.UserSettingsService} implementations.
 *
 * <p>
 *     Instead we expose settings using the {@link todoapp.dom.module.settings.ToDoAppSettingsService} wrapper.
 * </p>
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class HideIsisAddonsSettingsFunctionality {

    //region > postConstruct, preDestroy
    @Programmatic
    @PostConstruct
    public void postConstruct() {
        eventBusService.register(this);
    }

    @Programmatic
    @PreDestroy
    public void preDestroy() {
        eventBusService.unregister(this);
    }
    //endregion

    @Programmatic
    @Subscribe
    public void on(final SettingsModule.ActionDomainEvent<?> event) {
        if(event.getEventPhase() == AbstractDomainEvent.Phase.HIDE) {
            event.hide();
        }
    }

    //region > injected services
    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion

}
