package zimbra.soap;

import auth.AuthenticationSource;
import auth.SimpleAuthenticationSource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import javax.xml.soap.SOAPException;
import mapper.Apply;
import soap.SOAPFactory;
import strings.AbstractStringSource;
import strings.ForkedString;
import util.Log;
import zimbra.ZimbraEmailException;

/**
 *
 * @author curt
 */
public final class ZimbraEmailChecker 
    extends AbstractStringSource
{

    final SOAPFactory soap;
    final URL baseUrl;
    final AuthenticationSource auth;

    private ZimbraEmailChecker(SOAPFactory soap, URL baseUrl, AuthenticationSource auth) {
        this.soap = soap;
        this.baseUrl = baseUrl;
        this.auth = auth;
    }
    
    public static Iterator<ForkedString> of(AuthenticationSource auth) {
        return Apply.to(
             new ZimbraEmailChecker(
                 new SOAPFactory(),newURL("https://zimbra.asolutions.com/"),auth),
             EmailDataToEmail.of());
    }
    
    @Override
    public ForkedString<EmailData> next() {
        try {
            return ForkedString.fromList(EmailData.class,getEmail());
        } catch (Exception e) {
            report(e);
            throw ZimbraEmailException.from(e);
        }
    }

    void report(Throwable t) {
        String message = t.getClass().getSimpleName() + ":" +t.getMessage();
        Log.info(message);
    }
    
    List<EmailData> getEmail()
            throws SOAPException, MalformedURLException, IOException {
        String account = auth.getUser();
        String password = new String(auth.getPassword());
        String authToken = new AuthTokenRequest(baseUrl,account,password,soap).getAuthToken();
        MailRequest checker = new MailRequest(baseUrl,authToken,soap);        
        return checker.getQueryData();
    }

    static URL newURL(String url) {
        try {
            return new URL("https://zimbra.asolutions.com/");
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(
                of(SimpleAuthenticationSource.of("", ""))
                .next());
    }
}
