
package colombia.dian.wcf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.eventresponse.ArrayOfEventResponse;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SendEventUpdateStatusResult" type="{http://schemas.datacontract.org/2004/07/EventResponse}ArrayOfEventResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "sendEventUpdateStatusResult"
})
@XmlRootElement(name = "SendEventUpdateStatusResponse")
public class SendEventUpdateStatusResponse {

    @XmlElementRef(name = "SendEventUpdateStatusResult", namespace = "http://wcf.dian.colombia", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfEventResponse> sendEventUpdateStatusResult;

    /**
     * Gets the value of the sendEventUpdateStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     *     
     */
    public JAXBElement<ArrayOfEventResponse> getSendEventUpdateStatusResult() {
        return sendEventUpdateStatusResult;
    }

    /**
     * Sets the value of the sendEventUpdateStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     *     
     */
    public void setSendEventUpdateStatusResult(JAXBElement<ArrayOfEventResponse> value) {
        this.sendEventUpdateStatusResult = value;
    }

}
