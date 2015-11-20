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
package todoapp.app.services.restful;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.viewer.restfulobjects.applib.RepresentationType;
import org.apache.isis.viewer.restfulobjects.rendering.service.conmap.ContentMappingService;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import todoapp.dom.similarto.SimilarToContributions;
import todoapp.dom.todoitem.ToDoItem;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ContentMappingServiceForToDoItem implements ContentMappingService {

    private MapperFactory mapperFactory;

    @Programmatic
    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.registerClassMap(
                mapperFactory.classMap(ToDoItem.class, todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto.class)
                        .byDefault()
                        .toClassMap());
        mapperFactory.registerClassMap(
                mapperFactory.classMap(ToDoItem.class, todoapp.app.viewmodels.todoitem.v1_1.ToDoItemDto.class)
                        .byDefault()
                        .toClassMap());
    }

    @Programmatic
    @Override
    public Object map(
            final Object object,
            final List<MediaType> acceptableMediaTypes,
            final RepresentationType representationType) {

        if(object instanceof ToDoItem) {
            for (MediaType acceptableMediaType : acceptableMediaTypes) {
                final Map<String, String> parameters = acceptableMediaType.getParameters();
                final String className = parameters.get("x-ro-domain-type");
                if(todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto.class.getName().equals(className)) {
                    return toDtoV1((ToDoItem) object);
                }
                if(todoapp.app.viewmodels.todoitem.v1_1.ToDoItemDto.class.getName().equals(className)) {
                    return toDtoV2((ToDoItem) object);
                }
            }
        }

        return null;
    }

    @Programmatic
    public todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto toDtoV1(final ToDoItem toDoItem) {

        final todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto dto =
                mapperFactory.getMapperFacade().map(toDoItem, todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto.class);

        return dto;
    }

    @Programmatic
    public todoapp.app.viewmodels.todoitem.v1_1.ToDoItemDto toDtoV2(final ToDoItem toDoItem) {

        final todoapp.app.viewmodels.todoitem.v1_1.ToDoItemDto dto =
                mapperFactory.getMapperFacade().map(toDoItem, todoapp.app.viewmodels.todoitem.v1_1.ToDoItemDto.class);

        dto.setToDoItem(toDoItem);
        final List<ToDoItem> toDoItems = similarToContributions.similarTo(toDoItem);
        dto.setSimilarItems(toDoItems);

        return dto;
    }


    @Inject
    SimilarToContributions similarToContributions;


}
