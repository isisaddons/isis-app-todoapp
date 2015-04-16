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

import javax.ws.rs.core.Response;

import org.apache.isis.viewer.restfulobjects.rendering.service.RepresentationServiceForRestfulObjects;

/**
 * Demonstrates an extension to the built-in representation service. framework-provided services.
 *
 * <p>
 *     In <code>isis.properties</code>, is registered using:
 * </p>
 *
 * <pre>
 *     isis.services = \
 *                     ...,
 *                     1:todoapp.webapp.CustomRepresentationService,
 *                     ...,
 * </pre>
 * <p>
 *     that is, with a menuOrder of 1.
 * </p>
 */
public class CustomRepresentationService extends RepresentationServiceForRestfulObjects {

    @Override
    protected Response buildResponse(Response.ResponseBuilder responseBuilder) {
        responseBuilder.header("X-ResponseGeneratedBy", getClass().getCanonicalName());
        return super.buildResponse(responseBuilder);
    }

}
