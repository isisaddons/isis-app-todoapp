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

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.wrapper.InvalidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import todoapp.dom.categories.Categorized_updateCategory;
import todoapp.dom.categories.Category;
import todoapp.dom.categories.Subcategory;
import todoapp.dom.todoitem.ToDoItem;
import todoapp.fixture.scenarios.RecreateToDoItemsForCurrentUser;

public abstract class Categorized_updateCategory_IntegTest extends AbstractToDoIntegTest {

    RecreateToDoItemsForCurrentUser fixtureScript;

    @Before
    public void setUpData() throws Exception {
        fixtureScript = new RecreateToDoItemsForCurrentUser();
        fixtureScripts.runFixtureScript(fixtureScript, null);
    }

    ToDoItem toDoItem;

    @Before
    public void setUp() throws Exception {

        toDoItem = fixtureScript.getToDoItems().get(0);
        assertThat(toDoItem).isNotNull();
    }

    Categorized_updateCategory mixin() {
        return mixin(Categorized_updateCategory.class, toDoItem);
    }

    public static class Actions {
        public static class UpdateCategory extends Categorized_updateCategory_IntegTest {

            @Test
            public void happyCase() throws Exception {

                // when
                wrap(mixin()).$$(Category.PROFESSIONAL, Subcategory.CONSULTING);

                // then
                assertThat(toDoItem.getCategory()).isEqualTo(Category.PROFESSIONAL);
                assertThat(toDoItem.getSubcategory()).isEqualTo(Subcategory.CONSULTING);

                // when
                wrap(mixin()).$$(Category.DOMESTIC, Subcategory.CHORES);

                // then
                assertThat(toDoItem.getCategory()).isEqualTo(Category.DOMESTIC);
                assertThat(toDoItem.getSubcategory()).isEqualTo(Subcategory.CHORES);
            }


            @Test
            public void categoryCannotBeNull() throws Exception {
                // expect
                expectedExceptions.expect(InvalidException.class);
                expectedExceptions.expectMessage("'Category' is mandatory");
                // when
                wrap(mixin()).$$(null, Subcategory.CHORES);
            }

            @Test
            public void subcategoryCanBeNull() throws Exception {
                // when, then
                wrap(mixin()).$$(Category.PROFESSIONAL, null);
            }

            @Test
            public void subcategoryMustBelongToCategory() throws Exception {
                // expect
                expectedExceptions.expectMessage(containsString("Invalid subcategory"));
                // when
                wrap(mixin()).$$(Category.PROFESSIONAL, Subcategory.CHORES);
            }
        }
    }
}