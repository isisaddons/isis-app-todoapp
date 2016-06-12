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
package todoapp.integtests;

import org.apache.isis.core.integtestsupport.IsisSystemForTest;
import org.apache.isis.core.security.authentication.AuthenticationRequestNameOnly;
import org.apache.isis.objectstore.jdo.datanucleus.IsisConfigurationForJdoIntegTests;

import todoapp.app.ToDoAppAppManifest;

/**
 * Holds an instance of an {@link IsisSystemForTest} as a {@link ThreadLocal} on the current thread,
 * initialized with ToDo app's domain services. 
 */
public class ToDoAppSystemInitializer {
    
    private ToDoAppSystemInitializer(){}

    public static IsisSystemForTest initIsft() {
        IsisSystemForTest isft = IsisSystemForTest.getElseNull();
        if(isft == null) {
            isft = IsisSystemForTest.builder()
                    .withLoggingAt(org.apache.log4j.Level.INFO)
                    .with(new IsisConfigurationForJdoIntegTests())
                    .with(new ToDoAppAppManifest())
                    .with(new AuthenticationRequestNameOnly("todoapp-admin"))
                    .build()
                    .setUpSystem();
            IsisSystemForTest.set(isft);
        }
        return isft;
    }
}
