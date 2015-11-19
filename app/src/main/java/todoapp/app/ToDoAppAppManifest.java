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
package todoapp.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import org.isisaddons.metamodel.paraname8.NamedFacetOnParameterParaname8Factory;
import org.isisaddons.module.security.facets.TenantedAuthorizationFacetFactory;

import todoapp.dom.ToDoAppDomainModule;
import todoapp.fixture.ToDoAppFixtureModule;

public class ToDoAppAppManifest implements AppManifest {

    /**
     * Load all services and entities found in (the packages and subpackages within) these modules
     */
    @Override
    public List<Class<?>> getModules() {
        return Arrays.asList(
                ToDoAppDomainModule.class,
                ToDoAppFixtureModule.class,
                ToDoAppAppModule.class,
                org.isisaddons.module.audit.AuditModule.class,
                org.isisaddons.module.command.CommandModule.class,
                org.isisaddons.module.devutils.DevUtilsModule.class,
                org.isisaddons.module.docx.DocxModule.class,
                org.isisaddons.module.publishing.PublishingModule.class,
                org.isisaddons.module.sessionlogger.SessionLoggerModule.class,
                org.isisaddons.module.security.SecurityModule.class,
                org.isisaddons.module.settings.SettingsModule.class,
                org.isisaddons.wicket.gmap3.cpt.service.Gmap3ServiceModule.class
        );
    }

    @Override
    public List<Class<?>> getAdditionalServices() {
        return Arrays.asList(
                org.isisaddons.module.security.dom.password.PasswordEncryptionServiceUsingJBcrypt.class
               ,org.isisaddons.module.publishing.dom.eventserializer.RestfulObjectsSpecEventSerializer.class
        );
    }

    @Override
    public String getAuthenticationMechanism() {
        return null;
    }

    @Override
    public String getAuthorizationMechanism() {
        return null;
    }

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String, String> props = Maps.newHashMap();
        appendParaname8Facets(props);
        return props;
    }

    protected void appendParaname8Facets(final Map<String, String> props) {
        props.put(
                "isis.reflector.facets.include",
                Joiner.on(",").join(
                        TenantedAuthorizationFacetFactory.class.getName(),
                        NamedFacetOnParameterParaname8Factory.class.getName()));
        props.put("isis.services.eventbus.titleUiEvent.postForDefault", "true");
        props.put("isis.services.eventbus.iconUiEvent.postForDefault", "true");
        props.put("isis.services.eventbus.cssClassUiEvent.postForDefault", "true");
        props.put("isis.services.eventbus.actionDomainEvent.postForDefault", "true");
        props.put("isis.services.eventbus.propertyDomainEvent.postForDefault", "true");
        props.put("isis.services.eventbus.collectionDomainEvent.postForDefault", "true");
    }

}
