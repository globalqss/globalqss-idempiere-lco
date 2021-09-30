
package colombia.dian.wcf;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import org.datacontract.schemas._2004._07.dianresponse.ArrayOfDianResponse;
import org.datacontract.schemas._2004._07.dianresponse.DianResponse;
import org.datacontract.schemas._2004._07.eventresponse.ArrayOfEventResponse;
import org.datacontract.schemas._2004._07.eventresponse.EventResponse;
import org.datacontract.schemas._2004._07.exchangeemailresponse.ExchangeEmailResponse;
import org.datacontract.schemas._2004._07.numberrangeresponselist.NumberRangeResponseList;
import org.datacontract.schemas._2004._07.uploaddocumentresponse.UploadDocumentResponse;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the colombia.dian.wcf package. 
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

    private final static QName _GetExchangeEmailsResponseGetExchangeEmailsResult_QNAME = new QName("http://wcf.dian.colombia", "GetExchangeEmailsResult");
    private final static QName _GetStatusTrackId_QNAME = new QName("http://wcf.dian.colombia", "trackId");
    private final static QName _GetStatusResponseGetStatusResult_QNAME = new QName("http://wcf.dian.colombia", "GetStatusResult");
    private final static QName _GetStatusZipResponseGetStatusZipResult_QNAME = new QName("http://wcf.dian.colombia", "GetStatusZipResult");
    private final static QName _SendBillAsyncFileName_QNAME = new QName("http://wcf.dian.colombia", "fileName");
    private final static QName _SendBillAsyncContentFile_QNAME = new QName("http://wcf.dian.colombia", "contentFile");
    private final static QName _SendBillAsyncResponseSendBillAsyncResult_QNAME = new QName("http://wcf.dian.colombia", "SendBillAsyncResult");
    private final static QName _SendTestSetAsyncTestSetId_QNAME = new QName("http://wcf.dian.colombia", "testSetId");
    private final static QName _SendTestSetAsyncResponseSendTestSetAsyncResult_QNAME = new QName("http://wcf.dian.colombia", "SendTestSetAsyncResult");
    private final static QName _SendBillSyncResponseSendBillSyncResult_QNAME = new QName("http://wcf.dian.colombia", "SendBillSyncResult");
    private final static QName _SendBillAttachmentAsyncResponseSendBillAttachmentAsyncResult_QNAME = new QName("http://wcf.dian.colombia", "SendBillAttachmentAsyncResult");
    private final static QName _SendEventUpdateStatusResponseSendEventUpdateStatusResult_QNAME = new QName("http://wcf.dian.colombia", "SendEventUpdateStatusResult");
    private final static QName _GetNumberingRangeAccountCode_QNAME = new QName("http://wcf.dian.colombia", "accountCode");
    private final static QName _GetNumberingRangeAccountCodeT_QNAME = new QName("http://wcf.dian.colombia", "accountCodeT");
    private final static QName _GetNumberingRangeSoftwareCode_QNAME = new QName("http://wcf.dian.colombia", "softwareCode");
    private final static QName _GetNumberingRangeResponseGetNumberingRangeResult_QNAME = new QName("http://wcf.dian.colombia", "GetNumberingRangeResult");
    private final static QName _GetXmlByDocumentKeyResponseGetXmlByDocumentKeyResult_QNAME = new QName("http://wcf.dian.colombia", "GetXmlByDocumentKeyResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: colombia.dian.wcf
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetExchangeEmails }
     * 
     */
    public GetExchangeEmails createGetExchangeEmails() {
        return new GetExchangeEmails();
    }

    /**
     * Create an instance of {@link GetExchangeEmailsResponse }
     * 
     */
    public GetExchangeEmailsResponse createGetExchangeEmailsResponse() {
        return new GetExchangeEmailsResponse();
    }

    /**
     * Create an instance of {@link GetStatus }
     * 
     */
    public GetStatus createGetStatus() {
        return new GetStatus();
    }

    /**
     * Create an instance of {@link GetStatusResponse }
     * 
     */
    public GetStatusResponse createGetStatusResponse() {
        return new GetStatusResponse();
    }

    /**
     * Create an instance of {@link GetStatusZip }
     * 
     */
    public GetStatusZip createGetStatusZip() {
        return new GetStatusZip();
    }

    /**
     * Create an instance of {@link GetStatusZipResponse }
     * 
     */
    public GetStatusZipResponse createGetStatusZipResponse() {
        return new GetStatusZipResponse();
    }

    /**
     * Create an instance of {@link SendBillAsync }
     * 
     */
    public SendBillAsync createSendBillAsync() {
        return new SendBillAsync();
    }

    /**
     * Create an instance of {@link SendBillAsyncResponse }
     * 
     */
    public SendBillAsyncResponse createSendBillAsyncResponse() {
        return new SendBillAsyncResponse();
    }

    /**
     * Create an instance of {@link SendTestSetAsync }
     * 
     */
    public SendTestSetAsync createSendTestSetAsync() {
        return new SendTestSetAsync();
    }

    /**
     * Create an instance of {@link SendTestSetAsyncResponse }
     * 
     */
    public SendTestSetAsyncResponse createSendTestSetAsyncResponse() {
        return new SendTestSetAsyncResponse();
    }

    /**
     * Create an instance of {@link SendBillSync }
     * 
     */
    public SendBillSync createSendBillSync() {
        return new SendBillSync();
    }

    /**
     * Create an instance of {@link SendBillSyncResponse }
     * 
     */
    public SendBillSyncResponse createSendBillSyncResponse() {
        return new SendBillSyncResponse();
    }

    /**
     * Create an instance of {@link SendBillAttachmentAsync }
     * 
     */
    public SendBillAttachmentAsync createSendBillAttachmentAsync() {
        return new SendBillAttachmentAsync();
    }

    /**
     * Create an instance of {@link SendBillAttachmentAsyncResponse }
     * 
     */
    public SendBillAttachmentAsyncResponse createSendBillAttachmentAsyncResponse() {
        return new SendBillAttachmentAsyncResponse();
    }

    /**
     * Create an instance of {@link SendEventUpdateStatus }
     * 
     */
    public SendEventUpdateStatus createSendEventUpdateStatus() {
        return new SendEventUpdateStatus();
    }

    /**
     * Create an instance of {@link SendEventUpdateStatusResponse }
     * 
     */
    public SendEventUpdateStatusResponse createSendEventUpdateStatusResponse() {
        return new SendEventUpdateStatusResponse();
    }

    /**
     * Create an instance of {@link GetNumberingRange }
     * 
     */
    public GetNumberingRange createGetNumberingRange() {
        return new GetNumberingRange();
    }

    /**
     * Create an instance of {@link GetNumberingRangeResponse }
     * 
     */
    public GetNumberingRangeResponse createGetNumberingRangeResponse() {
        return new GetNumberingRangeResponse();
    }

    /**
     * Create an instance of {@link GetXmlByDocumentKey }
     * 
     */
    public GetXmlByDocumentKey createGetXmlByDocumentKey() {
        return new GetXmlByDocumentKey();
    }

    /**
     * Create an instance of {@link GetXmlByDocumentKeyResponse }
     * 
     */
    public GetXmlByDocumentKeyResponse createGetXmlByDocumentKeyResponse() {
        return new GetXmlByDocumentKeyResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ExchangeEmailResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "GetExchangeEmailsResult", scope = GetExchangeEmailsResponse.class)
    public JAXBElement<ExchangeEmailResponse> createGetExchangeEmailsResponseGetExchangeEmailsResult(ExchangeEmailResponse value) {
        return new JAXBElement<ExchangeEmailResponse>(_GetExchangeEmailsResponseGetExchangeEmailsResult_QNAME, ExchangeEmailResponse.class, GetExchangeEmailsResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "trackId", scope = GetStatus.class)
    public JAXBElement<String> createGetStatusTrackId(String value) {
        return new JAXBElement<String>(_GetStatusTrackId_QNAME, String.class, GetStatus.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "GetStatusResult", scope = GetStatusResponse.class)
    public JAXBElement<DianResponse> createGetStatusResponseGetStatusResult(DianResponse value) {
        return new JAXBElement<DianResponse>(_GetStatusResponseGetStatusResult_QNAME, DianResponse.class, GetStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "trackId", scope = GetStatusZip.class)
    public JAXBElement<String> createGetStatusZipTrackId(String value) {
        return new JAXBElement<String>(_GetStatusTrackId_QNAME, String.class, GetStatusZip.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfDianResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfDianResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "GetStatusZipResult", scope = GetStatusZipResponse.class)
    public JAXBElement<ArrayOfDianResponse> createGetStatusZipResponseGetStatusZipResult(ArrayOfDianResponse value) {
        return new JAXBElement<ArrayOfDianResponse>(_GetStatusZipResponseGetStatusZipResult_QNAME, ArrayOfDianResponse.class, GetStatusZipResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "fileName", scope = SendBillAsync.class)
    public JAXBElement<String> createSendBillAsyncFileName(String value) {
        return new JAXBElement<String>(_SendBillAsyncFileName_QNAME, String.class, SendBillAsync.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "contentFile", scope = SendBillAsync.class)
    public JAXBElement<byte[]> createSendBillAsyncContentFile(byte[] value) {
        return new JAXBElement<byte[]>(_SendBillAsyncContentFile_QNAME, byte[].class, SendBillAsync.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "SendBillAsyncResult", scope = SendBillAsyncResponse.class)
    public JAXBElement<UploadDocumentResponse> createSendBillAsyncResponseSendBillAsyncResult(UploadDocumentResponse value) {
        return new JAXBElement<UploadDocumentResponse>(_SendBillAsyncResponseSendBillAsyncResult_QNAME, UploadDocumentResponse.class, SendBillAsyncResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "fileName", scope = SendTestSetAsync.class)
    public JAXBElement<String> createSendTestSetAsyncFileName(String value) {
        return new JAXBElement<String>(_SendBillAsyncFileName_QNAME, String.class, SendTestSetAsync.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "contentFile", scope = SendTestSetAsync.class)
    public JAXBElement<byte[]> createSendTestSetAsyncContentFile(byte[] value) {
        return new JAXBElement<byte[]>(_SendBillAsyncContentFile_QNAME, byte[].class, SendTestSetAsync.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "testSetId", scope = SendTestSetAsync.class)
    public JAXBElement<String> createSendTestSetAsyncTestSetId(String value) {
        return new JAXBElement<String>(_SendTestSetAsyncTestSetId_QNAME, String.class, SendTestSetAsync.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "SendTestSetAsyncResult", scope = SendTestSetAsyncResponse.class)
    public JAXBElement<UploadDocumentResponse> createSendTestSetAsyncResponseSendTestSetAsyncResult(UploadDocumentResponse value) {
        return new JAXBElement<UploadDocumentResponse>(_SendTestSetAsyncResponseSendTestSetAsyncResult_QNAME, UploadDocumentResponse.class, SendTestSetAsyncResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "fileName", scope = SendBillSync.class)
    public JAXBElement<String> createSendBillSyncFileName(String value) {
        return new JAXBElement<String>(_SendBillAsyncFileName_QNAME, String.class, SendBillSync.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "contentFile", scope = SendBillSync.class)
    public JAXBElement<byte[]> createSendBillSyncContentFile(byte[] value) {
        return new JAXBElement<byte[]>(_SendBillAsyncContentFile_QNAME, byte[].class, SendBillSync.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DianResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "SendBillSyncResult", scope = SendBillSyncResponse.class)
    public JAXBElement<DianResponse> createSendBillSyncResponseSendBillSyncResult(DianResponse value) {
        return new JAXBElement<DianResponse>(_SendBillSyncResponseSendBillSyncResult_QNAME, DianResponse.class, SendBillSyncResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "fileName", scope = SendBillAttachmentAsync.class)
    public JAXBElement<String> createSendBillAttachmentAsyncFileName(String value) {
        return new JAXBElement<String>(_SendBillAsyncFileName_QNAME, String.class, SendBillAttachmentAsync.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "contentFile", scope = SendBillAttachmentAsync.class)
    public JAXBElement<byte[]> createSendBillAttachmentAsyncContentFile(byte[] value) {
        return new JAXBElement<byte[]>(_SendBillAsyncContentFile_QNAME, byte[].class, SendBillAttachmentAsync.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UploadDocumentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "SendBillAttachmentAsyncResult", scope = SendBillAttachmentAsyncResponse.class)
    public JAXBElement<UploadDocumentResponse> createSendBillAttachmentAsyncResponseSendBillAttachmentAsyncResult(UploadDocumentResponse value) {
        return new JAXBElement<UploadDocumentResponse>(_SendBillAttachmentAsyncResponseSendBillAttachmentAsyncResult_QNAME, UploadDocumentResponse.class, SendBillAttachmentAsyncResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link byte[]}{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "contentFile", scope = SendEventUpdateStatus.class)
    public JAXBElement<byte[]> createSendEventUpdateStatusContentFile(byte[] value) {
        return new JAXBElement<byte[]>(_SendBillAsyncContentFile_QNAME, byte[].class, SendEventUpdateStatus.class, ((byte[]) value));
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ArrayOfEventResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "SendEventUpdateStatusResult", scope = SendEventUpdateStatusResponse.class)
    public JAXBElement<ArrayOfEventResponse> createSendEventUpdateStatusResponseSendEventUpdateStatusResult(ArrayOfEventResponse value) {
        return new JAXBElement<ArrayOfEventResponse>(_SendEventUpdateStatusResponseSendEventUpdateStatusResult_QNAME, ArrayOfEventResponse.class, SendEventUpdateStatusResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "accountCode", scope = GetNumberingRange.class)
    public JAXBElement<String> createGetNumberingRangeAccountCode(String value) {
        return new JAXBElement<String>(_GetNumberingRangeAccountCode_QNAME, String.class, GetNumberingRange.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "accountCodeT", scope = GetNumberingRange.class)
    public JAXBElement<String> createGetNumberingRangeAccountCodeT(String value) {
        return new JAXBElement<String>(_GetNumberingRangeAccountCodeT_QNAME, String.class, GetNumberingRange.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "softwareCode", scope = GetNumberingRange.class)
    public JAXBElement<String> createGetNumberingRangeSoftwareCode(String value) {
        return new JAXBElement<String>(_GetNumberingRangeSoftwareCode_QNAME, String.class, GetNumberingRange.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link NumberRangeResponseList }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "GetNumberingRangeResult", scope = GetNumberingRangeResponse.class)
    public JAXBElement<NumberRangeResponseList> createGetNumberingRangeResponseGetNumberingRangeResult(NumberRangeResponseList value) {
        return new JAXBElement<NumberRangeResponseList>(_GetNumberingRangeResponseGetNumberingRangeResult_QNAME, NumberRangeResponseList.class, GetNumberingRangeResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link String }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "trackId", scope = GetXmlByDocumentKey.class)
    public JAXBElement<String> createGetXmlByDocumentKeyTrackId(String value) {
        return new JAXBElement<String>(_GetStatusTrackId_QNAME, String.class, GetXmlByDocumentKey.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EventResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link EventResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://wcf.dian.colombia", name = "GetXmlByDocumentKeyResult", scope = GetXmlByDocumentKeyResponse.class)
    public JAXBElement<EventResponse> createGetXmlByDocumentKeyResponseGetXmlByDocumentKeyResult(EventResponse value) {
        return new JAXBElement<EventResponse>(_GetXmlByDocumentKeyResponseGetXmlByDocumentKeyResult_QNAME, EventResponse.class, GetXmlByDocumentKeyResponse.class, value);
    }

}
