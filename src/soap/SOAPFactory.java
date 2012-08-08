package soap;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;


public final class SOAPFactory
    implements ISOAPFactory
{

    @Override
    public SOAPMessage newInstance() throws SOAPException {
        return MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL)
            .createMessage();
    }

    @Override
    public SOAPMessage getResponseMessage(SOAPMessage request, URL url) throws IOException, SOAPException {
        return MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL)
            .createMessage(null, getResponseStream(request,url));
    }

    @Override
    public InputStream getResponseStream(SOAPMessage request, URL url) throws IOException, SOAPException {
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        request.writeTo(conn.getOutputStream());
        return new BufferedInputStream(conn.getInputStream());
    }

}
