@javax.xml.bind.annotation.XmlSchema(
        namespace = "http://viewmodels.app.todoapp/todoitem/v1_1/todoitem.xsd",
        xmlns = {
                @javax.xml.bind.annotation.XmlNs(
                        namespaceURI = "http://isis.apache.org/schema/common",
                        prefix = "common"
                ),
                @javax.xml.bind.annotation.XmlNs(
                        namespaceURI = "http://viewmodels.app.todoapp/todoitem/v1_0/todoitem.xsd",
                        prefix = "todoitem-v1_0"
                )
        },
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)
package todoapp.app.viewmodels.todoitem.v1_1;

