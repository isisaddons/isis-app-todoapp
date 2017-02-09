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

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;

import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_similarTo extends AbstractFactoryAndRepository {

    private final ToDoItem toDoItem;

    public ToDoItem_similarTo(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @ActionLayout(
            contributed = Contributed.AS_ASSOCIATION
    )
    @Action(semantics = SemanticsOf.SAFE)
    public List<ToDoItem> $$() {
        return similarToService.similarTo(toDoItem);
    }



    @javax.inject.Inject
    private SimilarToService similarToService;
}
