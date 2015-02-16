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
package todoapp.dom.module.todoitem;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import com.google.common.eventbus.Subscribe;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.EventBusService;

@DomainService(nature = NatureOfService.DOMAIN)
public class ApplicationTenancySubscriber {

    //region > postConstruct, preDestroy
    /**
     * Registers this service with the {@link org.apache.isis.applib.services.eventbus.EventBusService}.
     *
     * <p>
     *     Because this service is a singleton, this is called during initial bootstrap.
     * </p>
     */
    @Programmatic
    @PostConstruct
    public void postConstruct() {
        eventBusService.register(this);
    }

    /**
     * Unregisters this service from the {@link org.apache.isis.applib.services.eventbus.EventBusService}.
     *
     * <p>
     *     Because this service is a singleton, this is only done when the system is shutdown.
     * </p>
     */
    @Programmatic
    @PreDestroy
    public void preDestroy() {
        eventBusService.unregister(this);
    }
    //endregion

    //region > on
    @Programmatic
    @Subscribe
    public void on(ApplicationTenancy.DeleteDomainEvent deleteDomainEvent) {
        final ApplicationTenancy source = deleteDomainEvent.getSource();
        switch (deleteDomainEvent.getEventPhase()) {
            case DISABLE :
                final String atPath = source.getPath();
                List<ToDoItem> items = toDoItems.findByAtPath(atPath);

        }
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private EventBusService eventBusService;

    @javax.inject.Inject
    private ToDoItems toDoItems;
    //endregion

}
