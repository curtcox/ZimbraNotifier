package zimbra.soap;

import util.Check;
import soap.ISOAPFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author curt
 */
final class MailRequest {

    private final URL baseUrl;
    private final String authToken;
    private final ISOAPFactory soap;
     
    static final String SOAP_URI = "/service/soap/";
    static final String ACCOUNT = "urn:zimbraAccount";
    static final String ZIMBRA  = "urn:zimbra";
    static final String MAIL    = "urn:zimbraMail";
    static final String BATCH_REQUEST = "BatchRequest";
    static final String QUERY = "query";
    static final String SEARCH_REQUEST = "SearchRequest";

    public MailRequest(URL baseUrl, String authToken, ISOAPFactory soap) {
        this.baseUrl = Check.notNull(baseUrl);
        this.authToken = Check.notNull(authToken);
        this.soap = Check.notNull(soap);
    }
   
    List<EmailData> getQueryData() throws SOAPException, MalformedURLException, IOException {
        return processQueryCall(callForQuery(authToken));
    }
 
    SOAPEnvelope callForQuery(String authToken) throws IOException, SOAPException {
        final SOAPMessage message = soap.newInstance();
        createQueryRequest(batchElement(message,authToken));
        URL url = new URL(baseUrl, SOAP_URI);
        return soap.getResponseMessage(message,url).getSOAPPart().getEnvelope();
    }
    
    void createQueryRequest(SOAPElement batch) throws SOAPException {
        addQueryForUnreadMail(batch);
    }

    SOAPElement batchElement(SOAPMessage message, String authTokenString) throws SOAPException {
        message.getSOAPHeader()
              .addChildElement("context", "", ZIMBRA)
              .addChildElement("authToken", "")
                  .addTextNode(authTokenString);
        return message.getSOAPBody()
                .addChildElement(BATCH_REQUEST, "", ZIMBRA);
    }
    
    static void addQueryForUnreadMail(SOAPElement batch) throws SOAPException {
        batch.addChildElement(SEARCH_REQUEST, "", MAIL)
            .addChildElement(QUERY, "")
                .addTextNode("is:unread");
    }
    
    List<EmailData> processQueryCall(SOAPEnvelope envelope) throws SOAPException {
        List<EmailData> list = new ArrayList<EmailData>();
        SOAPBody body = envelope.getBody();
        list.addAll(getMessageData(body));
        return list;
    }

    static List<EmailData> getMessageData(SOAPElement element) {
        List<EmailData> list = new ArrayList<EmailData>();
        for (SOAPElement child : Each.of(element.getChildElements())) {
            list.add(EmailData.of(child));
            list.addAll(getMessageData(child));
        }
        return list;
    }
    
}
