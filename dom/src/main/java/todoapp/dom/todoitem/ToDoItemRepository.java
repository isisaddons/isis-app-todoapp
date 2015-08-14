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
package todoapp.dom.todoitem;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import todoapp.dom.categories.Category;
import todoapp.dom.ToDoAppFeature;

/**
 * Delegates to underlying implementation and caches result.
 *
 * <p>
 *     Uses feature toggles to determine which to delegate to.
 * </p>
 */
@DomainService(repositoryFor = ToDoItem.class)
public class ToDoItemRepository {

    ToDoItemRepositoryImpl getToDoItemRepositoryImpl() {
        final ToDoItemRepositoryImpl toDoItemRepository =
                ToDoAppFeature.useTypeSafeQueries.isActive()
                        ? toDoItemRepositoryImplUsingTypesafeQueries
                        : toDoItemRepositoryImplUsingJdoql;
        return toDoItemRepository;
    }

    @Programmatic
    public  List<ToDoItem> findByAtPathAndCategory(final String atPath, final Category category) {
        return queryResultsCache.execute(
                () -> getToDoItemRepositoryImpl().findByAtPathAndCategory(atPath, category),
                ToDoItemRepository.class,
                "findByAtPathAndCategory",
                atPath, category);
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndComplete(
            final String atPath, final boolean complete) {
        return queryResultsCache.execute(
                () -> getToDoItemRepositoryImpl().findByAtPathAndComplete(atPath, complete),
                ToDoItemRepository.class,
                "findByAtPathAndComplete",
                atPath, complete);
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndDescriptionContains(
            final String atPath, final String description) {
        return queryResultsCache.execute(
                () -> getToDoItemRepositoryImpl().findByAtPathAndDescriptionContains(atPath, description),
                ToDoItemRepository.class,
                "findByAtPathAndDescriptionContains",
                atPath, description);
    }

    //region > injected services
    @javax.inject.Inject
    private ToDoItemRepositoryImplUsingJdoql toDoItemRepositoryImplUsingJdoql;
    @javax.inject.Inject
    private ToDoItemRepositoryImplUsingTypesafeQueries toDoItemRepositoryImplUsingTypesafeQueries;

    @javax.inject.Inject
    protected QueryResultsCache queryResultsCache;
    //endregion

}
