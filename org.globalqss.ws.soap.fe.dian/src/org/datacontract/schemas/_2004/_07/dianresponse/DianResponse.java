
package org.datacontract.schemas._2004._07.dianresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;


/**
 * <p>Java class for DianResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DianResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ErrorMessage" type="{http://schemas.microsoft.com/2003/10/Serialization/Arrays}ArrayOfstring" minOccurs="0"/&gt;
 *         &lt;element name="IsValid" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="StatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatusDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="StatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="XmlBase64Bytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="XmlBytes" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/&gt;
 *         &lt;element name="XmlDocumentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="XmlFileName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DianResponse", propOrder = {
    "errorMessage",
    "isValid",
    "statusCode",
    "statusDescription",
    "statusMessage",
    "xmlBase64Bytes",
    "xmlBytes",
    "xmlDocumentKey",
    "xmlFileName"
})
public class DianResponse {

    @XmlElementRef(name = "ErrorMessage", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfstring> errorMessage;
    @XmlElement(name = "IsValid")
    protected Boolean isValid;
    @XmlElementRef(name = "StatusCode", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusCode;
    @XmlElementRef(name = "StatusDescription", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusDescription;
    @XmlElementRef(name = "StatusMessage", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> statusMessage;
    @XmlElementRef(name = "XmlBase64Bytes", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> xmlBase64Bytes;
    @XmlElementRef(name = "XmlBytes", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<byte[]> xmlBytes;
    @XmlElementRef(name = "XmlDocumentKey", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlDocumentKey;
    @XmlElementRef(name = "XmlFileName", namespace = "http://schemas.datacontract.org/2004/07/DianResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlFileName;

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public JAXBElement<ArrayOfstring> getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     *     
     */
    public void setErrorMessage(JAXBElement<ArrayOfstring> value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the isValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsValid() {
        return isValid;
    }

    /**
     * Sets the value of the isValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsValid(Boolean value) {
        this.isValid = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusCode(JAXBElement<String> value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the statusDescription property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusDescription() {
        return statusDescription;
    }

    /**
     * Sets the value of the statusDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusDescription(JAXBElement<String> value) {
        this.statusDescription = value;
    }

    /**
     * Gets the value of the statusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getStatusMessage() {
        return statusMessage;
    }

    /**
     * Sets the value of the statusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setStatusMessage(JAXBElement<String> value) {
        this.statusMessage = value;
    }

    /**
     * Gets the value of the xmlBase64Bytes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getXmlBase64Bytes() {
        return xmlBase64Bytes;
    }

    /**
     * Sets the value of the xmlBase64Bytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setXmlBase64Bytes(JAXBElement<byte[]> value) {
        this.xmlBase64Bytes = value;
    }

    /**
     * Gets the value of the xmlBytes property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public JAXBElement<byte[]> getXmlBytes() {
        return xmlBytes;
    }

    /**
     * Sets the value of the xmlBytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     *     
     */
    public void setXmlBytes(JAXBElement<byte[]> value) {
        this.xmlBytes = value;
    }

    /**
     * Gets the value of the xmlDocumentKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getXmlDocumentKey() {
        return xmlDocumentKey;
    }

    /**
     * Sets the value of the xmlDocumentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setXmlDocumentKey(JAXBElement<String> value) {
        this.xmlDocumentKey = value;
    }

    /**
     * Gets the value of the xmlFileName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getXmlFileName() {
        return xmlFileName;
    }

    /**
     * Sets the value of the xmlFileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setXmlFileName(JAXBElement<String> value) {
        this.xmlFileName = value;
    }

}
