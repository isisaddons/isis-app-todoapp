@javax.xml.bind.annotation.XmlSchema(
        xmlns = {
                @XmlNs(
                        namespaceURI = "http://isis.apache.org/schema/common",
                        prefix = "common"
                ),
                @XmlNs(
                        namespaceURI = "http://viewmodels.app.todoapp/v1/todoitem",
                        prefix = "todoitem-v1"
                ),
                @XmlNs(
                        namespaceURI = "http://viewmodels.app.todoapp/v2/todoitem",
                        prefix = "todoitem-v2"
                )
        },
        namespace = "http://viewmodels.app.todoapp/v2/todoitem",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)
package todoapp.app.viewmodels.todoitem.v2;

import javax.xml.bind.annotation.XmlNs;