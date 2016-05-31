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

import java.io.StringReader;
import java.util.List;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;

import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.applib.services.jaxb.JaxbService;

import static org.assertj.core.api.Assertions.assertThat;
import todoapp.app.viewmodels.todoitem.ToDoItem_asV1_1;
import todoapp.app.viewmodels.todoitem.v1.ToDoItemV1_1;
import todoapp.app.viewmodels.todoitem.v1.dto.ToDoItemV11;
import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;
import todoapp.fixture.scenarios.RecreateToDoItemsForCurrentUser;

public class ToDoItemDtoIntegTest extends AbstractToDoIntegTest {

    @Inject
    BookmarkService bookmarkService;
    @Inject
    ToDoItems toDoItems;
    @Inject
    JaxbService jaxbService;

    RecreateToDoItemsForCurrentUser fixtureScript;
    ToDoItem toDoItem;

    @Before
    public void setUp() throws Exception {
        fixtureScript = new RecreateToDoItemsForCurrentUser();
        fixtureScripts.runFixtureScript(fixtureScript, null);

        final List<ToDoItem> all = toDoItems.notYetComplete();
        toDoItem = all.get(0);
        transactionService.nextTransaction();
    }

    ToDoItem_asV1_1 mixin;


    @Test
    public void can_marshall_using_vm_but_then_unmarshall_using_dto() throws Exception {

        // given
        mixin = mixin(ToDoItem_asV1_1.class, toDoItem);
        final ToDoItemV1_1 vm = mixin.$$();
        final String vmAsXml = jaxbService.toXml(vm);

        System.out.println(vmAsXml);

        // when
        final JAXBContext jaxbContext = JAXBContext.newInstance(ToDoItemV11.class);
        final Object unmarshal = jaxbContext.createUnmarshaller().unmarshal(new StreamSource(new StringReader(vmAsXml)), ToDoItemV11.class);

        // then
        assertThat(unmarshal).isInstanceOf(JAXBElement.class);

        JAXBElement jaxbElement = (JAXBElement) unmarshal;
        assertThat(jaxbElement.getValue()).isInstanceOf(ToDoItemV11.class);
        ToDoItemV11 dto = (ToDoItemV11) jaxbElement.getValue();

        assertThat(dto.getCategory()).isEqualTo(vm.getCategory());
        assertThat(dto.getSubcategory()).isEqualTo(vm.getSubcategory());
        assertThat(dto.getToDoItem().getId()).isEqualTo(bookmarkService.bookmarkFor(toDoItem).getIdentifier());
        assertThat(dto.getToDoItem().getType()).isEqualTo(bookmarkService.bookmarkFor(toDoItem).getObjectType());
        assertThat(dto.getDescription()).isEqualTo(vm.getDescription());
        assertThat(dto.getSimilarItems().getTodoItem().size()).isEqualTo(vm.getSimilarItems().size());
    }



}