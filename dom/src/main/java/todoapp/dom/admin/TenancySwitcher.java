/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package todoapp.dom.admin;

import java.util.List;
import org.isisaddons.module.security.app.user.MeService;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancies;
import org.isisaddons.module.security.dom.tenancy.ApplicationTenancy;
import org.isisaddons.module.security.dom.user.ApplicationUser;
import org.apache.isis.applib.AbstractService;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;


@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        menuBar = DomainServiceLayout.MenuBar.TERTIARY
)
public class TenancySwitcher extends AbstractService {


    //region > switchTenancy (action)
    @Action(
            semantics = SemanticsOf.IDEMPOTENT
    )
    @ActionLayout(
            cssClassFa = "fa-exchange"
    )
    public void switchTenancy(final ApplicationTenancy applicationTenancy) {
        final ApplicationUser applicationUser = meService.me();
        applicationUser.updateTenancy(applicationTenancy);
    }

    public List<ApplicationTenancy> autoComplete0SwitchTenancy(final String search) {
        return applicationTenancies.autoComplete(search);
    }

    public ApplicationTenancy default0SwitchTenancy() {
        final ApplicationUser applicationUser = meService.me();
        return applicationUser.getTenancy();
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private MeService meService;

    @javax.inject.Inject
    private ApplicationTenancies applicationTenancies;
    //endregion

}

