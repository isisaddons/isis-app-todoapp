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
package todoapp.fixture.module.security;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Password;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.user.AccountType;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

public class CreateUserFixtureScript extends FixtureScript {

    //region > username (required)
    /**
     * The user to setup (required).
     */
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    //endregion

    //region > emailAddress (optional)
    private String emailAddress;

    /**
     * Optional.
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }
    //endregion

    //region > accountType (optional)
    private AccountType accountType;

    /**
     * The account type to create; optional, defaults to {@link org.isisaddons.module.security.dom.user.AccountType#LOCAL}.
     */
    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(final AccountType accountType) {
        this.accountType = accountType;
    }
    //endregion



    //region > initialRole (optional)
    private ApplicationRole initialRole;

    /**
     * The initial role for the user to have (optional).
     *
     * <p>
     *     Defaults to <code>null</code>, meaning none.   Takes precedence over {@link #getInitialRoleName()}.
     * </p>
     */
    public ApplicationRole getInitialRole() {
        return initialRole;
    }
    public void setInitialRole(ApplicationRole initialRole) {
        this.initialRole = initialRole;
    }
    //endregion

    //region > initialRoleName (optional)
    private String initialRoleName;

    /**
     * The (name of) the initial role for the user to have (optional).
     *
     * <p>
     *     Defaults to <code>null</code>, meaning none.
     * </p>
     *
     */
    public String getInitialRoleName() {
        return initialRoleName;
    }

    public void setInitialRoleName(final String initialRoleName) {
        this.initialRoleName = initialRoleName;
    }
    //endregion

    //region > enabled (optional)
    private Boolean enabled;

    /**
     * Whether the user should be enabled or not (optional).
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
    //endregion

    //region > password (optional)
    private String password;

    /**
     * The password to set up for a {@link org.isisaddons.module.security.dom.user.AccountType#LOCAL local} user only
     * (optional).
     *
     * <p>
     *     Defaults to 'pass'.  Is ignored if setting up a delegate user.
     * </p>
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion

    //region > applicationUser (output)
    private ApplicationUser applicationUser;

    /**
     * The application user created by the fixture (output property)
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }
    private void setApplicationUser(final ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
    //endregion

    @Override
    protected void execute(FixtureScript.ExecutionContext ec) {

        // required
        final String username = todoapp.fixture.util.Util.coalesce(ec.getParameter("username"), getUsername());
        if (username == null) {
            throw new IllegalArgumentException("username is required");
        }

        // optional
        final String emailAddress = todoapp.fixture.util.Util.coalesce(ec.getParameter("emailAddress"), getEmailAddress());
        final AccountType accountType = todoapp.fixture.util.Util.coalesce(ec.getParameterAsEnum("accountType", AccountType.class), getAccountType(), AccountType.LOCAL);
//        final String tenancyPath = todoapp.fixture.util.Util.coalesce(ec.getParameter("atPath"), getAtPath(), GlobalTenancy.TENANCY_PATH + username);
        final String password = todoapp.fixture.util.Util.coalesce(ec.getParameter("password"), getPassword(), "pass");
        final ApplicationRole initialRole = todoapp.fixture.util.Util.coalesce(findRoleByName(ec.getParameter("initialRole")), getInitialRole(), findRoleByName(getInitialRoleName()));
        boolean enabled = todoapp.fixture.util.Util.coalesce(ec.getParameterAsBoolean("enabled"), getEnabled(), Boolean.TRUE);

        //
        // execute
        //
        final ApplicationUser applicationUser;
        if (accountType == AccountType.DELEGATED) {
            applicationUser = wrap(applicationUsers).newDelegateUser(username, initialRole, enabled);
        } else {
            final Password passwordValue = new Password(password);
            applicationUser = wrap(applicationUsers).newLocalUser(username, passwordValue, passwordValue, initialRole, enabled, emailAddress);
        }

        // no longer required, performed automatically by subscriber...
//        final ApplicationTenancy applicationTenancy = applicationTenancies.findTenancyByPath(tenancyPath);
//        if (applicationTenancy == null) {
//            final ApplicationTenancy rootTenancy = applicationTenancies.findTenancyByPath(GlobalTenancy.TENANCY_PATH);
//            applicationTenancies.newTenancy(username, tenancyPath, rootTenancy);
//        }
//        applicationUser.setTenancy(applicationTenancy);

        ec.addResult(this, username, applicationUser);

        setApplicationUser(applicationUser);
    }

    private ApplicationRole findRoleByName(final String initialRole1) {
        return applicationRoles.findRoleByName(initialRole1);
    }

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;

    @javax.inject.Inject
    private ApplicationRoles applicationRoles;

    @javax.inject.Inject
    private ApplicationTenancies applicationTenancies;


}
