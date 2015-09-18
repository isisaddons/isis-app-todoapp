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
package todoapp.dom.appuser;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import org.isisaddons.module.security.dom.tenancy.ApplicationTenancyPathEvaluator;

import todoapp.dom.todoitem.ToDoItem;


@DomainService(
        nature = NatureOfService.DOMAIN
)
public class ApplicationTenancyPathEvaluatorForToDoApp implements ApplicationTenancyPathEvaluator {

    @Override
    public boolean handles(final Class<?> cls) {
        return ToDoItem.class == cls;
    }

    @Override
    public String applicationTenancyPathFor(final Object domainObject) {
        // always safe to do, per the handles(...) method earlier
        final ToDoItem toDoItem = (ToDoItem) domainObject;
        return toDoItem.getAtPath();
    }
}

