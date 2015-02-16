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
package todoapp.fixture.todoitem.actions.complete;

import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.dom.module.todoitem.ToDoItems;
import todoapp.fixture.util.Util;

import java.util.Collection;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;
import org.apache.isis.applib.fixturescripts.FixtureScript;

public class ToDoItemCompleteOrig extends FixtureScript {

    //region > username (optional property)
    private String username;

    /**
     * The username that owns the item to be completed; optional, defaults to current user.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    //endregion

    //region > description (required property)
    private String description;

    /**
     * The description of the item to complete; required.
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
    //endregion

    //region > applicationUser (output property)
    private ApplicationUser applicationUser;

    /**
     * The application user created by the fixture (output property)
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }
    private void setApplicationUser(final ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
    //endregion

    //region > toDoItem (output property)

    private ToDoItem toDoItem;

    /**
     * The item that was completed (output property
     */
    public ToDoItem getToDoItem() {
        return toDoItem;
    }

    private void setToDoItem(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }
    //endregion

    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final String username = Util.coalesce(ec.getParameter("username"), getUsername(), getContainer().getUser().getName());

        final String description = Util.coalesce(ec.getParameter("description"), getDescription());
        if (description == null) {
            throw new IllegalArgumentException("description is required");
        }

        // validate user
        this.applicationUser = applicationUsers.findUserByUsername(username);
        if(this.applicationUser == null) {
            throw new IllegalArgumentException(String.format("No user with username: '%s'", username));
        }

        // find todoitem
        this.toDoItem = findToDoItem(description, username);
        if(this.toDoItem == null) {
            throw new IllegalArgumentException(String.format("No todo item with description: '%s' for user '%s'", description, username));
        }

        // execute
        this.toDoItem.setComplete(true);

        // make results available
        ec.addResult(this, applicationUser);
        ec.addResult(this, toDoItem);
    }

    private ToDoItem findToDoItem(final String description, final String username) {
        final String atPath = GlobalTenancy.TENANCY_PATH + username;
        final Collection<ToDoItem> filtered = Collections2.filter(getContainer().allInstances(ToDoItem.class), new Predicate<ToDoItem>() {
            @Override
            public boolean apply(ToDoItem input) {
                return Objects.equal(description, input.getDescription()) &&
                       Objects.equal(atPath, input.getAtPath());
            }
        });
        return filtered.isEmpty()? null: filtered.iterator().next();
    }

    //endregion

    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

}