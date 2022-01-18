
package colombia.dian.wcf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.exchangeemailresponse.ExchangeEmailResponse;


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
 *         &lt;element name="GetExchangeEmailsResult" type="{http://schemas.datacontract.org/2004/07/ExchangeEmailResponse}ExchangeEmailResponse" minOccurs="0"/&gt;
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
    "getExchangeEmailsResult"
})
@XmlRootElement(name = "GetExchangeEmailsResponse")
public class GetExchangeEmailsResponse {

    @XmlElementRef(name = "GetExchangeEmailsResult", namespace = "http://wcf.dian.colombia", type = JAXBElement.class, required = false)
    protected JAXBElement<ExchangeEmailResponse> getExchangeEmailsResult;

    /**
     * Gets the value of the getExchangeEmailsResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     *     
     */
    public JAXBElement<ExchangeEmailResponse> getGetExchangeEmailsResult() {
        return getExchangeEmailsResult;
    }

    /**
     * Sets the value of the getExchangeEmailsResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     *     
     */
    public void setGetExchangeEmailsResult(JAXBElement<ExchangeEmailResponse> value) {
        this.getExchangeEmailsResult = value;
    }

}
