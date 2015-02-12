/*
 *  Copyright 2013~2014 Dan Haywood
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
package todoapp.dom.module.export;

import todoapp.dom.app.ToDoAppDashboard;

import java.io.IOException;
import org.isisaddons.module.docx.dom.MergeException;
import org.jdom2.JDOMException;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.value.Blob;

@DomainService(
        nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY
)
public class ToDoItemsExportContributions {

    //region > exportToWordDoc (action)

    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            cssClassFa = "fa-download"
    )
    @MemberOrder(sequence = "10")
    public Blob exportToWordDoc(final ToDoAppDashboard dashboard) throws IOException, JDOMException, MergeException {
        return toDoItemsExportService.exportToWordDoc();
    }

    //endregion

    //region > injected services

    @javax.inject.Inject
    private ToDoItemsExportService toDoItemsExportService;
    //endregion

}
