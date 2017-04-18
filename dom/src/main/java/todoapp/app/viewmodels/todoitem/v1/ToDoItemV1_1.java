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

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.DomainObjectLayout;
import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.services.dto.Dto;

import lombok.Getter;
import lombok.Setter;
import todoapp.dom.todoitem.ToDoItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        propOrder = {
            "majorVersion",
            "minorVersion",
            "description",
            "category",
            "subcategory",
            "cost",
            "toDoItem",
            "similarItems"
        }
)
@XmlRootElement(name = "toDoItemDto")
@DomainObject(
        editing = Editing.DISABLED
)
@DomainObjectLayout(
//        titleUiEvent = TitleUiEvent.Default.class
)
public class ToDoItemV1_1 implements Dto {

    private static final String MAJOR_VERSION = "1";

    private static final String MINOR_VERSION = "1";

    /**
     * Matches the namespace version.  Bump only if there is a breaking change in the serialized XML.
     */
    @XmlElement(required = true, defaultValue = "1")
    public final String getMajorVersion() {
        return MAJOR_VERSION;
    }

    /**
     * Increment whenever there is a non-breaking change in the serialized XML (eg new optional fields).
     */
    @XmlElement(required = true, defaultValue = "1")
    public String getMinorVersion() {
        return MINOR_VERSION;
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

    @XmlElement(required = true)
    @Getter @Setter
    protected ToDoItem toDoItem;

    @XmlElementWrapper
    @XmlElement(name = "todoItem")
    @Getter @Setter
    protected List<ToDoItem> similarItems = Lists.newArrayList();

}
