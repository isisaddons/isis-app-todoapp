package todoapp.app.viewmodels.todoitem.v1;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.services.dto.Dto;
import org.apache.isis.applib.services.eventbus.TitleUiEvent;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        namespace = "http://viewmodels.app.todoapp/v1/todoitem",
        propOrder = {
            "description",
            "category",
            "subcategory",
            "cost"
        }
)
@XmlRootElement(name = "toDoItemDto")
@DomainObjectLayout(
        titleUiEvent = TitleUiEvent.Default.class
)
public class ToDoItemDto implements Dto {

    @XmlElement(required = true)
    @Getter @Setter
    protected String description;

    @XmlElement(required = true)
    @Getter @Setter
    protected String category;

    @Getter @Setter
    protected String subcategory;

    @Getter @Setter
    protected BigDecimal cost;

}
