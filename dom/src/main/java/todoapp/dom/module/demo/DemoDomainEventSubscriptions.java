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
package todoapp.dom.module.demo;

import todoapp.dom.module.settings.ToDoAppSettingsService;
import todoapp.dom.module.todoitem.ToDoItem;

import java.util.EventObject;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;
import org.isisaddons.module.audit.dom.AuditingServiceMenu;
import org.isisaddons.module.command.dom.CommandServiceMenu;
import org.isisaddons.module.publishing.dom.PublishingServiceMenu;
import org.isisaddons.module.sessionlogger.dom.SessionLoggingServiceMenu;
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

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

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
        menuOrder = "999.2")
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
    @MemberOrder(sequence = "80")
    public void subscriberBehaviour(
            @ParameterLayout(
                    named="Behaviour"
            )
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
        if(getSubscriberBehaviour() == DemoBehaviour.AnyExecuteVetoWithRecoverableException) {
            throw new RecoverableException("Rejecting event (recoverable exception thrown)");
        }
        if(getSubscriberBehaviour() == DemoBehaviour.AnyExecuteVetoWithNonRecoverableException) {
            throw new NonRecoverableException("Rejecting event (non-recoverable exception thrown)");
        }
        if(getSubscriberBehaviour() == DemoBehaviour.AnyExecuteVetoWithOtherException) {
            throw new RuntimeException("Throwing some other exception");
        }
    }
    //endregion

    //region > on(Event) for ToDoItem-specific events
    @Programmatic
    @Subscribe
    public void on(final ToDoItem.CompletedEvent ev) {
        recordEvent(ev);
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
                LOG.info("Received ToDoItem.CompletedEvent for : " + ev.getSource().toString());
                break;
        }
    }
    //endregion

    //region > on(Event) for sessionlogger, auditing, command, publishing events
    @Programmatic
    @Subscribe
    public void on(final SessionLoggingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ActivityMenuHideAll, DemoBehaviour.ActivityMenuHideSessionLogger);
    }

    @Programmatic
    @Subscribe
    public void on(final AuditingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ActivityMenuHideAll, DemoBehaviour.ActivityMenuHideAuditing);
    }

    @Programmatic
    @Subscribe
    public void on(final CommandServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ActivityMenuHideAll, DemoBehaviour.ActivityMenuHideCommand);
    }

    @Programmatic
    @Subscribe
    public void on(final PublishingServiceMenu.ActionDomainEvent ev) {
        vetoIf(ev, DemoBehaviour.ActivityMenuHideAll, DemoBehaviour.ActivityMenuHidePublishing);
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
    @Subscribe
    public void on(final ActionDomainEvent<?> ev) {
        recordEvent(ev);
        switch(ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.UpdateCostActionHide) {
                    if(ev.getIdentifier().getMemberName().equals("updateCost")) {
                        ev.hide();
                    }
                }
                break;
            case DISABLE:
                if(getSubscriberBehaviour() == DemoBehaviour.UpdateCostActionDisable) {
                    if(ev.getIdentifier().getMemberName().equals("updateCost")) {
                        ev.disable("ToDoItemSubscriptions says: updateCost action disabled!");
                    }
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.UpdateCostActionInvalidate &&
                        ev.getIdentifier().getMemberName().equals("updateCost")) {
                    ev.invalidate("ToDoItemSubscriptions says: can't invoke updateCost action with these args!");
                }
                break;
            case EXECUTING:
                break;
            case EXECUTED:
                LOG.info("Received ActionDomainEvent, " + ev.getSource().toString() + ", invoked " + ev.getIdentifier().getMemberName());
                onExecutedThrowExceptionIfSet(ev);
                break;
        }
    }

    @Programmatic
    @Subscribe
    public void on(PropertyDomainEvent<?,?> ev) {
        recordEvent(ev);
        switch(ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.DescriptionPropertyHide &&
                    ev.getIdentifier().getMemberName().equals("description")) {
                    ev.veto("");
                }
                break;
            case DISABLE:
                if(getSubscriberBehaviour() == DemoBehaviour.DescriptionPropertyDisable &&
                    ev.getIdentifier().getMemberName().equals("description")) {
                    ev.veto("ToDoItemSubscriptions says: description property disabled!");
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.DescriptionPropertyInvalidate &&
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
    @Subscribe
    public void on(CollectionDomainEvent<?,?> ev) {
        recordEvent(ev);
        switch (ev.getEventPhase()) {
            case HIDE:
                if(getSubscriberBehaviour() == DemoBehaviour.DependenciesCollectionHide &&
                    ev.getIdentifier().getMemberName().equals("dependencies")) {
                    ev.veto("");
                }
                if (getSubscriberBehaviour() == DemoBehaviour.SimilarToCollectionHide &&
                    ev.getIdentifier().getMemberName().equals("similarTo")) {
                    ev.veto("");
                }
                break;
            case DISABLE:
                if (getSubscriberBehaviour() == DemoBehaviour.DependenciesCollectionDisable &&
                    ev.getIdentifier().getMemberName().equals("dependencies")) {
                    ev.veto("ToDoItemSubscriptions says: dependencies collection disabled!");
                }
                break;
            case VALIDATE:
                if(getSubscriberBehaviour() == DemoBehaviour.DependenciesCollectionInvalidateAdd &&
                    ev.getIdentifier().getMemberName().equals("dependencies") &&
                    ev.getOf() == CollectionDomainEvent.Of.ADD_TO ) {
                    ev.veto("ToDoItemSubscriptions says: can't add this object to dependencies collection!");
                }
                if(getSubscriberBehaviour() == DemoBehaviour.DependenciesCollectionInvalidateRemove &&
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
        subscriberBehaviour(DemoBehaviour.AnyExecuteAccept);
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
