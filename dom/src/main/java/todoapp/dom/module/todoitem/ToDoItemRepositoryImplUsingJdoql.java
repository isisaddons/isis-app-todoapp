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
package todoapp.dom.module.todoitem;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;

import todoapp.dom.module.categories.Category;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ToDoItemRepositoryImplUsingJdoql implements ToDoItemRepositoryImpl {

    @Programmatic
    public List<ToDoItem> findByAtPathAndCategory(final String atPath, final Category category) {
        return container.allMatches(
                new QueryDefault<>(ToDoItem.class,
                        "findByAtPathAndCategory",
                        "atPath", atPath,
                        "category", category));
    }


    @Override
    public List<ToDoItem> findByAtPathAndComplete(
            final String atPath,
            final boolean complete) {
        return container.allMatches(
                new QueryDefault<>(ToDoItem.class,
                        "findByAtPathAndComplete",
                        "atPath", atPath,
                        "complete", complete));
    }

    @Override
    public List<ToDoItem> findByAtPathAndDescriptionContains(
            final String atPath,
            final String description) {
        return container.allMatches(
                new QueryDefault<>(ToDoItem.class,
                        "findByAtPathAndDescriptionContains",
                        "atPath", atPath,
                        "description", description));
    }

    //region > injected services
    @javax.inject.Inject
    private DomainObjectContainer container;
    //endregion

}
