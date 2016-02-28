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
        propOrder = {
            "majorVersion",
            "minorVersion",
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
public class ToDoItemV1_0 implements Dto {

    @XmlElement(required = true, defaultValue = "1")
    public final String getMajorVersion() {
        return "1";
    }

    @XmlElement(required = true, defaultValue = "0")
    public String getMinorVersion() {
        return "0";
    }

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
