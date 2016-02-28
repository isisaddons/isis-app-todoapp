package todoapp.app.viewmodels.todoitem.v1;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.services.eventbus.TitleUiEvent;

import lombok.Getter;
import lombok.Setter;
import todoapp.dom.todoitem.ToDoItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
                "toDoItem",
                "similarItems"
        }
)
@XmlRootElement(name = "toDoItemDto")
@DomainObjectLayout(
        titleUiEvent = TitleUiEvent.Default.class
)
public class ToDoItemV1_1 extends ToDoItemV1_0 {

    @Override
    @XmlTransient
    public String getMinorVersion() {
        return "1";
    }

    @XmlElement(required = true)
    @Getter @Setter
    protected ToDoItem toDoItem;

    @XmlElementWrapper
    @XmlElement(name = "todoItem")
    @Getter @Setter
    protected List<ToDoItem> similarItems = Lists.newArrayList();

}
