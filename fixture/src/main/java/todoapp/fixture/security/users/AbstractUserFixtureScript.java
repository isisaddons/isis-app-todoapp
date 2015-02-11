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
package todoapp.fixture.security.users;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Password;

public abstract class AbstractUserFixtureScript extends FixtureScript {

    private ApplicationRole initialRole;

    /**
     * The initial role for the user to have, if any.
     *
     * <p>
     *     Defaults to <code>null</code>, meaning none.
     * </p>
     */
    public ApplicationRole getInitialRole() {
        return initialRole;
    }
    public void setInitialRole(ApplicationRole initialRole) {
        this.initialRole = initialRole;
    }

    private Boolean enabled;

    /**
     * Whether the user should be enabled or not.
     *
     * <p>
     *     Defaults to <code>null</code>, which is interpreted as disabled.
     * </p>
     */
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    private String password;

    /**
     * The password to set up for a {@link org.isisaddons.module.security.dom.user.AccountType#LOCAL local} user only.
     * Is ignored if setting up a delegate user.
     *
     * <p>
     *     Defaults to '12345678a'
     * </p>
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    protected ApplicationUser create(
            final String name,
            final AccountType accountType,
            final String tenancyPath,
            final ExecutionContext executionContext) {

        return create(name, null, accountType, tenancyPath, executionContext);
    }

    protected ApplicationUser create(
        final String name,
        final String emailAddress,
        final AccountType accountType,
        String tenancyPath,
        final ExecutionContext executionContext) {

        if(tenancyPath == null) {
            tenancyPath = "/" + name;
        }

        final ApplicationUser applicationUser;
        if(accountType == AccountType.DELEGATED) {
            applicationUser = applicationUsers.newDelegateUser(name, null, null);
        } else {
            final String passwordStr = Util.coalesce(executionContext.getParameter("password"), getPassword(), "12345678a");
            final Password password = new Password(passwordStr);
            applicationUser = applicationUsers.newLocalUser(name, password, password, null, null, emailAddress);
        }

        final ApplicationTenancy applicationTenancy = applicationTenancies.findTenancyByPath(tenancyPath);
        if(applicationTenancy == null) {
            final ApplicationTenancy rootTenancy = applicationTenancies.findTenancyByPath(GlobalTenancy.TENANCY_PATH);
            applicationTenancies.newTenancy(name, tenancyPath, rootTenancy);
        }
        applicationUser.setTenancy(applicationTenancy);

        executionContext.addResult(this, name, applicationUser);
        return applicationUser;
    }

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private ApplicationTenancies applicationTenancies;
}
