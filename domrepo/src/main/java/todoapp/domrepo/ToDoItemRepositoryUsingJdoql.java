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
package todoapp.domrepo;

import java.util.List;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.query.QueryDefault;

import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItemRepository;

//@DomainService(repositoryFor = ToDoItem.class)
public class ToDoItemRepositoryUsingJdoql extends ToDoItemRepository {

    @Override
    protected List<ToDoItem> doFindByAtPathAndComplete(final String atPath, final boolean complete) {
        return container.allMatches(
                new QueryDefault<>(ToDoItem.class,
                        "findByAtPathAndComplete",
                        "atPath", atPath,
                        "complete", complete));
    }


    @Override
    protected List<ToDoItem> doFindByAtPathAndDescriptionContains(final String atPath, final String description) {
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
