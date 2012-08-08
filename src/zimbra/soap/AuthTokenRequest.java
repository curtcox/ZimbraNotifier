package zimbra.soap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import soap.ISOAPFactory;
import util.Check;

/**
 *
 * @author curt
 */
final class AuthTokenRequest {

    private String authToken;
    private final URL baseUrl;
    private final String account;
    private final String password;
    private final ISOAPFactory soap;
     
    public static final String SOAP_URI = "/service/soap/";
    static final String ACCOUNT = "urn:zimbraAccount";
    static final String ZIMBRA  = "urn:zimbra";
    static final String MAIL    = "urn:zimbraMail";
 

    AuthTokenRequest(URL baseUrl, String account, String password, ISOAPFactory soap) {
        this.baseUrl = Check.notNull(baseUrl);
        this.account = Check.notNull(account);
        this.password = Check.notNull(password);
        this.soap = Check.notNull(soap);
    }

    SOAPEnvelope callForAuthToken() throws MalformedURLException, SOAPException, IOException {
        URL url = new URL(baseUrl, SOAP_URI);
        SOAPMessage message = createAuthTokenRequest();
        return soap.getResponseMessage(message, url).getSOAPPart().getEnvelope();
    }
    
    SOAPMessage createAuthTokenRequest() throws SOAPException {
        SOAPMessage message = soap.newInstance();
        SOAPBody body = message.getSOAPBody();
        SOAPElement authRequest = body.addChildElement("AuthRequest", "", ACCOUNT);
        SOAPElement requestAccount = authRequest.addChildElement("account", "");
            requestAccount.setAttribute("by", "name");
            requestAccount.addTextNode(account);
        SOAPElement requestPassword = authRequest.addChildElement("password", "");
            requestPassword.addTextNode(password);
        return message;
    }
        
    String processAuthTokenCall(SOAPEnvelope envelope) throws SOAPException {
        SOAPBody body = envelope.getBody();
        for (SOAPElement iter : Each.of(body.getChildElements(new QName(ACCOUNT, "AuthResponse")))) {
            for (SOAPElement element : Each.of(iter.getChildElements())) {
                if ("authToken".equals(element.getNodeName())) {
                    final String authTokenValue = element.getValue();
                    if (authTokenValue == null || authTokenValue.trim().length() == 0) {
                        throw new IllegalArgumentException("Wrong auth token returned: " + authTokenValue);
                    }
                    return authTokenValue;
                }
            }
        }
        throw new IllegalArgumentException("No auth token in body");
    }
   
    String getAuthToken() throws SOAPException, MalformedURLException, IOException {
        authToken = processAuthTokenCall(callForAuthToken());
        return authToken;
    }
}
