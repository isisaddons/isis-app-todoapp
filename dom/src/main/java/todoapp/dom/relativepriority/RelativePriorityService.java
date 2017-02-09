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

import java.util.List;

import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import org.joda.time.LocalDate;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;
import org.apache.isis.applib.services.registry.ServiceRegistry2;

import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;

@DomainService(nature = NatureOfService.DOMAIN)
public class RelativePriorityService  {

    @Programmatic
    public Integer relativePriority(final ToDoItem toDoItem) {
        return queryResultsCache.execute(() -> {
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
        }, RelativePriorityService.class, "relativePriority", toDoItem);
    }

    ToDoItem itemWithPriorityElse(int priority, final ToDoItem itemElse) {
        if(priority < 1) {
            return null;
        }
        final List<ToDoItem> items = sortedNotYetComplete();
        if(priority > items.size()) {
            return null;
        }
        return priority>=0 && items.size()>=priority? items.get(priority-1): itemElse;
    }

    List<ToDoItem> sortedNotYetComplete() {
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



    //region > comparator (programmatic)

    @Programmatic
    public java.util.Comparator<ToDoItem> comparator() {
        final java.util.Comparator<ToDoItem> comparator = new Comparator();
        serviceRegistry.injectServicesInto(comparator);
        return comparator;
    }

    public static class Comparator implements java.util.Comparator<ToDoItem> {

        @Override
        public int compare(final ToDoItem o1, final ToDoItem o2) {
            final Integer p1 = relativePriorityService.relativePriority(o1);
            final Integer p2 = relativePriorityService.relativePriority(o2);
            if(p1 == null && p2 != null) { return -1; }
            if(p1 != null && p2 == null) { return +1; }
            if(p1 == null && p2 == null) { return 0; }
            return p1.intValue() - p2.intValue();
        }

        @Inject
        private RelativePriorityService relativePriorityService;

    }
    //endregion


    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private QueryResultsCache queryResultsCache;
    @javax.inject.Inject
    private ServiceRegistry2 serviceRegistry;

    //endregion

}
