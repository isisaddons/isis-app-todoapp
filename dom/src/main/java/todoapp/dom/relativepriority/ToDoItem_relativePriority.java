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
package todoapp.dom.relativepriority;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;

@Mixin
public class ToDoItem_relativePriority {

    private final ToDoItem toDoItem;
    public ToDoItem_relativePriority(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            hidden = Where.ALL_TABLES
    )
    @ActionLayout(
            describedAs = "The relative priority of this item compared to others not yet complete (using 'due by' date)",
            contributed = Contributed.AS_ASSOCIATION
    )
    @Property(
            editing = Editing.DISABLED,
            editingDisabledReason = "Relative priority, derived from due date"
    )
    public Integer $$() {
        return queryResultsCache.execute(() -> {
            if(toDoItem.isComplete()) {
                return null;
            }

            // sort items, then locate this one
            int i=1;
            for (final ToDoItem each : relativePriorityService.sortedNotYetComplete()) {
                if(each == toDoItem) {
                    return i;
                }
                i++;
            }
            return null;
        }, ToDoItem_relativePriority.class, "relativePriority", toDoItem);
    }


    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private QueryResultsCache queryResultsCache;

    @Inject
    private RelativePriorityService relativePriorityService;

}
