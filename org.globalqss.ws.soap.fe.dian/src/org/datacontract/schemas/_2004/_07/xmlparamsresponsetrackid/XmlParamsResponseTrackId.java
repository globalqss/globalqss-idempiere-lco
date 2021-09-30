
package org.datacontract.schemas._2004._07.xmlparamsresponsetrackid;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for XmlParamsResponseTrackId complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="XmlParamsResponseTrackId"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="DocumentKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ProcessedMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="SenderCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Success" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
@XmlType(name = "XmlParamsResponseTrackId", propOrder = {
    "documentKey",
    "processedMessage",
    "senderCode",
    "success",
    "xmlFileName"
})
public class XmlParamsResponseTrackId {

    @XmlElementRef(name = "DocumentKey", namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", type = JAXBElement.class, required = false)
    protected JAXBElement<String> documentKey;
    @XmlElementRef(name = "ProcessedMessage", namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", type = JAXBElement.class, required = false)
    protected JAXBElement<String> processedMessage;
    @XmlElementRef(name = "SenderCode", namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", type = JAXBElement.class, required = false)
    protected JAXBElement<String> senderCode;
    @XmlElement(name = "Success")
    protected Boolean success;
    @XmlElementRef(name = "XmlFileName", namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", type = JAXBElement.class, required = false)
    protected JAXBElement<String> xmlFileName;

    /**
     * Gets the value of the documentKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDocumentKey() {
        return documentKey;
    }

    /**
     * Sets the value of the documentKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDocumentKey(JAXBElement<String> value) {
        this.documentKey = value;
    }

    /**
     * Gets the value of the processedMessage property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getProcessedMessage() {
        return processedMessage;
    }

    /**
     * Sets the value of the processedMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setProcessedMessage(JAXBElement<String> value) {
        this.processedMessage = value;
    }

    /**
     * Gets the value of the senderCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSenderCode() {
        return senderCode;
    }

    /**
     * Sets the value of the senderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSenderCode(JAXBElement<String> value) {
        this.senderCode = value;
    }

    /**
     * Gets the value of the success property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccess(Boolean value) {
        this.success = value;
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
