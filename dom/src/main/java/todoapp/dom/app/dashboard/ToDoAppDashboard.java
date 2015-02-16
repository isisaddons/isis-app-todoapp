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
package todoapp.dom.app.dashboard;

import todoapp.dom.app.relativepriority.RelativePriorityContributions;
import todoapp.dom.module.export.ToDoItemsExportService;
import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItems;

import java.util.Collections;
import java.util.List;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.Collection;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Nature;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

@DomainObject(nature = Nature.VIEW_MODEL)
public class ToDoAppDashboard {

    //region > identification in the UI
    public String title() {
        return "Dashboard";
    }
    //endregion

    //region > notYetComplete (collection)

    @Collection(
            editing = Editing.DISABLED
    )
    @CollectionLayout(
            sortedBy = RelativePriorityContributions.Comparator.class
    )
    public List<ToDoItem> getNotYetComplete() {
        return toDoItems.notYetCompleteNoUi();
    }
    //endregion

    //region > completed (collection)
    @Collection(
            editing = Editing.DISABLED
    )
    public List<ToDoItem> getComplete() {
        return toDoItems.complete();
    }
    //endregion

    //region > exportToWordDoc
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Blob exportToWordDoc() {

        final List items = getNotYetComplete();
        Collections.sort(items, relativePriorityContributions.comparator());
        return toDoItemsExportService.exportToWordDoc(items);
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private ToDoItemsExportService toDoItemsExportService;

    @javax.inject.Inject
    private RelativePriorityContributions relativePriorityContributions;

    //endregion

}
