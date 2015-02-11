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

import todoapp.fixture.todoitem.delete.ToDoItemsDelete;
import todoapp.fixture.todoitem.create.ToDoItemForBuyBread;
import todoapp.fixture.todoitem.create.ToDoItemForBuyMilk;
import todoapp.fixture.todoitem.create.ToDoItemForBuyStamps;
import todoapp.fixture.todoitem.create.ToDoItemForMowLawn;
import todoapp.fixture.todoitem.create.ToDoItemForOrganizeBrownBag;
import todoapp.fixture.todoitem.create.ToDoItemForPickUpLaundry;
import todoapp.fixture.todoitem.create.ToDoItemForSharpenKnives;
import todoapp.fixture.todoitem.create.ToDoItemForStageIsisRelease;
import todoapp.fixture.todoitem.create.ToDoItemForSubmitConferenceSession;
import todoapp.fixture.todoitem.create.ToDoItemForVacuumHouse;
import todoapp.fixture.todoitem.create.ToDoItemForWriteBlogPost;
import todoapp.fixture.todoitem.create.ToDoItemForWriteToPenPal;
import todoapp.fixture.util.Util;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class ToDoItemsRecreate extends FixtureScript {

    public ToDoItemsRecreate() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    //region > ownedBy (optional)
    private String ownedBy;

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
    //endregion

    @Override
    protected void execute(ExecutionContext executionContext) {

        // defaults
        executionContext.setParameterIfNotPresent(
                "ownedBy",
                Util.coalesce(getOwnedBy(), getContainer().getUser().getName()));

        // prereqs
        executionContext.executeChild(this, new ToDoItemsDelete());

        // create items
        executionContext.executeChild(this, new ToDoItemForBuyMilk());
        executionContext.executeChild(this, new ToDoItemForBuyBread());
        executionContext.executeChild(this, new ToDoItemForBuyStamps());
        executionContext.executeChild(this, new ToDoItemForPickUpLaundry());
        executionContext.executeChild(this, new ToDoItemForMowLawn());
        executionContext.executeChild(this, new ToDoItemForVacuumHouse());
        executionContext.executeChild(this, new ToDoItemForSharpenKnives());
        executionContext.executeChild(this, new ToDoItemForWriteToPenPal());
        executionContext.executeChild(this, new ToDoItemForWriteBlogPost());
        executionContext.executeChild(this, new ToDoItemForOrganizeBrownBag());
        executionContext.executeChild(this, new ToDoItemForSubmitConferenceSession());
        executionContext.executeChild(this, new ToDoItemForStageIsisRelease());
    }
}