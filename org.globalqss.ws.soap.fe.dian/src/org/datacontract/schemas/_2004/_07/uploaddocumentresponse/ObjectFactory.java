
package org.datacontract.schemas._2004._07.uploaddocumentresponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.xmlparamsresponsetrackid.ArrayOfXmlParamsResponseTrackId;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.datacontract.schemas._2004._07.uploaddocumentresponse package. 
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

    private final static QName _UploadDocumentResponse_QNAME = new QName("http://schemas.datacontract.org/2004/07/UploadDocumentResponse", "UploadDocumentResponse");
    private final static QName _UploadDocumentResponseErrorMessageList_QNAME = new QName("http://schemas.datacontract.org/2004/07/UploadDocumentResponse", "ErrorMessageList");
    private final static QName _UploadDocumentResponseZipKey_QNAME = new QName("http://schemas.datacontract.org/2004/07/UploadDocumentResponse", "ZipKey");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.datacontract.schemas._2004._07.uploaddocumentresponse
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UploadDocumentResponse }
     * 
     */
    public UploadDocumentResponse createUploadDocumentResponse() {
        return new UploadDocumentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/UploadDocumentResponse", name = "UploadDocumentResponse")
    public JAXBElement<UploadDocumentResponse> createUploadDocumentResponse(UploadDocumentResponse value) {
        return new JAXBElement<UploadDocumentResponse>(_UploadDocumentResponse_QNAME, UploadDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfXmlParamsResponseTrackId }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/UploadDocumentResponse", name = "ErrorMessageList", scope = UploadDocumentResponse.class)
    public JAXBElement<ArrayOfXmlParamsResponseTrackId> createUploadDocumentResponseErrorMessageList(ArrayOfXmlParamsResponseTrackId value) {
        return new JAXBElement<ArrayOfXmlParamsResponseTrackId>(_UploadDocumentResponseErrorMessageList_QNAME, ArrayOfXmlParamsResponseTrackId.class, UploadDocumentResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://schemas.datacontract.org/2004/07/UploadDocumentResponse", name = "ZipKey", scope = UploadDocumentResponse.class)
    public JAXBElement<String> createUploadDocumentResponseZipKey(String value) {
        return new JAXBElement<String>(_UploadDocumentResponseZipKey_QNAME, String.class, UploadDocumentResponse.class, value);
    }

}
