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

import com.google.common.collect.Lists;

import org.datanucleus.api.jdo.JDOPersistenceManager;
import org.datanucleus.query.typesafe.BooleanExpression;
import org.datanucleus.query.typesafe.TypesafeQuery;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import todoapp.dom.module.categories.Category;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ToDoItemRepositoryImplUsingTypesafeQueries implements ToDoItemRepositoryImpl {

    @Programmatic
    public List<ToDoItem> findByAtPathAndCategory(final String atPath, final Category category) {
        final QToDoItem qToDoItem = QToDoItem.candidate();
        return executeQuery(ToDoItem.class,
                qToDoItem.atPath.eq(atPath).and(
                qToDoItem.category.eq(category)));
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndComplete(
            final String atPath,
            final boolean complete) {
        final QToDoItem qToDoItem = QToDoItem.candidate();
        return executeQuery(ToDoItem.class,
                qToDoItem.atPath.eq(atPath).and(
                qToDoItem.complete.eq(complete)));
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndDescriptionContains(
            final String atPath,
            final String description) {
        final QToDoItem qToDoItem = QToDoItem.candidate();
        return executeQuery(ToDoItem.class,
                qToDoItem.atPath.eq(atPath).and(
                qToDoItem.description.indexOf(description).gt(0)));
    }


    /**
     * Execute the query, taking a safe copy of the result set of the list, and eagerly closes said query.
     */
    private <T> List<T> executeQuery(final Class<T> cls, final BooleanExpression expression) {
        final TypesafeQuery<T> query = newQuery(cls).filter(expression);
        return executeListAndClose(query);
    }

    private static <T> List<T> executeListAndClose(final TypesafeQuery<T> query) {
        final List<T> elements = query.executeList();
        final List<T> list = Lists.newArrayList(elements);
        query.closeAll();
        return list;
    }

    private <T> TypesafeQuery<T> newQuery(Class<T> cls) {
        return getJdoPersistenceManager().newTypesafeQuery(cls);
    }

    private JDOPersistenceManager getJdoPersistenceManager() {
        return (JDOPersistenceManager) isisJdoSupport.getJdoPersistenceManager();
    }


    //region > injected services
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;
    //endregion

}
