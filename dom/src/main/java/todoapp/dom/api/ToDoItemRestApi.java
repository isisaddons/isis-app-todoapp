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
package todoapp.dom.api;

import javax.annotation.PostConstruct;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.metamodel.MetaModelService;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dto.module.todoitem.OidDto;
import todoapp.dto.module.todoitem.ToDoItemDto;

@DomainService(
        nature = NatureOfService.VIEW_REST_ONLY
)
public class ToDoItemRestApi {

    private MapperFactory mapperFactory;

    @PostConstruct
    public void init() {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.registerClassMap(
                mapperFactory.classMap(ToDoItem.class, ToDoItemDto.class)
                        .byDefault() // all fields are the compatible
                        .toClassMap());
        mapperFactory.registerClassMap(
                mapperFactory.classMap(Bookmark.class, OidDto.class)
                        .field("identifier", "objectIdentifier") // customized
                        .byDefault() // all other fields are compatible
                        .toClassMap());

    }

    @Action(semantics = SemanticsOf.SAFE)
    public ToDoItemDto findItem(final String identifier) {

        final String objectType = metaModelService.toObjectType(ToDoItem.class);
        final Bookmark bookmark = new Bookmark(objectType, identifier);

        final ToDoItem toDoItem = (ToDoItem) bookmarkService.lookup(bookmark);
        if(toDoItem == null) {
            return null;
        }

        final ToDoItemDto dto = mapperFactory.getMapperFacade().map(toDoItem, ToDoItemDto.class);
        final OidDto oidDto = mapperFactory.getMapperFacade().map(bookmark, OidDto.class);

        // manually wire togethr
        dto.setOid(oidDto);

        return dto;
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private MetaModelService metaModelService;
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    @javax.inject.Inject
    private DomainObjectContainer container;
    //endregion

}
