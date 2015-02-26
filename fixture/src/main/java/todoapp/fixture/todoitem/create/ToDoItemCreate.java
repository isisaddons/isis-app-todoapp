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
import todoapp.fixture.util.Util;

import java.math.BigDecimal;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.joda.time.LocalDate;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.clock.ClockService;

public class ToDoItemCreate extends FixtureScript {

    public static final String DESCRIPTION_BUY_STAMPS = "Buy stamps";
    public static final String DESCRIPTION_WRITE_BLOG_POST = "Write blog post";


    private Object[][] canned() {
        return new Object[][]{
                {"Buy bread", Category.DOMESTIC, Subcategory.SHOPPING, nowPlusDays(0), BD("1.75")},
                {"Buy milk", Category.DOMESTIC, Subcategory.SHOPPING, nowPlusDays(0), BD("0.75")},
                {DESCRIPTION_BUY_STAMPS, Category.DOMESTIC, Subcategory.SHOPPING, nowPlusDays(0), BD("10.00")},
                {"Mow lawn", Category.DOMESTIC, Subcategory.GARDEN, nowPlusDays(6), null},
                {"Organize brown bag", Category.PROFESSIONAL, Subcategory.CONSULTING, nowPlusDays(14), null},
                {"Pick up laundry", Category.DOMESTIC, Subcategory.CHORES, nowPlusDays(6), BD("7.50")},
                {"Sharpen knives", Category.DOMESTIC, Subcategory.CHORES, nowPlusDays(14), null},
                {"Stage Isis release", Category.PROFESSIONAL, Subcategory.OPEN_SOURCE, null, null},
                {"Submit conference session", Category.PROFESSIONAL, Subcategory.EDUCATION, nowPlusDays(21), null},
                {"Vacuum house", Category.DOMESTIC, Subcategory.HOUSEWORK, nowPlusDays(3), null},
                {DESCRIPTION_WRITE_BLOG_POST, Category.PROFESSIONAL, Subcategory.MARKETING, nowPlusDays(7), null},
                {"Write to penpal", Category.OTHER, Subcategory.OTHER, null, null},
        };
    }

    public static int numberCanned() {
        return 12; // keep in step with canned() !!
    }

    //region > index (required)
    private int index;

    /**
     * Which precanned object to create (index=0 to 11); required.
     */
    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }
    //endregion

    //region > username (optional)
    private String username;

    /**
     * User to create items for; optional, defaults to current user.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    //endregion

    //region > todoItem (output)
    private ToDoItem toDoItem;

    /**
     * The todoitem created by the script (output).
     */
    public ToDoItem getToDoItem() {
        return toDoItem;
    }

    public void setToDoItem(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }
    //endregion


    @Override
    protected void execute(final ExecutionContext ec) {

        // required
        Integer index = Util.coalesce(ec.getParameterAsInteger("index"), getIndex());
        if(index == null) {
            throw new IllegalArgumentException("Index of object to create is required");
        }

        final int numCanned = canned().length;
        if(index < 0 || index >= numCanned) {
            throw new IllegalArgumentException(String.format("Index of object to create must be in range [0, %d)", numCanned));
        }

        // defaults
        final String username = Util.coalesce(ec.getParameter("username"), getUsername(), getContainer().getUser().getName());


        // execute
        final Object[] objects = canned()[((int) index)];
        final String description = (String) objects[0];
        final Category category = (Category) objects[1];
        final Subcategory subcategory = (Subcategory) objects[2];
        final LocalDate dueBy = (LocalDate) objects[3];
        final BigDecimal cost = (BigDecimal) objects[4];

        // validate parameters
        // execute


        this.toDoItem = toDoItems.newToDo(
                description, category, subcategory, username, dueBy, cost);

        toDoItem.setLocation(new Location(51.5172 + random(-0.05, +0.05), 0.1182 + random(-0.05, +0.05)));

        ec.addResult(this, toDoItem);
    }

    private static double random(final double from, final double to) {
        return Math.random() * (to-from) + from;
    }

    protected LocalDate nowPlusDays(final int days) {
        return clockService.now().plusDays(days);
    }

    protected BigDecimal BD(final String str) {
        return new BigDecimal(str);
    }


    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    protected ClockService clockService;


    //endregion


}