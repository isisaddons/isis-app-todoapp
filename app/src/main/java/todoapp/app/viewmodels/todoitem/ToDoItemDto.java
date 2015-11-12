package todoapp.app.viewmodels.todoitem;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.schema.common.OidDto;

import lombok.Getter;
import lombok.Setter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "oid",
    "description",
    "category",
    "subcategory",
    "cost"
})
@XmlRootElement(name = "toDoItemDto")
public class ToDoItemDto implements Dto {

    @XmlElement(required = true)
    @Setter
    protected OidDto oid;
    @Programmatic
    public OidDto getOid() {
        return oid;
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
