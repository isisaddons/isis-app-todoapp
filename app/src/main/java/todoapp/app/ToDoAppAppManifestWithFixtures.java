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
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.isis.applib.AppManifest;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import todoapp.dom.ToDoAppDomainModule;
import todoapp.fixture.ToDoAppFixtureModule;
import todoapp.fixture.demo.DemoFixture;

/**
 * Bootstrap the application.
 */
public class ToDoAppAppManifestWithFixtures extends ToDoAppAppManifest {

    @Override
    public List<Class<? extends FixtureScript>> getFixtures() {
        return Lists.newArrayList(DemoFixture.class);
    }

    @Override
    public Map<String, String> getConfigurationProperties() {
        Map<String, String> props = super.getConfigurationProperties();
        appendInstallFixturesKey(props);
        return props;
    }

    protected void appendInstallFixturesKey(final Map<String, String> props) {
        props.put("isis.persistor.datanucleus.install-fixtures", "true");
    }

}
