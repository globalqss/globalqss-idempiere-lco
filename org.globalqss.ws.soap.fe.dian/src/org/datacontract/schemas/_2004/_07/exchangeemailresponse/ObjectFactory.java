
package org.datacontract.schemas._2004._07.exchangeemailresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.exchangeemailresponse package. 
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

    private final static QName _ExchangeEmailResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", "ExchangeEmailResponse");
    private final static QName _ExchangeEmailResponseCsvBase64Bytes_QNAME = new QName("http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", "CsvBase64Bytes");
    private final static QName _ExchangeEmailResponseMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", "Message");
    private final static QName _ExchangeEmailResponseStatusCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", "StatusCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.exchangeemailresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExchangeEmailResponse }
     * 
     */
    public ExchangeEmailResponse createExchangeEmailResponse() {
        return new ExchangeEmailResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", name = "ExchangeEmailResponse")
    public JAXBElement<ExchangeEmailResponse> createExchangeEmailResponse(ExchangeEmailResponse value) {
        return new JAXBElement<ExchangeEmailResponse>(_ExchangeEmailResponse_QNAME, ExchangeEmailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", name = "CsvBase64Bytes", scope = ExchangeEmailResponse.class)
    public JAXBElement<byte[]> createExchangeEmailResponseCsvBase64Bytes(byte[] value) {
        return new JAXBElement<byte[]>(_ExchangeEmailResponseCsvBase64Bytes_QNAME, byte[].class, ExchangeEmailResponse.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", name = "Message", scope = ExchangeEmailResponse.class)
    public JAXBElement<String> createExchangeEmailResponseMessage(String value) {
        return new JAXBElement<String>(_ExchangeEmailResponseMessage_QNAME, String.class, ExchangeEmailResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/ExchangeEmailResponse", name = "StatusCode", scope = ExchangeEmailResponse.class)
    public JAXBElement<String> createExchangeEmailResponseStatusCode(String value) {
        return new JAXBElement<String>(_ExchangeEmailResponseStatusCode_QNAME, String.class, ExchangeEmailResponse.class, value);
    }

}
