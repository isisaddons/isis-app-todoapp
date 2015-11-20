@javax.xml.bind.annotation.XmlSchema(
        xmlns = {
                @XmlNs(
                        namespaceURI = "http://isis.apache.org/schema/common",
                        prefix = "common"
                ),
                @XmlNs(
                        namespaceURI = "http://viewmodels.app.todoapp/v1_0/todoitem",
                        prefix = "todoitem-v1_0"
                ),
                @XmlNs(
                        namespaceURI = "http://viewmodels.app.todoapp/v1_1/todoitem",
                        prefix = "todoitem-v1_1"
                )
        },
        namespace = "http://viewmodels.app.todoapp/v1_1/todoitem",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)
package todoapp.app.viewmodels.todoitem.v1_1;

import javax.xml.bind.annotation.XmlNs;