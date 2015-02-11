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

import org.apache.isis.applib.fixturescripts.FixtureScript;

public class AllUserRoles extends FixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        // all-powerful superuser (security and todoapp)
        executionContext.executeChild(this, new SvenUser_Has_IsisSecurityAdminAndRegularUserRole());
        executionContext.executeChild(this, new SvenUser_Has_ToDoAppAdminRoles());

        // admin for todoapp
        executionContext.executeChild(this, new BobUser_Has_IsisSecurityModuleRegularRole());
        executionContext.executeChild(this, new BobUser_Has_ToDoAppAdminRoles());

        // dev for todoapp (can run fixtures)
        executionContext.executeChild(this, new DickUser_Has_IsisSecurityModuleRegularRole());
        executionContext.executeChild(this, new DickUser_Has_ToDoAppRegularRole());
        executionContext.executeChild(this, new DickUser_Has_ToDoAppFixturesServiceRole());

        // end-user of todoapp
        executionContext.executeChild(this, new JoeUser_Has_IsisSecurityModuleRegularRole());
        executionContext.executeChild(this, new JoeUser_Has_ToDoAppRegularRole());
    }

}
