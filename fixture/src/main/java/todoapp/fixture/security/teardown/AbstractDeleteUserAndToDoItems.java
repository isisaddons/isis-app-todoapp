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
package todoapp.fixture.security.teardown;

import todoapp.fixture.util.Util;

import javax.inject.Inject;
import org.isisaddons.module.security.seed.scripts.GlobalTenancy;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;

public abstract class AbstractDeleteUserAndToDoItems extends FixtureScript {

    //region > ownedBy
    private String ownedBy;

    public AbstractDeleteUserAndToDoItems(final String ownedBy) {
        setOwnedBy(ownedBy);
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }
    //endregion

    protected void delete(final ExecutionContext executionContext) {

        // defaults
        executionContext.setParameterIfNotPresent(
                "ownedBy",
                Util.coalesce(getOwnedBy(), getContainer().getUser().getName()));

        isisJdoSupport.executeUpdate(
                "DELETE FROM \"ToDoItem\" WHERE \"atPath\" = '" + GlobalTenancy.TENANCY_PATH + ownedBy + "'");

        final String ownedBy = executionContext.getParameter("ownedBy");
        isisJdoSupport.executeUpdate(
                "DELETE FROM \"IsisSecurityApplicationUserRoles\" WHERE \"userId\" IN (SELECT \"id\" FROM \"IsisSecurityApplicationUsers\" WHERE = '" + ownedBy + "'");
        isisJdoSupport.executeUpdate(
                "DELETE FROM \"IsisSecurityApplicationUsers\" WHERE \"id\" = '" + ownedBy + "'");
    }

    @Inject
    private IsisJdoSupport isisJdoSupport;

}