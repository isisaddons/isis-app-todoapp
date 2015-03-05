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
package todoapp.dom.app.relativepriority;

import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItems;

import java.util.List;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class RelativePriorityContributions extends AbstractFactoryAndRepository {

    //region > relativePriority (contributed property)
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
    public Integer relativePriority(final ToDoItem toDoItem) {
        return queryResultsCache.execute(new Callable<Integer>(){
            @Override
            public Integer call() throws Exception {
                if(toDoItem.isComplete()) {
                    return null;
                }

                // sort items, then locate this one
                int i=1;
                for (final ToDoItem each : sortedNotYetComplete()) {
                    if(each == toDoItem) {
                        return i;
                    }
                    i++;
                }
                return null;
            }}, RelativePriorityContributions.class, "relativePriority", toDoItem);
    }

    private List<ToDoItem> sortedNotYetComplete() {
        return ORDERING_DUE_BY
        .compound(ORDERING_DESCRIPTION)
        .sortedCopy(toDoItems.notYetCompleteNoUi());
    }

    private static final Ordering<ToDoItem> ORDERING_DUE_BY =
        Ordering.natural().nullsLast().onResultOf(new Function<ToDoItem, LocalDate>(){
            @Override
            public LocalDate apply(final ToDoItem input) {
                return input.getDueBy();
            }
        });
    
    private static final Ordering<ToDoItem> ORDERING_DESCRIPTION =
        Ordering.natural().nullsLast().onResultOf(new Function<ToDoItem, String>(){
            @Override
            public String apply(final ToDoItem input) {
                return input.getDescription();
            }
        });


    //endregion

    //region >  next, previous (contributed actions)
    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(
            describedAs = "The next item not yet completed",
            contributed = Contributed.AS_ACTION
    )
    public ToDoItem next(final ToDoItem item) {
        final Integer priority = relativePriority(item);
        if(priority == null) {
            return item;
        }
        int priorityOfNext = priority != null ? priority + 1 : 0;
        return itemWithPriorityElse(priorityOfNext, item);
    }
    public String disableNext(final ToDoItem toDoItem) {
        if (toDoItem.isComplete()) {
            return "Completed";
        }
        if(next(toDoItem) == null) {
            return "No next item";
        }
        return null;
    }

    // //////////////////////////////////////

    @ActionLayout(
            describedAs = "The previous item not yet completed",
            contributed = Contributed.AS_ACTION
    )
    @Action(semantics = SemanticsOf.SAFE)
    public ToDoItem previous(final ToDoItem item) {
        final Integer priority = relativePriority(item);
        if(priority == null) {
            return item;
        }
        int priorityOfPrevious = priority != null? priority - 1 : 0;
        return itemWithPriorityElse(priorityOfPrevious, item);
    }
    public String disablePrevious(final ToDoItem toDoItem) {
        if (toDoItem.isComplete()) {
            return "Completed";
        }
        if(previous(toDoItem) == null) {
            return "No previous item";
        }
        return null;
    }

    // //////////////////////////////////////

    /**
     * @param priority : 1-based priority
     */
    private ToDoItem itemWithPriorityElse(int priority, final ToDoItem itemElse) {
        if(priority < 1) {
            return null;
        }
        final List<ToDoItem> items = sortedNotYetComplete();
        if(priority > items.size()) {
            return null;
        }
        return priority>=0 && items.size()>=priority? items.get(priority-1): itemElse;
    }

    //endregion


    //region > comparator (programmatic)

    @Programmatic
    public java.util.Comparator<ToDoItem> comparator() {
        final java.util.Comparator<ToDoItem> comparator = new Comparator();
        getContainer().injectServicesInto(comparator);
        return comparator;
    }

    public static class Comparator implements java.util.Comparator<ToDoItem> {

        @Override
        public int compare(final ToDoItem o1, final ToDoItem o2) {
            final Integer p1 = relativePriorityContributions.relativePriority(o1);
            final Integer p2 = relativePriorityContributions.relativePriority(o2);
            if(p1 == null && p2 != null) { return -1; }
            if(p1 != null && p2 == null) { return +1; }
            if(p1 == null && p2 == null) { return 0; }
            return p1.intValue() - p2.intValue();
        }

        @Inject
        private RelativePriorityContributions relativePriorityContributions;

    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private QueryResultsCache queryResultsCache;

    //endregion

}
