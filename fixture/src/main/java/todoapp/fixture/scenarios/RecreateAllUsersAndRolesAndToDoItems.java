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

import javax.inject.Inject;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public class RecreateAllUsersAndRolesAndToDoItems extends FixtureScript {

    public RecreateAllUsersAndRolesAndToDoItems() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext executionContext) {

        // for the current user (eg if logged in as todoapp-admin)
        executionContext.executeChild(this, new RecreateToDoItemsForCurrentUser());

        // for other pre-canned users
        executionContext.executeChild(this, new RecreateBillUserAndRolesAndToDoItems());
        executionContext.executeChild(this, new RecreateBobUserAndRolesAndToDoItems());
        executionContext.executeChild(this, new RecreateDickUserAndRolesAndToDoItems());
        executionContext.executeChild(this, new RecreateJoeUserAndRolesAndToDoItems());
        executionContext.executeChild(this, new RecreateSvenUserAndRolesAndToDoItems());
    }

    @Inject
    private IsisJdoSupport isisJdoSupport;

}