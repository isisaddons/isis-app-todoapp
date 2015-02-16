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

import todoapp.dom.module.categories.Category;
import todoapp.dom.module.categories.Subcategory;
import todoapp.dom.module.categories.UpdateCategoryContributions;
import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItems;
import todoapp.fixture.scenarios.RecreateToDoItemsForCurrentUser;

import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public abstract class UpdateCategoryContributionsIntegTest extends AbstractToDoIntegTest {

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

    ToDoItem toDoItem;

    @Before
    public void setUp() throws Exception {

        toDoItem = wrap(fixtureScript.getToDoItems().get(0));
        assertThat(toDoItem, is(not(nullValue())));

        updateCategoryContributionsWrapped = wrap(updateCategoryContributions);
    }

    public static class Actions {
        public static class UpdateCategory extends UpdateCategoryContributionsIntegTest {

            @Test
            public void happyCase() throws Exception {

                // when
                updateCategoryContributionsWrapped.updateCategory(toDoItem, Category.Professional, Subcategory.Consulting);

                // then
                assertThat(toDoItem.getCategory(), is(Category.Professional));
                assertThat(toDoItem.getSubcategory(), is(Subcategory.Consulting));

                // when
                updateCategoryContributionsWrapped.updateCategory(toDoItem, Category.Domestic, Subcategory.Chores);

                // then
                assertThat(toDoItem.getCategory(), is(Category.Domestic));
                assertThat(toDoItem.getSubcategory(), is(Subcategory.Chores));
            }


            @Test
            public void categoryCannotBeNull() throws Exception {

                // when, then
                expectedExceptions.expectMessage("'Category' is mandatory");
                updateCategoryContributionsWrapped.updateCategory(toDoItem, null, Subcategory.Chores);
            }

            @Test
            public void subcategoryCanBeNull() throws Exception {

                // when, then
                updateCategoryContributionsWrapped.updateCategory(toDoItem, Category.Professional, null);
            }

            @Test
            public void subcategoryMustBelongToCategory() throws Exception {

                // when, then
                expectedExceptions.expectMessage(containsString("Invalid subcategory"));
                updateCategoryContributionsWrapped.updateCategory(toDoItem, Category.Professional, Subcategory.Chores);
            }
        }

    }

}