package zimbra.rss;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import mapper.StringMapper;
import org.xml.sax.SAXException;
import strings.ForkedString;
import zimbra.ZimbraEmail;

public final class ZimbraRssToZimbraEmails
    implements StringMapper
{

    public static ZimbraRssToZimbraEmails of() {
        return new ZimbraRssToZimbraEmails();
    }

    @Override
    public ForkedString<ZimbraEmail> transform(ForkedString forked) {
        if (forked.string.isEmpty()) {
            return forked;
        }
        try {
            InputStream in = new ByteArrayInputStream(forked.toString().getBytes("UTF-8"));
            List<ZimbraEmail> parts = parse(in);
            String string = parts.toString();
            return ForkedString.of(string,parts,ZimbraEmail.class);
        } catch (Exception e) {
            String message = "transforming <<<" + forked + ">>>";
            throw new RuntimeException(message,e);
        }
    }

 
   List<ZimbraEmail> parse(InputStream in) throws SAXException, IOException, ParserConfigurationException {
      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();
      ZimbraEmailHandler handler = new ZimbraEmailHandler();
      saxParser.parse(in, handler);
      return handler.getEmails();
   }
 
}