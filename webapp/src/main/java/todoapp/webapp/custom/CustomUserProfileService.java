/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
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
package todoapp.webapp.custom;

import todoapp.dom.seed.tenancies.UsersTenancy;

import javax.inject.Inject;
import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.services.userprof.UserProfileService;

/**
 * Demonstrates how to provide a custom implementation of the {@link org.apache.isis.applib.services.userprof.UserProfileService}.
 */
@DomainService(
        nature = NatureOfService.DOMAIN
)
public class CustomUserProfileService implements UserProfileService {

    @Override
    @Programmatic
    public String userProfileName() {
        final ApplicationUser applicationUser = meService.me();

        final StringBuilder buf = new StringBuilder();
        final String username = applicationUser.getName();
        final ApplicationTenancy tenancy = applicationUser.getTenancy();

        buf.append("Hi ");
        buf.append(username);
        if (!tenancy.getPath().equals(UsersTenancy.TENANCY_PATH + username)) {
            buf.append(" @").append(tenancy.getName());
        }

        return buf.toString();
    }


    @Inject
    private MeService meService;
}
