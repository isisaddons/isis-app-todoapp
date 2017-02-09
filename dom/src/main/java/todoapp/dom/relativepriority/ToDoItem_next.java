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
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_next {

    private final ToDoItem toDoItem;
    public ToDoItem_next(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            describedAs = "The next item not yet completed",
            contributed = Contributed.AS_ACTION
    )
    public ToDoItem $$() {
        final Integer priority = relativePriorityService.relativePriority(toDoItem);
        if(priority == null) {
            return toDoItem;
        }
        int priorityOfNext = priority != null ? priority + 1 : 0;
        return relativePriorityService.itemWithPriorityElse(priorityOfNext, toDoItem);
    }
    public String disable$$() {
        if (toDoItem.isComplete()) {
            return "Completed";
        }
        if($$() == null) {
            return "No next item";
        }
        return null;
    }


    @Inject
    private RelativePriorityService relativePriorityService;

}
