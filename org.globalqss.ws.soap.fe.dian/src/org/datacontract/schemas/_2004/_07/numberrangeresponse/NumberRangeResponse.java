
package org.datacontract.schemas._2004._07.numberrangeresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NumberRangeResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NumberRangeResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ResolutionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ResolutionDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Prefix" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="FromNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ToNumber" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/&gt;
 *         &lt;element name="ValidDateFrom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ValidDateTo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="TechnicalKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NumberRangeResponse", propOrder = {
    "resolutionNumber",
    "resolutionDate",
    "prefix",
    "fromNumber",
    "toNumber",
    "validDateFrom",
    "validDateTo",
    "technicalKey"
})
public class NumberRangeResponse {

    @XmlElementRef(name = "ResolutionNumber", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resolutionNumber;
    @XmlElementRef(name = "ResolutionDate", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> resolutionDate;
    @XmlElementRef(name = "Prefix", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> prefix;
    @XmlElement(name = "FromNumber")
    protected Long fromNumber;
    @XmlElement(name = "ToNumber")
    protected Long toNumber;
    @XmlElementRef(name = "ValidDateFrom", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> validDateFrom;
    @XmlElementRef(name = "ValidDateTo", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> validDateTo;
    @XmlElementRef(name = "TechnicalKey", namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> technicalKey;

    /**
     * Gets the value of the resolutionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResolutionNumber() {
        return resolutionNumber;
    }

    /**
     * Sets the value of the resolutionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResolutionNumber(JAXBElement<String> value) {
        this.resolutionNumber = value;
    }

    /**
     * Gets the value of the resolutionDate property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getResolutionDate() {
        return resolutionDate;
    }

    /**
     * Sets the value of the resolutionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setResolutionDate(JAXBElement<String> value) {
        this.resolutionDate = value;
    }

    /**
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPrefix(JAXBElement<String> value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the fromNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFromNumber() {
        return fromNumber;
    }

    /**
     * Sets the value of the fromNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFromNumber(Long value) {
        this.fromNumber = value;
    }

    /**
     * Gets the value of the toNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getToNumber() {
        return toNumber;
    }

    /**
     * Sets the value of the toNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setToNumber(Long value) {
        this.toNumber = value;
    }

    /**
     * Gets the value of the validDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getValidDateFrom() {
        return validDateFrom;
    }

    /**
     * Sets the value of the validDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setValidDateFrom(JAXBElement<String> value) {
        this.validDateFrom = value;
    }

    /**
     * Gets the value of the validDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getValidDateTo() {
        return validDateTo;
    }

    /**
     * Sets the value of the validDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setValidDateTo(JAXBElement<String> value) {
        this.validDateTo = value;
    }

    /**
     * Gets the value of the technicalKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getTechnicalKey() {
        return technicalKey;
    }

    /**
     * Sets the value of the technicalKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setTechnicalKey(JAXBElement<String> value) {
        this.technicalKey = value;
    }

}
