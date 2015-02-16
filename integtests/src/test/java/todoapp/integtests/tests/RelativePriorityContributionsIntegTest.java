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
package todoapp.integtests.tests;

import todoapp.dom.app.relativepriority.RelativePriorityContributions;
import todoapp.dom.module.categories.UpdateCategoryContributions;
import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItems;
import todoapp.fixture.scenarios.RecreateToDoItemsForCurrentUser;

import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public abstract class RelativePriorityContributionsIntegTest extends AbstractToDoIntegTest {

    RecreateToDoItemsForCurrentUser fixtureScript;

    @Before
    public void setUpData() throws Exception {
        fixtureScript = new RecreateToDoItemsForCurrentUser();
        fixtureScripts.runFixtureScript(fixtureScript, null);
    }

    @Inject
    FixtureScripts fixtureScripts;
    @Inject
    ToDoItems toDoItems;
    @Inject
    UpdateCategoryContributions updateCategoryContributions;

    UpdateCategoryContributions updateCategoryContributionsWrapped;

    @Inject
    RelativePriorityContributions relativePriorityContributions;

    RelativePriorityContributions relativePriorityContributionsWrapped;

    ToDoItem toDoItem;

    @Before
    public void setUp() throws Exception {

        toDoItem = wrap(fixtureScript.lookup("to-do-items-recreate-and-complete-several/to-do-item-complete-for-buy-stamps/item-1", ToDoItem.class));
        assertThat(toDoItem, is(not(nullValue())));

        updateCategoryContributionsWrapped = wrap(updateCategoryContributions);

        relativePriorityContributionsWrapped = wrap(relativePriorityContributions);
    }

    public static class Properties {
        public static class Priority extends RelativePriorityContributionsIntegTest {

            private List<ToDoItem> notYetComplete;

            @Before
            public void setUp() throws Exception {
                notYetComplete = wrap(toDoItems).notYetComplete();
            }

            @Test
            public void happyCase() throws Exception {
                assertPriority(0, 1);
                assertPriority(1, 2);
                assertPriority(2, 4);
                assertPriority(3, 6);
                assertPriority(4, 5);
                assertPriority(5, 7);
                assertPriority(6, 9);
                assertPriority(7, 8);
                assertPriority(8, 3);
                assertPriority(9, 10);
            }

            private void assertPriority(final int n, final int priority) {
                assertThat(relativePriorityContributions.relativePriority(notYetComplete.get(n)), is(Integer.valueOf(priority)));
            }
        }
    }

}