package todoapp.app.viewmodels.todoitem;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;

import todoapp.app.viewmodels.todoitem.v1_0.ToDoItemDto;
import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_asDtoV1_0 {

    private final ToDoItem toDoItem;

    public ToDoItem_asDtoV1_0(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    @Action(
            semantics = SemanticsOf.SAFE,
            restrictTo = RestrictTo.PROTOTYPING
    )
    @ActionLayout(
            cssClassFa = "fa-external-link",
            named = "As DTO v1.0"
    )
    @MemberOrder(sequence = "1")
    public ToDoItemDto $$() {
        return contentMappingServiceForToDoItem.toDtoV1(toDoItem);
    }

    @javax.inject.Inject
    ContentMappingServiceForToDoItem contentMappingServiceForToDoItem;


}
