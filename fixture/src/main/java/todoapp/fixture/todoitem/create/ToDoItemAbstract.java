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
package todoapp.fixture.todoitem.create;

import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.categories.Category;
import todoapp.dom.module.categories.Subcategory;
import todoapp.dom.module.todoitem.ToDoItems;

import java.math.BigDecimal;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

public abstract class ToDoItemAbstract extends FixtureScript {

    protected ToDoItem createToDoItem(
            final String description,
            final Category category, final Subcategory subcategory,
            final LocalDate dueBy,
            final BigDecimal cost,
            final ExecutionContext executionContext) {

        // validate parameters
        final String ownedBy = executionContext.getParameter("ownedBy");
        if(ownedBy == null) {
            throw new IllegalArgumentException("'ownedBy' must be specified");
        }

        // execute
        ToDoItem newToDo = toDoItems.newToDo(
                description, category, subcategory, ownedBy, dueBy, cost);

        newToDo.setLocation(
                new Location(51.5172 + random(-0.05, +0.05), 0.1182 + random(-0.05, +0.05)));

        return executionContext.addResult(this, newToDo);
    }

    private static double random(double from, double to) {
        return Math.random() * (to-from) + from;
    }

    protected LocalDate nowPlusDays(int days) {
        return clockService.now().plusDays(days);
    }

    protected BigDecimal BD(String str) {
        return new BigDecimal(str);
    }

    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    protected ClockService clockService;
    //endregion


}