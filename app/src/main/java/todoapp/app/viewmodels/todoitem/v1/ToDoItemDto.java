package todoapp.app.viewmodels.todoitem.v1;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.services.eventbus.TitleUiEvent;
import org.apache.isis.applib.services.jaxb.Dto;

import lombok.Getter;
import lombok.Setter;
import todoapp.dom.todoitem.ToDoItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        namespace = "http://viewmodels.app.todoapp/v1/todoitem",
        propOrder = {
            "toDoItem",
            "description",
            "cost",
            "similarItems"
        }
)
@XmlRootElement(name = "toDoItemDto")
@DomainObjectLayout(
        titleUiEvent = TitleUiEvent.Default.class
)
public class ToDoItemDto implements Dto {

    @XmlElement(required = true)
    @Getter @Setter
    protected ToDoItem toDoItem;

    @XmlElement(required = true)
    @Getter @Setter
    protected String description;

    @Getter @Setter
    protected BigDecimal cost;

    @XmlElementWrapper
    @XmlElement(name = "todoItem")
    @Getter @Setter
    protected List<ToDoItem> similarItems = Lists.newArrayList();

}
