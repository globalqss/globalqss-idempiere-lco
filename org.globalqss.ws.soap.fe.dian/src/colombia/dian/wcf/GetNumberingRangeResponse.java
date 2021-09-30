
package colombia.dian.wcf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.numberrangeresponselist.NumberRangeResponseList;


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
 *         &lt;element name="GetNumberingRangeResult" type="{http://schemas.datacontract.org/2004/07/NumberRangeResponseList}NumberRangeResponseList" minOccurs="0"/&gt;
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
    "getNumberingRangeResult"
})
@XmlRootElement(name = "GetNumberingRangeResponse")
public class GetNumberingRangeResponse {

    @XmlElementRef(name = "GetNumberingRangeResult", namespace = "http://wcf.dian.colombia", type = JAXBElement.class, required = false)
    protected JAXBElement<NumberRangeResponseList> getNumberingRangeResult;

    /**
     * Gets the value of the getNumberingRangeResult property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     *     
     */
    public JAXBElement<NumberRangeResponseList> getGetNumberingRangeResult() {
        return getNumberingRangeResult;
    }

    /**
     * Sets the value of the getNumberingRangeResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     *     
     */
    public void setGetNumberingRangeResult(JAXBElement<NumberRangeResponseList> value) {
        this.getNumberingRangeResult = value;
    }

}
