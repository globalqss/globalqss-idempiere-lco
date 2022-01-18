
package org.datacontract.schemas._2004._07.numberrangeresponselist;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.numberrangeresponse.ArrayOfNumberRangeResponse;


/**
 * <p>Java class for NumberRangeResponseList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumberRangeResponseList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OperationCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="OperationDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResponseList" type="{http://schemas.datacontract.org/2004/07/NumberRangeResponse}ArrayOfNumberRangeResponse" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberRangeResponseList", propOrder = {
    "operationCode",
    "operationDescription",
    "responseList"
})
public class NumberRangeResponseList {

    @XmlElementRef(name = "OperationCode", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", type = JAXBElement.class, required = false)
    protected JAXBElement<String> operationCode;
    @XmlElementRef(name = "OperationDescription", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", type = JAXBElement.class, required = false)
    protected JAXBElement<String> operationDescription;
    @XmlElementRef(name = "ResponseList", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfNumberRangeResponse> responseList;

    /**
     * Gets the value of the operationCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOperationCode(JAXBElement<String> value) {
        this.operationCode = value;
    }

    /**
     * Gets the value of the operationDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOperationDescription() {
        return operationDescription;
    }

    /**
     * Sets the value of the operationDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOperationDescription(JAXBElement<String> value) {
        this.operationDescription = value;
    }

    /**
     * Gets the value of the responseList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     *     
     */
    public JAXBElement<ArrayOfNumberRangeResponse> getResponseList() {
        return responseList;
    }

    /**
     * Sets the value of the responseList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     *     
     */
    public void setResponseList(JAXBElement<ArrayOfNumberRangeResponse> value) {
        this.responseList = value;
    }

}
