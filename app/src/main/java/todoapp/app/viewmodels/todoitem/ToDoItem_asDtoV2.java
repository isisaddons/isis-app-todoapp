package todoapp.app.viewmodels.todoitem;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jaxb.Dto;

import todoapp.app.services.restful.ToDoAppContentMappingService;
import todoapp.app.viewmodels.todoitem.v2.ToDoItemDto;
import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_asDtoV2 implements Dto {

    private final ToDoItem toDoItem;

    public ToDoItem_asDtoV2(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            cssClassFa = "fa-external-link"
    )
    @MemberOrder(sequence = "2")
    public ToDoItemDto $$() {
        return toDoAppContentMappingService.toDto2(toDoItem);
    }

    @javax.inject.Inject
    ToDoAppContentMappingService toDoAppContentMappingService;


}
