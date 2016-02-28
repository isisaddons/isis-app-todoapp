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
package todoapp.app.viewmodels.todoitem;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.viewer.restfulobjects.applib.RepresentationType;
import org.apache.isis.viewer.restfulobjects.rendering.service.conmap.ContentMappingService;

import todoapp.app.viewmodels.todoitem.v1.ToDoItemV1_0;
import todoapp.app.viewmodels.todoitem.v1.ToDoItemV1_1;
import todoapp.dom.similarto.SimilarToContributions;
import todoapp.dom.todoitem.ToDoItem;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ContentMappingServiceForToDoItem implements ContentMappingService {

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
                if( matches(className,
                        ToDoItemV1_0.class,
                        ToDoItemV1_1.class)) {
                    return toViewModelLatest((ToDoItem) object);
                }
            }
        }

        return null;
    }

    private static boolean matches(
            final String className,
            final Class<?>... classes) {
        for (Class<?> aClass : classes) {
            if(aClass.getName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    @Programmatic
    public ToDoItemV1_1 toViewModelLatest(final ToDoItem toDoItem) {

        final ToDoItemV1_1 dto = new ToDoItemV1_1();

        dto.setToDoItem(toDoItem);
        dto.setCategory(nameOf(toDoItem.getCategory()));
        dto.setSubcategory(nameOf(toDoItem.getSubcategory()));


        return dto;
    }

    private static String nameOf(final Enum<?> e) {
        return e != null? e.name(): null;
    }

    @Inject
    SimilarToContributions similarToContributions;


}
