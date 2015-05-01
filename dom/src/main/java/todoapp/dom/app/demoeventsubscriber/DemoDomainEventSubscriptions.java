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
package todoapp.dom.app.demoeventsubscriber;

import java.util.EventObject;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.NonRecoverableException;
import org.apache.isis.applib.RecoverableException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.CollectionDomainEvent;
import org.apache.isis.applib.services.eventbus.EventBusService;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;

import org.isisaddons.module.audit.dom.AuditingServiceMenu;
import org.isisaddons.module.command.dom.CommandServiceMenu;
import org.isisaddons.module.publishing.dom.PublishingServiceMenu;
import org.isisaddons.module.sessionlogger.dom.SessionLoggingServiceMenu;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import todoapp.dom.ToDoAppDomainModule;
import todoapp.dom.module.settings.ToDoAppSettingsService;
import todoapp.dom.module.todoitem.ToDoItem;

/**
 * Subscribes to changes made to  the {@link todoapp.dom.module.todoitem.ToDoItem} entity.
 *
 * <p>
 *     (For demo purposes) the behaviour can be influenced using {@link #subscriberBehaviour(DemoBehaviour)}.
 *     In particular, the subscriber can be used to hide/disable/validate actions, or just to perform pre- or post-execute
 *     tasks.  This also includes being set to throw an exception during the execution of the action (also in effect
 *     vetoing the change).
 * </p>
 */
@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        named = "Prototyping",
        menuOrder = "500.20")
public class DemoDomainEventSubscriptions {

    //region > LOG
    private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(DemoDomainEventSubscriptions.class);
    //endregion

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
        LOG.info("postConstruct: registering to event bus");
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
        LOG.info("preDestroy: unregistering from event bus");
        eventBusService.unregister(this);
    }
    //endregion

    //region > subscriberBehaviour


    /**
     * To demo/test what occurs if a subscriber that might veto an event.
     */
    @Action(
            semantics = SemanticsOf.IDEMPOTENT,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            named="Set subscriber behaviour",
            cssClassFa="phone"
    )
    @MemberOrder(sequence = "500.20.1")
    public void subscriberBehaviour(
            final DemoBehaviour behaviour) {
        applicationSettingsService.set(DemoBehaviour.class, behaviour);
        container.informUser("Subscriber behaviour set to: " + behaviour);
    }
    public DemoBehaviour default0SubscriberBehaviour() {
        return getSubscriberBehaviour();
    }

    @Programmatic
    public DemoBehaviour getSubscriberBehaviour() {
        return applicationSettingsService.get(DemoBehaviour.class);
    }


    private void onExecutedThrowExceptionIfSet(final ActionDomainEvent<?> ev) {
        if(ev != null && ev.getSemantics().isSafe()) {
            return;
        }
        onExecutedThrowExceptionIfSet();
    }
    private void onExecutedThrowExceptionIfSet(final PropertyDomainEvent<?, ?> ev) {
        onExecutedThrowExceptionIfSet();
    }
    private void onExecutedThrowExceptionIfSet(final CollectionDomainEvent<?, ?> ev) {
        onExecutedThrowExceptionIfSet();
    }


    private void onExecutedThrowExceptionIfSet() {
        if(getSubscriberBehaviour() == DemoBehaviour.ANY_EXECUTE_VETO_WITH_RECOVERABLE_EXCEPTION) {
            throw new RecoverableException(
                    TranslatableString.tr("Rejecting event (recoverable exception thrown)"),
                    this.getClass(), "on(ActionDomainEvent)");
        }
        if(getSubscriberBehaviour() == DemoBehaviour.ANY_EXECUTE_VETO_WITH_NON_RECOVERABLE_EXCEPTION) {
            throw new NonRecoverableException(
                    TranslatableString.tr("Rejecting event (non-recoverable exception thrown)"),
                    this.getClass(), "on(ActionDomainEvent)");
        }
        if(getSubscriberBehaviour() == DemoBehaviour.ANY_EXECUTE_VETO_WITH_OTHER_EXCEPTION) {
            throw new RuntimeException("Throwing some other exception");
        }
    }
    //endregion

    //region > on(Event) for ToDoItem-specific action events
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.CompletedEvent ev) {
        recordEvent(ev);
        logEvent(ev);
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.DescriptionDomainEvent ev) {
        logEvent(ev);
    }
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.DueByDomainEvent ev) {
        logEvent(ev);
    }
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.AttachmentDomainEvent ev) {
        logEvent(ev);
    }
    //endregion

    //region > on(Event) for sessionlogger, auditing, command, publishing events
    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final SessionLoggingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ACTIVITY_MENU_HIDE_ALL, DemoBehaviour.ACTIVITY_MENU_HIDE_SESSION_LOGGER);
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final AuditingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ACTIVITY_MENU_HIDE_ALL, DemoBehaviour.ACTIVITY_MENU_HIDE_AUDITING);
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final CommandServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ACTIVITY_MENU_HIDE_ALL, DemoBehaviour.ACTIVITY_MENU_HIDE_COMMAND);
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final PublishingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ACTIVITY_MENU_HIDE_ALL, DemoBehaviour.ACTIVITY_MENU_HIDE_PUBLISHING);
    }

    private void vetoIf(
            final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> ev,
            final DemoBehaviour... behaviours) {
        if (ev.getEventPhase() != org.apache.isis.applib.services.eventbus.AbstractDomainEvent.Phase.HIDE) {
            return;
        }
        for (final DemoBehaviour behaviour : behaviours) {
            if(getSubscriberBehaviour() == behaviour) {
                ev.hide();
            }
        }
    }
    //endregion

    //region > on(Event) ... general purpose

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final org.apache.isis.applib.services.eventbus.ActionDomainEvent<?> ev) {
        recordEvent(ev);
        switch(ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.UPDATE_COST_ACTION_HIDE) {
                    if(ev.getIdentifier().getMemberName().equals("updateCost")) {
                        ev.hide();
                    }
                }
                break;
            case DISABLE:
                if(getSubscriberBehaviour() == DemoBehaviour.UPDATE_COST_ACTION_DISABLE) {
                    if(ev.getIdentifier().getMemberName().equals("updateCost")) {
                        ev.disable("ToDoItemSubscriptions says: updateCost action disabled!");
                    }
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.UPDATE_COST_ACTION_INVALIDATE &&
                        ev.getIdentifier().getMemberName().equals("updateCost")) {
                    ev.invalidate("ToDoItemSubscriptions says: can't invoke updateCost action with these args!");
                }
                break;
            case EXECUTING:
                break;
            case EXECUTED:
                final Object source = ev.getSource();
                //container.is
                //LOG.info("Received ActionDomainEvent, " + source.toString() + ", invoked " + ev.getIdentifier().getMemberName());
                onExecutedThrowExceptionIfSet(ev);
                break;
        }
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(org.apache.isis.applib.services.eventbus.PropertyDomainEvent<?,?> ev) {
        recordEvent(ev);
        switch(ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.DESCRIPTION_PROPERTY_HIDE &&
                    ev.getIdentifier().getMemberName().equals("description")) {
                    ev.veto("");
                }
                break;
            case DISABLE:
                if(getSubscriberBehaviour() == DemoBehaviour.DESCRIPTION_PROPERTY_DISABLE &&
                    ev.getIdentifier().getMemberName().equals("description")) {
                    ev.veto("ToDoItemSubscriptions says: description property disabled!");
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.DESCRIPTION_PROPERTY_INVALIDATE &&
                    ev.getIdentifier().getMemberName().equals("description")) {
                    ev.veto("ToDoItemSubscriptions says: can't change description property to this value!");
                }
                break;
            case EXECUTING:
                break;
            case EXECUTED:
                LOG.info("Received PropertyDomainEvent, " + ev.getSource().toString() + ", changed " + ev.getIdentifier().getMemberName() + " : " + ev.getOldValue() + " -> " + ev.getNewValue());
                onExecutedThrowExceptionIfSet(ev);

                if(ev.getIdentifier().getMemberName().contains("description")) {
                    String newValue = (String) ev.getNewValue();
                    if(newValue.matches(".*demo veto.*")) {
                        throw new RecoverableException("oh no you don't! " + ev.getNewValue());
                    }
                }
                break;
        }
    }

    @Programmatic
    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(org.apache.isis.applib.services.eventbus.CollectionDomainEvent<?,?> ev) {
        recordEvent(ev);
        switch (ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.DEPENDENCIES_COLLECTION_HIDE &&
                    ev.getIdentifier().getMemberName().equals("dependencies")) {
                    ev.veto("");
                }
                if (getSubscriberBehaviour() == DemoBehaviour.SIMILAR_TO_COLLECTION_HIDE &&
                    ev.getIdentifier().getMemberName().equals("similarTo")) {
                    ev.veto("");
                }
                break;
            case DISABLE:
                if (getSubscriberBehaviour() == DemoBehaviour.DEPENDENCIES_COLLECTION_DISABLE &&
                    ev.getIdentifier().getMemberName().equals("dependencies")) {
                    ev.veto("ToDoItemSubscriptions says: dependencies collection disabled!");
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.DEPENDENCIES_COLLECTION_INVALIDATE_ADD &&
                    ev.getIdentifier().getMemberName().equals("dependencies") &&
                    ev.getOf() == CollectionDomainEvent.Of.ADD_TO ) {
                    ev.veto("ToDoItemSubscriptions says: can't add this object to dependencies collection!");
                }
                if(getSubscriberBehaviour() == DemoBehaviour.DEPENDENCIES_COLLECTION_INVALIDATE_REMOVE &&
                    ev.getIdentifier().getMemberName().equals("dependencies") &&
                    ev.getOf() == CollectionDomainEvent.Of.REMOVE_FROM ) {
                    ev.veto("ToDoItemSubscriptions says: can't remove this object from dependencies collection!");
                }
                break;
            case EXECUTING:
                break;
            case EXECUTED:
                if(ev.getOf() == CollectionDomainEvent.Of.ADD_TO) {
                    LOG.info("Received CollectionDomainEvent, " + ev.getSource().toString() + ", added to " + ev.getIdentifier().getMemberName() + " : " + ev.getValue());
                } else {
                    LOG.info("Received CollectionDomainEvent, " + ev.getSource().toString() + ", removed from " + ev.getIdentifier().getMemberName() + " : " + ev.getValue());
                }
                onExecutedThrowExceptionIfSet(ev);
                break;
        }

    }

    //endregion

    //region > receivedEvents
    private final List<java.util.EventObject> receivedEvents = Lists.newLinkedList();

    /**
     * Used in integration tests.
     */
    @Programmatic
    public List<java.util.EventObject> receivedEvents() {
        return receivedEvents;
    }

    /**
     * Used in integration tests.
     */
    @Programmatic
    public <T extends java.util.EventObject> List<T> receivedEvents(final Class<T> expectedType) {
        return newArrayList(
                    transform(
                        filter(receivedEvents, instanceOf(expectedType)),
                        castTo(expectedType)));
    }

    private static <T extends EventObject> Function<EventObject, T> castTo(Class<T> expectedType) {
        return new Function<EventObject, T>() {
                    @Override
                    public T apply(EventObject input) {
                        return (T) input;
                    }
                };
    }

    private static <T extends EventObject> Predicate<EventObject> instanceOf(final Class<T> expectedType) {
        return new Predicate<EventObject>() {
            @Override
            public boolean apply(EventObject input) {
                return expectedType.isInstance(input);
            }
        };
    }


    void logEvent(final ToDoAppDomainModule.ActionDomainEvent<?> ev) {
        switch(ev.getEventPhase()) {
        case HIDE:
            break;
        case DISABLE:
            break;
        case VALIDATE:
            break;
        case EXECUTING:
            break;
        case EXECUTED:
            LOG.info("Received {} for {}", ev.getClass().getName(), ev.getSource().toString());
            break;
        }
    }

    void logEvent(final ToDoAppDomainModule.PropertyDomainEvent<?,?> ev) {
        switch(ev.getEventPhase()) {
        case HIDE:
            break;
        case DISABLE:
            break;
        case VALIDATE:
            break;
        case EXECUTING:
            break;
        case EXECUTED:
            LOG.info("Received {} for {}", ev.getClass().getName(), ev.getSource().toString());
            break;
        }
    }

    /**
     * Used in integration tests.
     */
    @Programmatic
    public <T extends java.util.EventObject> T mostRecentlyReceivedEvent(Class<T> expectedType) {
        final List<T> receivedEvents = receivedEvents(expectedType);
        return !receivedEvents.isEmpty() ? receivedEvents.get(0) : null;
    }
    private void recordEvent(final java.util.EventObject ev) {
        receivedEvents.add(0, ev);
    }
    /**
     * Used in integration tests.
     */
    @Programmatic
    public void reset() {
        receivedEvents.clear();
        subscriberBehaviour(DemoBehaviour.ANY_EXECUTE_ACCEPT);
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private DomainObjectContainer container;

    @javax.inject.Inject
    private EventBusService eventBusService;

    @javax.inject.Inject
    private ToDoAppSettingsService applicationSettingsService;
    //endregion

}
