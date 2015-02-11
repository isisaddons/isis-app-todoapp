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
package todoapp.dom.module.activity;

import todoapp.dom.ToDoAppDomainModule;

import java.util.List;
import org.isisaddons.module.audit.dom.AuditEntry;
import org.isisaddons.module.audit.dom.AuditingServiceRepository;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.clock.ClockService;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Activity",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "30"
)
public class AuditingMenuService extends AbstractService {

    public static abstract class PropertyDomainEvent<T> extends ToDoAppDomainModule.PropertyDomainEvent<AuditingMenuService, T> {
        public PropertyDomainEvent(final AuditingMenuService source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final AuditingMenuService source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends ToDoAppDomainModule.CollectionDomainEvent<AuditingMenuService, T> {
        public CollectionDomainEvent(final AuditingMenuService source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final AuditingMenuService source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends ToDoAppDomainModule.ActionDomainEvent<AuditingMenuService> {
        public ActionDomainEvent(final AuditingMenuService source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final AuditingMenuService source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final AuditingMenuService source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class FindAuditEntriesDomainEvent extends ActionDomainEvent {
        public FindAuditEntriesDomainEvent(final AuditingMenuService source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = FindAuditEntriesDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence="1")
    public List<AuditEntry> findAuditEntries(
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality=Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        return auditingServiceRepository.findByFromAndTo(from, to);
    }
    public boolean hideFindAuditEntries() {
        return auditingServiceRepository == null;
    }
    public LocalDate default0FindAuditEntries() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default1FindAuditEntries() {
        return clockService.now();
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private AuditingServiceRepository auditingServiceRepository;
    
    @javax.inject.Inject
    private ClockService clockService;

}

