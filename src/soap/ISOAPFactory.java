package soap;
import java.io.*;
import java.net.URL;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public interface ISOAPFactory {

    SOAPMessage newInstance() throws SOAPException;
    SOAPMessage getResponseMessage(SOAPMessage request, URL url) throws IOException, SOAPException;
    InputStream getResponseStream(SOAPMessage request, URL url) throws IOException, SOAPException;

}
