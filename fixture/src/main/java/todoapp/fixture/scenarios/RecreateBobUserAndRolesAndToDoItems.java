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

import java.util.Arrays;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

import todoapp.dom.seed.roles.AuditModuleRoleAndPermissions;
import todoapp.dom.seed.roles.CommandModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SessionLoggerModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SettingsModuleRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppFixtureServiceRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppRegularRoleAndPermissions;
import todoapp.fixture.module.DeleteUserAndUserRolesAndToDoItems;
import todoapp.fixture.module.security.CreateUserFixtureScript;
import todoapp.fixture.module.security.UserRolesFixtureScript;

public class RecreateBobUserAndRolesAndToDoItems extends FixtureScript {

    public static final String USER_NAME = "bob";

    public RecreateBobUserAndRolesAndToDoItems() {
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
                    ToDoAppRegularRoleAndPermissions.ROLE_NAME,
                    ToDoAppFixtureServiceRoleAndPermissions.ROLE_NAME,
                    AuditModuleRoleAndPermissions.ROLE_NAME,
                    CommandModuleRoleAndPermissions.ROLE_NAME,
                    SessionLoggerModuleRoleAndPermissions.ROLE_NAME,
                    SettingsModuleRoleAndPermissions.ROLE_NAME));
        }});

        // create items
        ec.executeChild(this, new RecreateToDoItemsForCurrentUser(){{setNumberToCreate(8); setNumberToComplete(3);}});
    }

}
