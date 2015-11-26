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
package todoapp.app.services.demoeventsubscriber;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import todoapp.dom.todoitem.ToDoItem;

/**
 * Demonstrates how to subscribe to custom lifecycle classes, eg as fired by ToDoItem.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public class ToDoItemLifecycleLoggingSubscriber extends AbstractSubscriber {

    public static final Logger LOG = LoggerFactory.getLogger(ToDoItemLifecycleLoggingSubscriber.class);

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.CreatedEvent ev) {
        LOG.info("ToDoItem created : " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.LoadedEvent ev) {
        LOG.info("ToDoItem loaded : " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.PersistingEvent ev) {
        LOG.info("ToDoItem persisting : " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.PersistedEvent ev) {
        LOG.info("ToDoItem persisted : " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.UpdatingEvent ev) {
        LOG.info("ToDoItem updating: " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.UpdatedEvent ev) {
        LOG.info("ToDoItem updated: " + ev.toString());
    }

    @Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final ToDoItem.RemovingEvent ev) {
        LOG.info("ToDoItem removing: " + ev.toString());
    }

}
