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
package todoapp.app.services.titlesubscriber;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractSubscriber;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.eventbus.IconUiEvent;
import org.apache.isis.applib.services.eventbus.TitleUiEvent;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class UiEventSubscriber extends AbstractSubscriber {

    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final TitleUiEvent<?> event) {
        final Object eventSource = event.getSource();
        if(eventSource instanceof todoapp.app.viewmodels.todoitem.v2.ToDoItemDto) {
            todoapp.app.viewmodels.todoitem.v2.ToDoItemDto dto = (todoapp.app.viewmodels.todoitem.v2.ToDoItemDto) eventSource;
            final String underlyingTitle = container.titleOf(dto.getToDoItem());
            event.setTitle("DTO v2 for: " + underlyingTitle);
        }
        if(eventSource instanceof todoapp.app.viewmodels.todoitem.v1.ToDoItemDto) {
            todoapp.app.viewmodels.todoitem.v1.ToDoItemDto dto = (todoapp.app.viewmodels.todoitem.v1.ToDoItemDto) eventSource;
            event.setTitle("DTO v1 for: " + dto.getDescription());
        }
    }

    @com.google.common.eventbus.Subscribe
    @org.axonframework.eventhandling.annotation.EventHandler
    public void on(final IconUiEvent<?> event) {
        final Object eventSource = event.getSource();
        if(eventSource instanceof todoapp.app.viewmodels.todoitem.v2.ToDoItemDto) {
            todoapp.app.viewmodels.todoitem.v2.ToDoItemDto dto = (todoapp.app.viewmodels.todoitem.v2.ToDoItemDto) eventSource;
            final String underlyingIconName = container.iconNameOf(dto.getToDoItem());
            event.setIconName(underlyingIconName);
        }
    }

    @Inject
    DomainObjectContainer container;

}
