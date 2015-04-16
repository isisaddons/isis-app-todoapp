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

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.module.security.dom.role.ApplicationRole;
import org.isisaddons.module.security.dom.role.ApplicationRoles;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.isisaddons.module.security.dom.user.ApplicationUsers;

import todoapp.fixture.util.Util;

public class UserRolesFixtureScript extends FixtureScript {

    //region > userName
    private String username;

    /**
     * Required.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
    //endregion

    //region > roleNames
    private List<String> roleNames;
    public List<String> getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(final List<String> roleNames) {
        this.roleNames = roleNames;
    }
    //endregion


    //region > applicationUser (output property)
    private ApplicationUser applicationUser;

    /**
     * The application user updated by the script.
     */
    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    private void setApplicationUser(final ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }
    //endregion

    //region > applicationRoleList (output property)
    private List<ApplicationRole> applicationRoleList;

    /**
     * The application roles corresponding to {@link #getRoleNames()}.
     */
    public List<ApplicationRole> getApplicationRoles() {
        return applicationRoleList;
    }

    public void setApplicationRoles(final List<ApplicationRole> applicationRoles) {
        this.applicationRoleList = applicationRoles;
    }
    //endregion

    @Override
    protected void execute(ExecutionContext ec) {

        // required
        final String username = Util.coalesce(ec.getParameter("username"), getUsername());
        if (username == null) {
            throw new IllegalArgumentException("username is required");
        }

        // validate user
        this.applicationUser = applicationUsers.findUserByUsername(username);
        if(this.applicationUser == null) {
            throw new IllegalArgumentException(String.format("No user with username: '%s'", username));
        }

        // no defaults for roles

        // validate all roleNames
        this.applicationRoleList =
                Lists.newArrayList(
                    Iterables.filter(
                        Iterables.transform(getRoleNames(), roleNameToRole()),
                        Predicates.notNull()));

        if(this.applicationRoleList.size() != getRoleNames().size()) {
            throw new IllegalArgumentException("One or more roles not found");
        }

        // execute
        ec.addResult(this, applicationUser.getName(), this.applicationUser);

        for (ApplicationRole applicationRole : this.applicationRoleList) {
            if (applicationRole != null) {
                this.applicationUser.addRole(applicationRole);
            }
            ec.addResult(this, applicationRole.getName(), applicationRole);
        }
    }

    private Function<String, ApplicationRole> roleNameToRole() {
        return new Function<String, ApplicationRole>() {
            @Nullable
            @Override
            public ApplicationRole apply(final String input) {
                return applicationRoles.findRoleByName(input);
            }
        };
    }

    private ApplicationRole findRoleByName(final String roleName) {
        return applicationRoles.findRoleByName(roleName);
    }

    @javax.inject.Inject
    private ApplicationUsers applicationUsers;
    @javax.inject.Inject
    private ApplicationRoles applicationRoles;

}
