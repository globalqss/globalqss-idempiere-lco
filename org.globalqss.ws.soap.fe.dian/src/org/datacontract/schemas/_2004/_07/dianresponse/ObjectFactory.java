
package org.datacontract.schemas._2004._07.dianresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.dianresponse package. 
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

    private final static QName _DianResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "DianResponse");
    private final static QName _ArrayOfDianResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "ArrayOfDianResponse");
    private final static QName _DianResponseErrorMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "ErrorMessage");
    private final static QName _DianResponseStatusCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "StatusCode");
    private final static QName _DianResponseStatusDescription_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "StatusDescription");
    private final static QName _DianResponseStatusMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "StatusMessage");
    private final static QName _DianResponseXmlBase64Bytes_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "XmlBase64Bytes");
    private final static QName _DianResponseXmlBytes_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "XmlBytes");
    private final static QName _DianResponseXmlDocumentKey_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "XmlDocumentKey");
    private final static QName _DianResponseXmlFileName_QNAME = new QName("http://schemas.datacontract.org/2004/07/DianResponse", "XmlFileName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.dianresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DianResponse }
     * 
     */
    public DianResponse createDianResponse() {
        return new DianResponse();
    }

    /**
     * Create an instance of {@link ArrayOfDianResponse }
     * 
     */
    public ArrayOfDianResponse createArrayOfDianResponse() {
        return new ArrayOfDianResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "DianResponse")
    public JAXBElement<DianResponse> createDianResponse(DianResponse value) {
        return new JAXBElement<DianResponse>(_DianResponse_QNAME, DianResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDianResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDianResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "ArrayOfDianResponse")
    public JAXBElement<ArrayOfDianResponse> createArrayOfDianResponse(ArrayOfDianResponse value) {
        return new JAXBElement<ArrayOfDianResponse>(_ArrayOfDianResponse_QNAME, ArrayOfDianResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "ErrorMessage", scope = DianResponse.class)
    public JAXBElement<ArrayOfstring> createDianResponseErrorMessage(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_DianResponseErrorMessage_QNAME, ArrayOfstring.class, DianResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "StatusCode", scope = DianResponse.class)
    public JAXBElement<String> createDianResponseStatusCode(String value) {
        return new JAXBElement<String>(_DianResponseStatusCode_QNAME, String.class, DianResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "StatusDescription", scope = DianResponse.class)
    public JAXBElement<String> createDianResponseStatusDescription(String value) {
        return new JAXBElement<String>(_DianResponseStatusDescription_QNAME, String.class, DianResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "StatusMessage", scope = DianResponse.class)
    public JAXBElement<String> createDianResponseStatusMessage(String value) {
        return new JAXBElement<String>(_DianResponseStatusMessage_QNAME, String.class, DianResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "XmlBase64Bytes", scope = DianResponse.class)
    public JAXBElement<byte[]> createDianResponseXmlBase64Bytes(byte[] value) {
        return new JAXBElement<byte[]>(_DianResponseXmlBase64Bytes_QNAME, byte[].class, DianResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "XmlBytes", scope = DianResponse.class)
    public JAXBElement<byte[]> createDianResponseXmlBytes(byte[] value) {
        return new JAXBElement<byte[]>(_DianResponseXmlBytes_QNAME, byte[].class, DianResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "XmlDocumentKey", scope = DianResponse.class)
    public JAXBElement<String> createDianResponseXmlDocumentKey(String value) {
        return new JAXBElement<String>(_DianResponseXmlDocumentKey_QNAME, String.class, DianResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/DianResponse", name = "XmlFileName", scope = DianResponse.class)
    public JAXBElement<String> createDianResponseXmlFileName(String value) {
        return new JAXBElement<String>(_DianResponseXmlFileName_QNAME, String.class, DianResponse.class, value);
    }

}
