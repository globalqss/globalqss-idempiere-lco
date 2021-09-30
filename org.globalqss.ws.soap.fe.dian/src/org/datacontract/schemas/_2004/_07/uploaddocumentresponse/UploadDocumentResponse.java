
package org.datacontract.schemas._2004._07.uploaddocumentresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import org.datacontract.schemas._2004._07.xmlparamsresponsetrackid.ArrayOfXmlParamsResponseTrackId;


/**
 * <p>Java class for UploadDocumentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UploadDocumentResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ErrorMessageList" type="{http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId}ArrayOfXmlParamsResponseTrackId" minOccurs="0"/&gt;
 *         &lt;element name="ZipKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UploadDocumentResponse", propOrder = {
    "errorMessageList",
    "zipKey"
})
public class UploadDocumentResponse {

    @XmlElementRef(name = "ErrorMessageList", namespace = "http://schemas.datacontract.org/2004/07/UploadDocumentResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfXmlParamsResponseTrackId> errorMessageList;
    @XmlElementRef(name = "ZipKey", namespace = "http://schemas.datacontract.org/2004/07/UploadDocumentResponse", type = JAXBElement.class, required = false)
    protected JAXBElement<String> zipKey;

    /**
     * Gets the value of the errorMessageList property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     *     
     */
    public JAXBElement<ArrayOfXmlParamsResponseTrackId> getErrorMessageList() {
        return errorMessageList;
    }

    /**
     * Sets the value of the errorMessageList property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     *     
     */
    public void setErrorMessageList(JAXBElement<ArrayOfXmlParamsResponseTrackId> value) {
        this.errorMessageList = value;
    }

    /**
     * Gets the value of the zipKey property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getZipKey() {
        return zipKey;
    }

    /**
     * Sets the value of the zipKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setZipKey(JAXBElement<String> value) {
        this.zipKey = value;
    }

}
