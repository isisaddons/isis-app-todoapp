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


import todoapp.fixture.security.users.SvenUser;

import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityAdminRoleAndPermissions;
import org.isisaddons.module.security.seed.scripts.IsisModuleSecurityRegularUserRoleAndPermissions;

public class SvenUser_Has_IsisSecurityAdminAndRegularUserRole extends AbstractUserRoleFixtureScript {
    public SvenUser_Has_IsisSecurityAdminAndRegularUserRole() {
        super(SvenUser.USER_NAME,
                IsisModuleSecurityAdminRoleAndPermissions.ROLE_NAME,
                IsisModuleSecurityRegularUserRoleAndPermissions.ROLE_NAME);
    }
}
