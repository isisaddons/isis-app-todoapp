/*
 *  Copyright 2014 Dan Haywood
 *
 *  Licensed under the Apache License, Version 2.0 (the
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

import todoapp.dom.seed.roles.ToDoAppFixtureServiceRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppRegularRoleAndPermissions;
import todoapp.fixture.security.teardown.DeleteUserAndUserRolesAndToDoItems;
import todoapp.fixture.security.userrole.UserRolesFixtureScript;
import todoapp.fixture.security.users.CreateUserFixtureScript;

import java.util.Arrays;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class RecreateDickUserAndRolesAndToDoItems extends CreateUserFixtureScript {

    public static final String USER_NAME = "dick";

    public RecreateDickUserAndRolesAndToDoItems() {
        withDiscoverability(Discoverability.DISCOVERABLE);
    }

    @Override
    protected void execute(final ExecutionContext ec) {

        ec.setParameter("username", USER_NAME);

        // teardown
        ec.executeChild(this, new DeleteUserAndUserRolesAndToDoItems());

        // create user
        ec.executeChild(this, new CreateUserFixtureScript());

        // setup roles
        ec.executeChild(this, new UserRolesFixtureScript() {{
            setRoleNames(Arrays.asList(
                    // security regular user ('me' service)
                    IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME,
                    // regular app role
                    ToDoAppRegularRoleAndPermissions.ROLE_NAME,
                    // dev role (can run fixtures)
                    ToDoAppFixtureServiceRoleAndPermissions.ROLE_NAME));
        }});

        // create items
        ec.executeChild(this, new RecreateToDoItemsForCurrentUser() {{setNumberToCreate(12); setNumberToComplete(3);}});
    }

}
