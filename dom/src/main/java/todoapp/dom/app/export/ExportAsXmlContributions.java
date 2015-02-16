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
package todoapp.dom.app.export;

import todoapp.dom.app.dashboard.ToDoAppDashboard;
import todoapp.dom.module.todoitem.ToDoItem;

import javax.inject.Inject;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.xmlsnapshot.XmlSnapshotService;
import org.apache.isis.applib.value.Clob;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class ExportAsXmlContributions extends AbstractFactoryAndRepository {

    // region > exportAsXml for ToDoItem (action)
    @Action(semantics = SemanticsOf.SAFE)
    public Clob exportAsXml(
            final ToDoItem toDoItem,
            @ParameterLayout(named = "File name") String fileName
    ) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }

        final XmlSnapshotService.Builder builder = xmlSnapshotService.builderFor(toDoItem);
        builder.includePath("dependencies");
        builder.includePath("similarTo");

        final XmlSnapshotService.Snapshot snapshot = builder.build();

        return new Clob(
                fileName,
                "application/xml",
                snapshot.getXmlDocumentAsString());
    }

    public String default1ExportAsXml(final ToDoItem toDoItem) {
        return "todo";
    }
    //endregion

    // region > exportAsXml for ToDoItem (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    public Clob exportAsXml(
            final ToDoAppDashboard dashboard,
            @ParameterLayout(named = "File name") String fileName
    ) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }

        final XmlSnapshotService.Builder builder = xmlSnapshotService.builderFor(dashboard);
        builder.includePath("notYetComplete");

        final XmlSnapshotService.Snapshot snapshot = builder.build();

        return new Clob(
                fileName,
                "application/xml",
                snapshot.getXmlDocumentAsString());
    }

    public String default1ExportAsXml(final ToDoAppDashboard dashboard) {
        return "todo";
    }
    //endregion

    @Inject
    private XmlSnapshotService xmlSnapshotService;
}
