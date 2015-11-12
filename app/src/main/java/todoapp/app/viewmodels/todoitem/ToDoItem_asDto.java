package todoapp.app.viewmodels.todoitem;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Mixin;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.bookmark.Bookmark;
import org.apache.isis.applib.services.bookmark.BookmarkService;
import org.apache.isis.schema.common.OidDto;

import ma.glasnost.orika.impl.DefaultMapperFactory;
import todoapp.dom.todoitem.ToDoItem;

@Mixin
public class ToDoItem_asDto implements Dto {

    private static final DefaultMapperFactory mapperFactory;

    static {
        mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.registerClassMap(
                mapperFactory.classMap(ToDoItem.class, todoapp.dto.todoitem.ToDoItemDto.class)
                        .byDefault() // all fields are the compatible
                        .toClassMap());
        mapperFactory.registerClassMap(
                mapperFactory.classMap(Bookmark.class, OidDto.class)
                        .field("identifier", "objectIdentifier") // customized
                        .byDefault() // all other fields are compatible
                        .toClassMap());
    }

    private final ToDoItem toDoItem;

    public ToDoItem_asDto(final ToDoItem toDoItem) {
        this.toDoItem = toDoItem;
    }

    //region > $$ (action)
    @Action(semantics = SemanticsOf.SAFE)
    @MemberOrder(sequence = "1")
    public ToDoItemDto $$() {

        final Bookmark bookmark = bookmarkService.bookmarkFor(toDoItem);

        final ToDoItemDto dto =
                mapperFactory.getMapperFacade().map(toDoItem, ToDoItemDto.class);
        final OidDto oidDto = mapperFactory.getMapperFacade().map(bookmark, OidDto.class);

        // manually wire together
        dto.setOid(oidDto);

        return dto;
    }
    //endregion
    
    //region > injected services
    @javax.inject.Inject
    private BookmarkService bookmarkService;
    //endregion


}
