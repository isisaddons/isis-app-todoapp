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
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

import todoapp.dom.categories.Category;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ToDoItemRepositoryImplUsingTypesafeQueries implements ToDoItemRepositoryImpl {

    @Programmatic
    public List<ToDoItem> findByAtPathAndCategory(final String atPath, final Category category) {
        final QToDoItem q = QToDoItem.candidate();
        return isisJdoSupport.executeQuery(ToDoItem.class,
                q.atPath.eq(atPath).and(
                q.category.eq(category)));
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndComplete(
            final String atPath,
            final boolean complete) {
        final QToDoItem q = QToDoItem.candidate();
        return isisJdoSupport.executeQuery(ToDoItem.class,
                q.atPath.eq(atPath).and(
                q.complete.eq(complete)));
    }

    @Programmatic
    public List<ToDoItem> findByAtPathAndDescriptionContains(
            final String atPath,
            final String description) {
        final QToDoItem q = QToDoItem.candidate();
        return isisJdoSupport.executeQuery(ToDoItem.class,
                q.atPath.eq(atPath).and(
                q.description.indexOf(description).gt(0)));
    }


    //region > injected services
    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;
    //endregion

}
