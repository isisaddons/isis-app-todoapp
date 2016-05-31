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

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import todoapp.dom.categories.Category;
import todoapp.dom.categories.Subcategory;
import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;
import todoapp.integtests.fixture.ToDoItemsIntegTestFixture;

public class ToDoItemsIntegTest extends AbstractToDoIntegTest {

    @Inject
    ToDoItems toDoItems;

    public static class Finders extends ToDoItemsIntegTest {

        ToDoItemsIntegTestFixture fixture;

        @Before
        public void setUpData() throws Exception {
            // executing the fixtures directly allows us to look up the results later.
            fixtureScripts.runFixtureScript(fixture = new ToDoItemsIntegTestFixture(), null);
        }

        private int notYetCompletedSize;
        private int completedSize;

        @Before
        public void setUp() throws Exception {

            // could use fixture#lookup(...), but can also just search directly.
            final List<ToDoItem> notYetCompleteItems = wrap(toDoItems).notYetComplete();
            final List<ToDoItem> completedItems = wrap(toDoItems).complete();

            notYetCompletedSize = notYetCompleteItems.size();
            completedSize = completedItems.size();

            assertThat(notYetCompletedSize).isGreaterThan(5);
        }

        @Test
        public void complete_and_notYetComplete() throws Exception {

            // given
            List<ToDoItem> notYetCompleteBefore = wrap(toDoItems).notYetComplete();
            ToDoItem toDoItem = notYetCompleteBefore.get(0);
            transactionService.nextTransaction();

            // when
            wrap(toDoItem).completed();
            transactionService.nextTransaction();

            // then
            List<ToDoItem> notYetCompleteAfter = wrap(toDoItems).notYetComplete();
            assertThat(notYetCompleteAfter).hasSize(notYetCompletedSize-1);
            assertThat(wrap(toDoItems).complete()).hasSize(completedSize+1);
            transactionService.nextTransaction();

            // and when
            wrap(toDoItem).notYetCompleted();
            transactionService.nextTransaction();

            // then
            assertThat(wrap(toDoItems).notYetComplete()).hasSize(notYetCompletedSize);
            assertThat(wrap(toDoItems).complete()).hasSize(completedSize);
        }
    }

    public static class NewToDo_and_Delete extends ToDoItemsIntegTest {

        @Before
        public void setUpData() throws Exception {
            // none
        }

        @Test
        public void happyCase() throws Exception {

            // given
            int size = wrap(toDoItems).notYetComplete().size();
            transactionService.nextTransaction();

            // when
            final ToDoItem newToDo = toDoItems.newToDo("new todo", Category.PROFESSIONAL, Subcategory.OPEN_SOURCE, null, null);
            transactionService.nextTransaction();

            // then
            assertThat(newToDo.getDescription()).isEqualTo("new todo");
            assertThat(newToDo.getCategory()).isEqualTo(Category.PROFESSIONAL);
            assertThat(wrap(toDoItems).notYetComplete()).hasSize(size+1);
            assertThat(repositoryService.isPersistent(newToDo)).isTrue();
            assertThat(repositoryService.isPersistent(wrap(newToDo))).isTrue();

            transactionService.nextTransaction();

            // when
            wrap(newToDo).delete();
            transactionService.nextTransaction();

            // then
            assertThat(wrap(toDoItems).notYetComplete()).hasSize(size);
        }

    }

}