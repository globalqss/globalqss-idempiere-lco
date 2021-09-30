
package org.datacontract.schemas._2004._07.numberrangeresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.numberrangeresponse package. 
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

    private final static QName _ArrayOfNumberRangeResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "ArrayOfNumberRangeResponse");
    private final static QName _NumberRangeResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "NumberRangeResponse");
    private final static QName _NumberRangeResponseResolutionNumber_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "ResolutionNumber");
    private final static QName _NumberRangeResponseResolutionDate_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "ResolutionDate");
    private final static QName _NumberRangeResponsePrefix_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "Prefix");
    private final static QName _NumberRangeResponseValidDateFrom_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "ValidDateFrom");
    private final static QName _NumberRangeResponseValidDateTo_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "ValidDateTo");
    private final static QName _NumberRangeResponseTechnicalKey_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponse", "TechnicalKey");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.numberrangeresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfNumberRangeResponse }
     * 
     */
    public ArrayOfNumberRangeResponse createArrayOfNumberRangeResponse() {
        return new ArrayOfNumberRangeResponse();
    }

    /**
     * Create an instance of {@link NumberRangeResponse }
     * 
     */
    public NumberRangeResponse createNumberRangeResponse() {
        return new NumberRangeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "ArrayOfNumberRangeResponse")
    public JAXBElement<ArrayOfNumberRangeResponse> createArrayOfNumberRangeResponse(ArrayOfNumberRangeResponse value) {
        return new JAXBElement<ArrayOfNumberRangeResponse>(_ArrayOfNumberRangeResponse_QNAME, ArrayOfNumberRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NumberRangeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NumberRangeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "NumberRangeResponse")
    public JAXBElement<NumberRangeResponse> createNumberRangeResponse(NumberRangeResponse value) {
        return new JAXBElement<NumberRangeResponse>(_NumberRangeResponse_QNAME, NumberRangeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "ResolutionNumber", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponseResolutionNumber(String value) {
        return new JAXBElement<String>(_NumberRangeResponseResolutionNumber_QNAME, String.class, NumberRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "ResolutionDate", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponseResolutionDate(String value) {
        return new JAXBElement<String>(_NumberRangeResponseResolutionDate_QNAME, String.class, NumberRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "Prefix", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponsePrefix(String value) {
        return new JAXBElement<String>(_NumberRangeResponsePrefix_QNAME, String.class, NumberRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "ValidDateFrom", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponseValidDateFrom(String value) {
        return new JAXBElement<String>(_NumberRangeResponseValidDateFrom_QNAME, String.class, NumberRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "ValidDateTo", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponseValidDateTo(String value) {
        return new JAXBElement<String>(_NumberRangeResponseValidDateTo_QNAME, String.class, NumberRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponse", name = "TechnicalKey", scope = NumberRangeResponse.class)
    public JAXBElement<String> createNumberRangeResponseTechnicalKey(String value) {
        return new JAXBElement<String>(_NumberRangeResponseTechnicalKey_QNAME, String.class, NumberRangeResponse.class, value);
    }

}
