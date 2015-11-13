package todoapp.app.viewmodels.todoitem;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.jaxb.Dto;

import todoapp.app.services.restful.ToDoAppContentMappingService;
import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_asDto implements Dto {

    private final ToDoItem toDoItem;

    public ToDoItem_asDto(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @MemberOrder(sequence = "1")
    public ToDoItemDto $$() {
        return toDoAppContentMappingService.toDto(toDoItem);
    }

    @javax.inject.Inject
    ToDoAppContentMappingService toDoAppContentMappingService;


}
