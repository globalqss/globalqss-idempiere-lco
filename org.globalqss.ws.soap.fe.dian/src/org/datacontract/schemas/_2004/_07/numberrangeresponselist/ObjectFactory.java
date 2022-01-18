
package org.datacontract.schemas._2004._07.numberrangeresponselist;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.numberrangeresponse.ArrayOfNumberRangeResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.numberrangeresponselist package. 
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

    private final static QName _NumberRangeResponseList_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponseList", "NumberRangeResponseList");
    private final static QName _NumberRangeResponseListOperationCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponseList", "OperationCode");
    private final static QName _NumberRangeResponseListOperationDescription_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponseList", "OperationDescription");
    private final static QName _NumberRangeResponseListResponseList_QNAME = new QName("http://schemas.datacontract.org/2004/07/NumberRangeResponseList", "ResponseList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.numberrangeresponselist
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link NumberRangeResponseList }
     * 
     */
    public NumberRangeResponseList createNumberRangeResponseList() {
        return new NumberRangeResponseList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", name = "NumberRangeResponseList")
    public JAXBElement<NumberRangeResponseList> createNumberRangeResponseList(NumberRangeResponseList value) {
        return new JAXBElement<NumberRangeResponseList>(_NumberRangeResponseList_QNAME, NumberRangeResponseList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", name = "OperationCode", scope = NumberRangeResponseList.class)
    public JAXBElement<String> createNumberRangeResponseListOperationCode(String value) {
        return new JAXBElement<String>(_NumberRangeResponseListOperationCode_QNAME, String.class, NumberRangeResponseList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", name = "OperationDescription", scope = NumberRangeResponseList.class)
    public JAXBElement<String> createNumberRangeResponseListOperationDescription(String value) {
        return new JAXBElement<String>(_NumberRangeResponseListOperationDescription_QNAME, String.class, NumberRangeResponseList.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfNumberRangeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/NumberRangeResponseList", name = "ResponseList", scope = NumberRangeResponseList.class)
    public JAXBElement<ArrayOfNumberRangeResponse> createNumberRangeResponseListResponseList(ArrayOfNumberRangeResponse value) {
        return new JAXBElement<ArrayOfNumberRangeResponse>(_NumberRangeResponseListResponseList_QNAME, ArrayOfNumberRangeResponse.class, NumberRangeResponseList.class, value);
    }

}
