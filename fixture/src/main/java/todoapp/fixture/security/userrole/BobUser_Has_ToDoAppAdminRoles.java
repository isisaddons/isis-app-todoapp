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
package todoapp.fixture.security.userrole;


import todoapp.dom.seed.roles.AuditModuleRoleAndPermissions;
import todoapp.dom.seed.roles.CommandModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SessionLoggerModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SettingsModuleRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppDomainAdminRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppFixtureServiceRoleAndPermissions;
import todoapp.fixture.security.users.BobUser;

public class BobUser_Has_ToDoAppAdminRoles extends AbstractUserRoleFixtureScript {
    public BobUser_Has_ToDoAppAdminRoles() {
        super(BobUser.USER_NAME,
                ToDoAppDomainAdminRoleAndPermissions.ROLE_NAME,
                ToDoAppFixtureServiceRoleAndPermissions.ROLE_NAME,
                AuditModuleRoleAndPermissions.ROLE_NAME,
                CommandModuleRoleAndPermissions.ROLE_NAME,
                SessionLoggerModuleRoleAndPermissions.ROLE_NAME,
                SettingsModuleRoleAndPermissions.ROLE_NAME);
    }
}
