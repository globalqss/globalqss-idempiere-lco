
package org.datacontract.schemas._2004._07.eventresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.eventresponse package. 
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

    private final static QName _ArrayOfEventResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/EventResponse", "ArrayOfEventResponse");
    private final static QName _EventResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/EventResponse", "EventResponse");
    private final static QName _EventResponseCode_QNAME = new QName("http://schemas.datacontract.org/2004/07/EventResponse", "Code");
    private final static QName _EventResponseMessage_QNAME = new QName("http://schemas.datacontract.org/2004/07/EventResponse", "Message");
    private final static QName _EventResponseXmlBytesBase64_QNAME = new QName("http://schemas.datacontract.org/2004/07/EventResponse", "XmlBytesBase64");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.eventresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ArrayOfEventResponse }
     * 
     */
    public ArrayOfEventResponse createArrayOfEventResponse() {
        return new ArrayOfEventResponse();
    }

    /**
     * Create an instance of {@link EventResponse }
     * 
     */
    public EventResponse createEventResponse() {
        return new EventResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/EventResponse", name = "ArrayOfEventResponse")
    public JAXBElement<ArrayOfEventResponse> createArrayOfEventResponse(ArrayOfEventResponse value) {
        return new JAXBElement<ArrayOfEventResponse>(_ArrayOfEventResponse_QNAME, ArrayOfEventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/EventResponse", name = "EventResponse")
    public JAXBElement<EventResponse> createEventResponse(EventResponse value) {
        return new JAXBElement<EventResponse>(_EventResponse_QNAME, EventResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/EventResponse", name = "Code", scope = EventResponse.class)
    public JAXBElement<String> createEventResponseCode(String value) {
        return new JAXBElement<String>(_EventResponseCode_QNAME, String.class, EventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/EventResponse", name = "Message", scope = EventResponse.class)
    public JAXBElement<String> createEventResponseMessage(String value) {
        return new JAXBElement<String>(_EventResponseMessage_QNAME, String.class, EventResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/EventResponse", name = "XmlBytesBase64", scope = EventResponse.class)
    public JAXBElement<String> createEventResponseXmlBytesBase64(String value) {
        return new JAXBElement<String>(_EventResponseXmlBytesBase64_QNAME, String.class, EventResponse.class, value);
    }

}
