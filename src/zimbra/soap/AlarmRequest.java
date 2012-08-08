package zimbra.soap;

import util.Check;
import soap.ISOAPFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 *
 * @author curt
 */
final class AlarmRequest {

    private final URL baseUrl;
    private final String authToken;
    private final ISOAPFactory soap;
     
    static final String SOAP_URI = "/service/soap/";
    static final String ACCOUNT = "urn:zimbraAccount";
    static final String ZIMBRA  = "urn:zimbra";
    static final String BATCH_REQUEST = "BatchRequest";
    static final String QUERY = "query";
    static final String SEARCH_REQUEST = "SearchRequest";
    static final String ALARMDATA = "alarmData";

    public AlarmRequest(URL baseUrl, String authToken, ISOAPFactory soap) {
        this.baseUrl = Check.notNull(baseUrl);
        this.authToken = Check.notNull(authToken);
        this.soap = Check.notNull(soap);
    }
   
    List<AlarmData> getQueryData() throws SOAPException, MalformedURLException, IOException {
        return processQueryCall(callForQuery(authToken));
    }
 
    SOAPEnvelope callForQuery(String authToken) throws IOException, SOAPException {
        final SOAPMessage message = soap.newInstance();
        createQueryRequest(batchElement(message,authToken));
        URL url = new URL(baseUrl, SOAP_URI);
        return soap.getResponseMessage(message,url).getSOAPPart().getEnvelope();
    }
    
    void createQueryRequest(SOAPElement batch) throws SOAPException {
        addQueryForAppointments(batch);
    }

    SOAPElement batchElement(SOAPMessage message, String authTokenString) throws SOAPException {
        message.getSOAPHeader()
              .addChildElement("context", "", ZIMBRA)
              .addChildElement("authToken", "")
                  .addTextNode(authTokenString);
        return message.getSOAPBody()
                .addChildElement(BATCH_REQUEST, "", ZIMBRA);
    }
    
    static void addQueryForAppointments(SOAPElement batchElement) throws SOAPException {
        long now = System.currentTimeMillis();
        long ms_per_day = 86400000;
        String _7_days_ago = Long.toString(now - 7 * ms_per_day);
        String _1_day_from_now = Long.toString(now + 1 * ms_per_day);
        SOAPElement search = batchElement.addChildElement(SEARCH_REQUEST, "", ALARMDATA);
            search.setAttribute("types", "appointment");
            search.setAttribute("sortBy", "dateAsc");
            search.setAttribute("calExpandInstStart", _7_days_ago);
            search.setAttribute("calExpandInstEnd", _1_day_from_now);
        
            search.addChildElement(QUERY, "")
                .addTextNode("inid:10");
    }
    
    List<AlarmData> processQueryCall(SOAPEnvelope envelope) throws SOAPException {
        List<AlarmData> list = new ArrayList<AlarmData>();
        SOAPBody body = envelope.getBody();
        for (SOAPElement batch : Each.of(body.getChildElements(new QName(ZIMBRA, "BatchResponse")))){
            for (SOAPElement search : Each.of(batch.getChildElements(new QName(ALARMDATA, "SearchResponse")))) {
                for (SOAPElement child : Each.of(search.getChildElements())) {
                    if ("appt".equals(child.getNodeName())) {
                        list.addAll(getAlarmData(child));
                    }
                }
            }
        }
        return list;
    }

    static List<AlarmData> getAlarmData(SOAPElement element) {
        List<AlarmData> list = new ArrayList<AlarmData>();
        for (SOAPElement child : Each.of(element.getChildElements())) {
            if (ALARMDATA.equals(child.getNodeName())) {
                list.add(new AlarmData(child));
            }
        }
        return list;
    }
    
}
