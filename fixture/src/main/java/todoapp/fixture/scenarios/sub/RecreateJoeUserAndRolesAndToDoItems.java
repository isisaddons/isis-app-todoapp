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
package todoapp.fixture.scenarios.sub;

import todoapp.fixture.security.teardown.DeleteBobUserAndToDoItems;
import todoapp.fixture.security.userrole.JoeUser_Has_IsisSecurityModuleRegularRole;
import todoapp.fixture.security.userrole.JoeUser_Has_ToDoAppRegularRole;
import todoapp.fixture.security.users.AbstractUserFixtureScript;
import todoapp.fixture.security.users.BobUser;

public class RecreateJoeUserAndRolesAndToDoItems extends AbstractUserFixtureScript {

    @Override
    protected void execute(ExecutionContext executionContext) {

        executionContext.executeChild(this, new DeleteBobUserAndToDoItems());

        executionContext.executeChild(this, new BobUser());

        // end-user of todoapp
        executionContext.executeChild(this, new JoeUser_Has_IsisSecurityModuleRegularRole());
        executionContext.executeChild(this, new JoeUser_Has_ToDoAppRegularRole());
    }

}
