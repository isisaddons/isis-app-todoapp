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
package todoapp.dom.app.exportjson;

import todoapp.dom.module.todoitem.ToDoItem;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Clob;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ExportAsJsonContributions extends AbstractFactoryAndRepository {

    // region > exportAsJson (action)
    /**
     * Demonstrates functionality of streaming back Clob/Blob result within an action with a prompt, i.e. Ajax request
     */
    @Action(semantics = SemanticsOf.SAFE)
    public Clob exportAsJson(
            final ToDoItem toDoItem,
            @ParameterLayout(named = "File name") String fileName
    ) {
        if(!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        return new Clob(
                fileName,
                "application/json",
                "{" +
                "\"description\": \"" + toDoItem.getDescription()+"\"" +
                ",\"complete\": " + ""+toDoItem.isComplete() +
                "}");
    }

    public String default1ExportAsJson() {
        return "todo";
    }
    //endregion

}
