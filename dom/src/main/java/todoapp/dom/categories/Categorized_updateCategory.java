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
package todoapp.dom.categories;

import java.util.List;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.SemanticsOf;

@Mixin
public class Categorized_updateCategory {

    private final Categorized categorized;

    public Categorized_updateCategory(final Categorized categorized) {
        this.categorized = categorized;
    }


    @ActionLayout(
            describedAs = "Update category and subcategory"
    )
    @Action(semantics = SemanticsOf.IDEMPOTENT)
    public Categorized $$(
            final Category category,
            final @Parameter(optionality = Optionality.OPTIONAL) Subcategory subcategory) {
        categorized.setCategory(category);
        categorized.setSubcategory(subcategory);
        return categorized;
    }
    public Category default0$$() {
        return categorized != null? categorized.getCategory(): null;
    }
    public Subcategory default1$$() {
        return categorized != null? categorized.getSubcategory(): null;
    }

    public List<Subcategory> choices1$$(final Category category) {
        return Subcategory.listFor(category);
    }
    
    public String validate$$(
            final Category category, final Subcategory subcategory) {
        return Subcategory.validate(category, subcategory);
    }
}
