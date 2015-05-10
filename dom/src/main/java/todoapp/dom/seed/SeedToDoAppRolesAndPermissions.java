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
package todoapp.dom.seed;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import todoapp.dom.seed.roles.AuditModuleRoleAndPermissions;
import todoapp.dom.seed.roles.CommandModuleRoleAndPermissions;
import todoapp.dom.seed.roles.DevUtilsModuleRoleAndPermissions;
import todoapp.dom.seed.roles.TogglzModuleAdminRole;
import todoapp.dom.seed.roles.PublishingModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SessionLoggerModuleRoleAndPermissions;
import todoapp.dom.seed.roles.SettingsModuleRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppDomainAdminRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppFixtureServiceRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppRegularRoleAndPermissions;
import todoapp.dom.seed.roles.ToDoAppToDoItemVetoSelectedMembersPermissions;
import todoapp.dom.seed.roles.TranslationServicePoMenuRoleAndPermissions;
import todoapp.dom.seed.tenancies.ToDoAppAdminUserTenancy;
import todoapp.dom.seed.tenancies.UsersTenancy;
import todoapp.dom.seed.users.ToDoAppAdminUser;

@DomainService(
        nature = NatureOfService.DOMAIN
)
public class SeedToDoAppRolesAndPermissions {

    //region > init
    @Programmatic
    @PostConstruct
    public void init() {
        fixtureScripts.runFixtureScript(new SeedFixtureScript(), null);
    }
    //endregion

    //region  >  (injected)
    @Inject
    FixtureScripts fixtureScripts;
    //endregion

    public static class SeedFixtureScript extends FixtureScript {

        @Override
        protected void execute(final ExecutionContext executionContext) {

            executionContext.executeChild(this, new UsersTenancy());

            executionContext.executeChild(this, new ToDoAppDomainAdminRoleAndPermissions());
            executionContext.executeChild(this, new ToDoAppRegularRoleAndPermissions());
            executionContext.executeChild(this, new ToDoAppToDoItemVetoSelectedMembersPermissions());
            executionContext.executeChild(this, new ToDoAppFixtureServiceRoleAndPermissions());

            executionContext.executeChild(this, new TogglzModuleAdminRole());
            executionContext.executeChild(this, new AuditModuleRoleAndPermissions());
            executionContext.executeChild(this, new CommandModuleRoleAndPermissions());
            executionContext.executeChild(this, new SessionLoggerModuleRoleAndPermissions());
            executionContext.executeChild(this, new SettingsModuleRoleAndPermissions());
            executionContext.executeChild(this, new PublishingModuleRoleAndPermissions());
            executionContext.executeChild(this, new DevUtilsModuleRoleAndPermissions());

            executionContext.executeChild(this, new TranslationServicePoMenuRoleAndPermissions());

            executionContext.executeChild(this, new ToDoAppAdminUserTenancy());
            executionContext.executeChild(this, new ToDoAppAdminUser());
        }

    }

}
