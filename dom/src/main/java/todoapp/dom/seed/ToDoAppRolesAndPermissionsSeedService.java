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

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.fixturescripts.FixtureScripts;
import todoapp.dom.seed.roles.*;
import todoapp.dom.seed.tenancies.ToDoAppAdminUserTenancy;
import todoapp.dom.seed.tenancies.UsersTenancy;
import todoapp.dom.seed.users.ToDoAppAdminUser;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@DomainService(
        nature = NatureOfService.DOMAIN
)
@DomainServiceLayout(
        menuOrder = "1100" // not visible, but determines the order initialized (must come after security module's seed service)
)
public class ToDoAppRolesAndPermissionsSeedService {

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

            executionContext.executeChild(this, new ToDoAppRegularRoleAndPermissions());
            executionContext.executeChild(this, new ToDoAppToDoItemVetoSelectedMembersPermissions());
            executionContext.executeChild(this, new ToDoAppFixtureServiceRoleAndPermissions());

            executionContext.executeChild(this, new TogglzModuleAdminRole());
            executionContext.executeChild(this, new AuditModuleRoleAndPermissions());
            executionContext.executeChild(this, new CommandModuleRoleAndPermissions());
            executionContext.executeChild(this, new SessionLoggerModuleRoleAndPermissions());
            executionContext.executeChild(this, new SettingsModuleRoleAndPermissions());
            executionContext.executeChild(this, new PublishingModuleRoleAndPermissions());

            executionContext.executeChild(this, new ApacheIsisApplibDtoRoleAndPermissions());
            executionContext.executeChild(this, new ApacheIsisPersistableMixinsRoleAndPermissions());
            executionContext.executeChild(this, new ApacheIsisApplibMixinsRoleAndPermissions());
            executionContext.executeChild(this, new ApacheIsisMetadataMenuRoleAndPermissions());
            executionContext.executeChild(this, new ApacheIsisTranslationServicePoMenuRoleAndPermissions());
            executionContext.executeChild(this, new ApacheIsisViewerWicketMixinsRoleAndPermissions());

            executionContext.executeChild(this, new ToDoAppAdminUserTenancy());
            executionContext.executeChild(this, new ToDoAppAdminUser());
        }

    }

}
