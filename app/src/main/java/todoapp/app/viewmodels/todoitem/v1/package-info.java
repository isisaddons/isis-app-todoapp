@javax.xml.bind.annotation.XmlSchema(
        xmlns = {
                @XmlNs(
                        namespaceURI = "http://isis.apache.org/schema/common",
                        prefix = "common"
                )
        },
        namespace = "http://viewmodels.app.todoapp/v1/todoitem",
        elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)
package todoapp.app.viewmodels.todoitem.v1;

import javax.xml.bind.annotation.XmlNs;