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
package todoapp.fixture.module.todoitem;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.sudo.SudoService;

import org.isisaddons.module.security.dom.user.ApplicationUsers;

import todoapp.dom.todoitem.ToDoItem;
import todoapp.dom.todoitem.ToDoItems;

public class ToDoItemComplete extends FixtureScript {

    //region > toDoItem (input property)

    private ToDoItem toDoItem;

    public ToDoItem getToDoItem() {
        return toDoItem;
    }
    public void setToDoItem(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
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

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        this.defaultParam("username", ec, getContainer().getUser().getName());

        // find todoitem
        if(this.toDoItem == null) {
            throw new IllegalArgumentException("ToDoItem required");
        }

        // execute
        sudoService.sudo(getUsername(),
                new Runnable() {
                    @Override
                    public void run() {
                        wrap(toDoItem).completed();
                    }
                });

        // make results available
        ec.addResult(this, toDoItem);
    }

    //endregion

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private SudoService sudoService;

}