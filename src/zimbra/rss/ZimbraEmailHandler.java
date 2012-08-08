package zimbra.rss;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import zimbra.ZimbraEmail;

final class ZimbraEmailHandler 
    extends DefaultHandler
{
    static final String ITEM = normalize("item");
    static final String AUTHOR = normalize("author");
    static final String TITLE = normalize("title");
    static final String PUBDATE = normalize("pubdate");
    static final String DESCRIPTION = normalize("description");

    List<ZimbraEmail> list = Collections.checkedList(new ArrayList<ZimbraEmail>(),ZimbraEmail.class);
    String field;
    Map<String,String> values = new HashMap<String,String>();

    @Override
    public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
        field = normalize(qName);    
    }

    static String normalize(String string) {
        return string.trim().toLowerCase();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (ITEM.equalsIgnoreCase(qName)) {
            list.add(ZimbraEmail.of(
                values.get(AUTHOR),
                values.get(TITLE),
                parseDate(values.get(PUBDATE)),
                values.get(DESCRIPTION)
            ));
        }
    }

    static final DateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    static long parseDate(String string) {
        if (string==null || string.trim().isEmpty()) {
            return 0;
        }
        try {
            return format.parse(string).getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        values.put(field,value);
    }

    public List<ZimbraEmail> getEmails() {
        return list;
    }
}