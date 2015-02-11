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
import java.util.UUID;
import org.isisaddons.module.command.dom.CommandJdo;
import org.isisaddons.module.command.dom.CommandServiceJdoRepository;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.Identifier;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.BookmarkPolicy;
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
        menuOrder = "20"
)
public class CommandsMenuService extends AbstractService {

    public static abstract class PropertyDomainEvent<T> extends ToDoAppDomainModule.PropertyDomainEvent<CommandsMenuService, T> {
        public PropertyDomainEvent(final CommandsMenuService source, final Identifier identifier) {
            super(source, identifier);
        }

        public PropertyDomainEvent(final CommandsMenuService source, final Identifier identifier, final T oldValue, final T newValue) {
            super(source, identifier, oldValue, newValue);
        }
    }

    public static abstract class CollectionDomainEvent<T> extends ToDoAppDomainModule.CollectionDomainEvent<CommandsMenuService, T> {
        public CollectionDomainEvent(final CommandsMenuService source, final Identifier identifier, final Of of) {
            super(source, identifier, of);
        }

        public CollectionDomainEvent(final CommandsMenuService source, final Identifier identifier, final Of of, final T value) {
            super(source, identifier, of, value);
        }
    }

    public static abstract class ActionDomainEvent extends ToDoAppDomainModule.ActionDomainEvent<CommandsMenuService> {
        public ActionDomainEvent(final CommandsMenuService source, final Identifier identifier) {
            super(source, identifier);
        }

        public ActionDomainEvent(final CommandsMenuService source, final Identifier identifier, final Object... arguments) {
            super(source, identifier, arguments);
        }

        public ActionDomainEvent(final CommandsMenuService source, final Identifier identifier, final List<Object> arguments) {
            super(source, identifier, arguments);
        }
    }

    // //////////////////////////////////////

    public static class CommandsCurrentlyRunningDomainEvent extends ActionDomainEvent {
        public CommandsCurrentlyRunningDomainEvent(final CommandsMenuService source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = CommandsCurrentlyRunningDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT
    )
    @MemberOrder(sequence="1")
    public List<CommandJdo> commandsCurrentlyRunning() {
        return commandServiceRepository.findCurrent();
    }
    public boolean hideCommandsCurrentlyRunning() {
        return commandServiceRepository == null;
    }

    // //////////////////////////////////////

    public static class CommandsPreviouslyRanDomainEvent extends ActionDomainEvent {
        public CommandsPreviouslyRanDomainEvent(final CommandsMenuService source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = CommandsPreviouslyRanDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence="2")
    public List<CommandJdo> commandsPreviouslyRan() {
        return commandServiceRepository.findCompleted();
    }
    public boolean hideCommandsPreviouslyRan() {
        return commandServiceRepository == null;
    }

    // //////////////////////////////////////

    public static class LookupCommandDomainEvent extends ActionDomainEvent {
        public LookupCommandDomainEvent(final CommandsMenuService source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = LookupCommandDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence="10.3")
    public CommandJdo lookupCommand(
            @ParameterLayout(named="Transaction Id")
            final UUID transactionId) {
        return commandServiceRepository.findByTransactionId(transactionId);
    }
    public boolean hideLookupCommand() {
        return commandServiceRepository == null;
    }

    // //////////////////////////////////////

    public static class FindCommandsDomainEvent extends ActionDomainEvent {
        public FindCommandsDomainEvent(final CommandsMenuService source, final Identifier identifier, final Object... args) {
            super(source, identifier, args);
        }
    }

    @Action(
            domainEvent = FindCommandsDomainEvent.class,
            semantics = SemanticsOf.SAFE
    )
    @MemberOrder(sequence="10.4")
    public List<CommandJdo> findCommands(
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="From")
            final LocalDate from,
            @Parameter(optionality= Optionality.OPTIONAL)
            @ParameterLayout(named="To")
            final LocalDate to) {
        return commandServiceRepository.findByFromAndTo(from, to);
    }
    public boolean hideFindCommands() {
        return commandServiceRepository == null;
    }
    public LocalDate default0FindCommands() {
        return clockService.now().minusDays(7);
    }
    public LocalDate default1FindCommands() {
        return clockService.now();
    }

    // //////////////////////////////////////

    @javax.inject.Inject
    private CommandServiceJdoRepository commandServiceRepository;

    @javax.inject.Inject
    private ClockService clockService;

}

