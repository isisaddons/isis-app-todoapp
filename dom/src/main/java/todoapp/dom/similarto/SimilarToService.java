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
package todoapp.dom.similarto;

import java.util.List;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;

import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;

import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItemRepository;
import todoapp.dom.todoitem.ToDoItems;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SimilarToService {

    @Programmatic
    public List<ToDoItem> similarTo(final ToDoItem toDoItem) {
        final List<ToDoItem> similarToDoItems = toDoItemRepository.findByAtPathAndCategory(currentUsersAtPath(),
                toDoItem.getCategory());
        return Lists.newArrayList(Iterables.filter(similarToDoItems, $_ -> $_ != toDoItem));
    }

    //endregion

    //region > helpers
    protected String currentUsersAtPath() {
        final ApplicationUser me = meService.me();
        final ApplicationTenancy tenancy = me.getTenancy();
        if(tenancy == null) {
            throw new IllegalStateException("No application tenancy defined");
        }
        return tenancy.getPath();
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private ToDoItemRepository toDoItemRepository;

    @javax.inject.Inject
    private MeService meService;
    //endregion

}
