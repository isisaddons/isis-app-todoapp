/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package todoapp.dom.module.appuser;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.eventbus.EventBusService;
import todoapp.dom.seed.tenancies.UsersTenancy;


@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ApplicationUserAndTenancySubscriptions extends AbstractService {

    //region > LOG
    private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ApplicationUserAndTenancySubscriptions.class);
    //endregion

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

    //region > on NewXxxUser
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ApplicationUsers.NewLocalUserDomainEvent ev) {
        switch(ev.getEventPhase()) {
            case EXECUTED:
                final ApplicationUser newUser = (ApplicationUser) ev.getReturnValue();
                createTenancyIfRequired(newUser);
                break;
        }
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ApplicationUsers.NewDelegateUserDomainEvent ev) {
        switch(ev.getEventPhase()) {
            case EXECUTED:
                final ApplicationUser newUser = (ApplicationUser) ev.getReturnValue();
                createTenancyIfRequired(newUser);
                break;
        }
    }

    protected void createTenancyIfRequired(final ApplicationUser newUser) {
        final String username = newUser.getName();
        final String atPath = UsersTenancy.TENANCY_PATH + username;  // eg "/users/fred"
        ApplicationTenancy applicationTenancy = applicationTenancies.findTenancyByName(atPath);
        if(applicationTenancy == null) {
            final ApplicationTenancy globalTenancy = applicationTenancies.findTenancyByPath(UsersTenancy.TENANCY_PATH);
            applicationTenancy = applicationTenancies.newTenancy(username, atPath, globalTenancy);
        }
        newUser.setTenancy(applicationTenancy);
    }
    //endregion

    //region > on DeleteXxxUser
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ApplicationUser.DeleteDomainEvent ev) {
        switch(ev.getEventPhase()) {
            case EXECUTING:
                final ApplicationUser user = ev.getSource();
                deleteTenancyIfRequired(user);
                break;
        }
    }

    protected void deleteTenancyIfRequired(final ApplicationUser newUser) {
        final String username = newUser.getName();
        final String atPath = UsersTenancy.TENANCY_PATH + username;
        final ApplicationTenancy applicationTenancy = applicationTenancies.findTenancyByPath(atPath);
        if(applicationTenancy != null) {
            applicationTenancy.delete(true);
        }
    }
    //endregion

    //region > on DeleteTenancy
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ApplicationTenancy.DeleteDomainEvent ev) {
        switch(ev.getEventPhase()) {
            case DISABLE:
                final String path = ev.getSource().getPath();
                final String username = path.substring(1);
                final ApplicationUser applicationUser = applicationUsers.findUserByUsername(username);
                if(applicationUser != null) {
                    ev.veto("User '%s' is associated with this application tenancy", username);
                }
        }
    }

    //endregion

    //region > injected services
    @javax.inject.Inject
    private ApplicationTenancies applicationTenancies;

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private EventBusService eventBusService;
    //endregion


}

