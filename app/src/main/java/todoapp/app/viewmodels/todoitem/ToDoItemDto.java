package todoapp.app.viewmodels.todoitem;

import java.math.BigDecimal;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.google.common.collect.Lists;

import org.apache.isis.applib.services.jaxb.Dto;

import lombok.Getter;
import lombok.Setter;
import todoapp.dom.todoitem.ToDoItem;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "toDoItem",
    "description",
    "category",
    "subcategory",
    "cost",
    "similarItems"
})
@XmlRootElement(name = "toDoItemDto")
public class ToDoItemDto implements Dto {

    @XmlElement(required = true)
    @Getter @Setter
    protected ToDoItem toDoItem;

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

    @XmlElementWrapper
    @XmlElement(name = "todoItem")
    @Getter @Setter
    protected List<ToDoItem> similarItems = Lists.newArrayList();

//    @XmlElements({
//        @XmlElement(name = "todoItem")
//    })
//    @XmlJavaTypeAdapter(type=ToDoItem.class, value = PersistentEntityAdapter.class)
//    @Getter @Setter
//    protected List<ToDoItem> similarItems = Lists.newArrayList();
//
//    @XmlElementWrapper(name = "similarItems")
//    @XmlElement(name = "todoItem")
//    @Getter @Setter
//    protected List<ToDoItemDto> similarItems = Lists.newArrayList();

}
