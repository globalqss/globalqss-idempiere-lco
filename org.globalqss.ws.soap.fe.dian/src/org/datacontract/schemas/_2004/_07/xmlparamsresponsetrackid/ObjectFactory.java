
package org.datacontract.schemas._2004._07.xmlparamsresponsetrackid;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.xmlparamsresponsetrackid package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ArrayOfXmlParamsResponseTrackId_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "ArrayOfXmlParamsResponseTrackId");
    private final static QName _XmlParamsResponseTrackId_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "XmlParamsResponseTrackId");
    private final static QName _XmlParamsResponseTrackIdDocumentKey_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "DocumentKey");
    private final static QName _XmlParamsResponseTrackIdProcessedMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "ProcessedMessage");
    private final static QName _XmlParamsResponseTrackIdSenderCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "SenderCode");
    private final static QName _XmlParamsResponseTrackIdXmlFileName_QNAME = new QName("http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", "XmlFileName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.xmlparamsresponsetrackid
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfXmlParamsResponseTrackId }
     * 
     */
    public ArrayOfXmlParamsResponseTrackId createArrayOfXmlParamsResponseTrackId() {
        return new ArrayOfXmlParamsResponseTrackId();
    }

    /**
     * Create an instance of {@link XmlParamsResponseTrackId }
     * 
     */
    public XmlParamsResponseTrackId createXmlParamsResponseTrackId() {
        return new XmlParamsResponseTrackId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "ArrayOfXmlParamsResponseTrackId")
    public JAXBElement<ArrayOfXmlParamsResponseTrackId> createArrayOfXmlParamsResponseTrackId(ArrayOfXmlParamsResponseTrackId value) {
        return new JAXBElement<ArrayOfXmlParamsResponseTrackId>(_ArrayOfXmlParamsResponseTrackId_QNAME, ArrayOfXmlParamsResponseTrackId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XmlParamsResponseTrackId }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link XmlParamsResponseTrackId }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "XmlParamsResponseTrackId")
    public JAXBElement<XmlParamsResponseTrackId> createXmlParamsResponseTrackId(XmlParamsResponseTrackId value) {
        return new JAXBElement<XmlParamsResponseTrackId>(_XmlParamsResponseTrackId_QNAME, XmlParamsResponseTrackId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "DocumentKey", scope = XmlParamsResponseTrackId.class)
    public JAXBElement<String> createXmlParamsResponseTrackIdDocumentKey(String value) {
        return new JAXBElement<String>(_XmlParamsResponseTrackIdDocumentKey_QNAME, String.class, XmlParamsResponseTrackId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "ProcessedMessage", scope = XmlParamsResponseTrackId.class)
    public JAXBElement<String> createXmlParamsResponseTrackIdProcessedMessage(String value) {
        return new JAXBElement<String>(_XmlParamsResponseTrackIdProcessedMessage_QNAME, String.class, XmlParamsResponseTrackId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "SenderCode", scope = XmlParamsResponseTrackId.class)
    public JAXBElement<String> createXmlParamsResponseTrackIdSenderCode(String value) {
        return new JAXBElement<String>(_XmlParamsResponseTrackIdSenderCode_QNAME, String.class, XmlParamsResponseTrackId.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/XmlParamsResponseTrackId", name = "XmlFileName", scope = XmlParamsResponseTrackId.class)
    public JAXBElement<String> createXmlParamsResponseTrackIdXmlFileName(String value) {
        return new JAXBElement<String>(_XmlParamsResponseTrackIdXmlFileName_QNAME, String.class, XmlParamsResponseTrackId.class, value);
    }

}
