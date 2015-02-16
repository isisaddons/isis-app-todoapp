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
package todoapp.dom.module.categories;

import todoapp.dom.module.todoitem.ToDoItems;

import java.util.List;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.queryresultscache.QueryResultsCache;

@DomainService(nature = NatureOfService.VIEW_CONTRIBUTIONS_ONLY)
public class UpdateCategoryContributions extends AbstractFactoryAndRepository {

    //region > updateCategory (contributed action)

    @ActionLayout(
            describedAs = "Update category and subcategory"
    )
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Categorized updateCategory(
            final Categorized item,
            final @ParameterLayout(named="Category") Category category,
            final @Parameter(optionality = Optionality.OPTIONAL) @ParameterLayout(named="Subcategory") Subcategory subcategory) {
        item.setCategory(category);
        item.setSubcategory(subcategory);
        return item;
    }
    public Category default1UpdateCategory(
            final Categorized item) {
        return item != null? item.getCategory(): null;
    }
    public Subcategory default2UpdateCategory(
            final Categorized item) {
        return item != null? item.getSubcategory(): null;
    }

    public List<Subcategory> choices2UpdateCategory(
            final Categorized item, final Category category) {
        return Subcategory.listFor(category);
    }
    
    public String validateUpdateCategory(
            final Categorized item, final Category category, final Subcategory subcategory) {
        return Subcategory.validate(category, subcategory);
    }
    //endregion

    //region > injected services
    @javax.inject.Inject
    private ToDoItems toDoItems;

    @javax.inject.Inject
    private QueryResultsCache queryResultsCache;
    //endregion

}
