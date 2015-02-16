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
package todoapp.fixture.scenarios;

import todoapp.dom.module.todoitem.ToDoItem;
import todoapp.fixture.todoitem.create.ToDoItemCreate;

import java.util.Collections;
import java.util.List;
import com.google.common.collect.Lists;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class RecreateToDoItemsForCurrentUser extends FixtureScript {

    public RecreateToDoItemsForCurrentUser() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

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

    //region > numberToCreate;
    private Integer numberToCreate;

    /**
     * Number to create; optional, defaults to 12
     */
    @Property(optionality = Optionality.OPTIONAL)
    public Integer getNumberToCreate() {
        return numberToCreate;
    }

    public void setNumberToCreate(final Integer numberToCreate) {
        this.numberToCreate = numberToCreate;
    }
    //endregion

    //region > numberToComplete
    private Integer numberToComplete = 2;

    /**
     * Number to complete; optional, defaults to 2.
     */
    @Property(optionality = Optionality.OPTIONAL)
    public Integer getNumberToComplete() {
        return numberToComplete;
    }

    public void setNumberToComplete(final Integer numberToComplete) {
        this.numberToComplete = numberToComplete;
    }
    //endregion

    //region > applicationUser (output property)
    private ApplicationUser applicationUser;

    /**
     * The application user updated by the script.
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    private void setApplicationUser(final ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
    //endregion


    @Override
    protected void execute(final ExecutionContext ec) {

        // defaults
        final String username = defaultParam("username", ec, getContainer().getUser().getName());
        final Integer numberToCreate = defaultParam("numberToCreate", ec, 12);
        final Integer numberToComplete = defaultParam("numberToComplete", ec, 2);


        // validate user
        this.applicationUser = applicationUsers.findUserByUsername(username);
        if(this.applicationUser == null) {
            throw new IllegalArgumentException(String.format("No user with username: '%s'", username));
        }

        // validate numberToXxx
        final int maxNumberCanned = ToDoItemCreate.numberCanned();
        if(numberToCreate > maxNumberCanned) {
            throw new IllegalArgumentException(String.format("Max number to request is %d", maxNumberCanned));
        }
        if(numberToComplete > numberToCreate) {
            throw new IllegalArgumentException(String.format("numberToComplete (%d) cannot be greater than numberToCreate (%d)", numberToComplete, numberToCreate));
        }



        //
        // execute...
        //
        final String atPath = GlobalTenancy.TENANCY_PATH + username;

        // delete
        isisJdoSupport.executeUpdate("delete from \"ToDoItem\" where \"atPath\" = '" + atPath + "'");

        //
        // create items
        //
        List<ToDoItem> newToDoItems = Lists.newArrayList();
        for (int i = 0; i < numberToCreate; i++) {
            final int index = i;
            final ToDoItemCreate script = new ToDoItemCreate() {{
                setUsername(username);
                setIndex(index);
            }};
            ec.executeChild(this, script);
            newToDoItems.add(script.getToDoItem());
        }

        //
        // complete some
        //
        Collections.shuffle(newToDoItems);
        for (int i = 0; i < numberToComplete; i++) {
            final ToDoItem toDoItem = newToDoItems.get(i);
            toDoItem.setComplete(true);
        }
    }


    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private IsisJdoSupport isisJdoSupport;

}