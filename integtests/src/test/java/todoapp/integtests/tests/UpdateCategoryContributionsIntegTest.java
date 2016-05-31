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

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import todoapp.dom.categories.Category;
import todoapp.dom.categories.Subcategory;
import todoapp.dom.categories.UpdateCategoryContributions;
import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;
import todoapp.fixture.scenarios.RecreateToDoItemsForCurrentUser;

public abstract class UpdateCategoryContributionsIntegTest extends AbstractToDoIntegTest {

    RecreateToDoItemsForCurrentUser fixtureScript;

    @Before
    public void setUpData() throws Exception {
        fixtureScript = new RecreateToDoItemsForCurrentUser();
        fixtureScripts.runFixtureScript(fixtureScript, null);
    }
    @Inject
    ToDoItems toDoItems;
    @Inject
    UpdateCategoryContributions updateCategoryContributions;

    ToDoItem toDoItem;

    @Before
    public void setUp() throws Exception {

        toDoItem = wrap(fixtureScript.getToDoItems().get(0));
        assertThat(toDoItem).isNotNull();
    }

    public static class Actions {
        public static class UpdateCategory extends UpdateCategoryContributionsIntegTest {

            @Test
            public void happyCase() throws Exception {

                // when
                wrap(updateCategoryContributions).updateCategory(toDoItem, Category.PROFESSIONAL, Subcategory.CONSULTING);

                // then
                assertThat(toDoItem.getCategory()).isEqualTo(Category.PROFESSIONAL);
                assertThat(toDoItem.getSubcategory()).isEqualTo(Subcategory.CONSULTING);

                // when
                wrap(updateCategoryContributions).updateCategory(toDoItem, Category.DOMESTIC, Subcategory.CHORES);

                // then
                assertThat(toDoItem.getCategory()).isEqualTo(Category.DOMESTIC);
                assertThat(toDoItem.getSubcategory()).isEqualTo(Subcategory.CHORES);
            }


            @Test
            public void categoryCannotBeNull() throws Exception {

                // when, then
                expectedExceptions.expect(InvalidException.class);
                expectedExceptions.expectMessage("'Category' is mandatory");
                wrap(updateCategoryContributions).updateCategory(toDoItem, null, Subcategory.CHORES);
            }

            @Test
            public void subcategoryCanBeNull() throws Exception {

                // when, then
                wrap(updateCategoryContributions).updateCategory(toDoItem, Category.PROFESSIONAL, null);
            }

            @Test
            public void subcategoryMustBelongToCategory() throws Exception {

                // when, then
                expectedExceptions.expectMessage(containsString("Invalid subcategory"));
                wrap(updateCategoryContributions).updateCategory(toDoItem, Category.PROFESSIONAL, Subcategory.CHORES);
            }
        }

    }

}