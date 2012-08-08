package zimbra.rss;



import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import strings.ForkedString;

import org.junit.Test;
import zimbra.ZimbraEmail;


public class ZimbraRssToZimbraEmailsTest {

    final ZimbraRssToZimbraEmails converter = ZimbraRssToZimbraEmails.of();

    static final ForkedString SIMPLE_1 = ForkedString.forked( 
        "<?xml version='1.0'?>",
        "<rss version='2.0'>",
          "<channel>",
               "<title>title</title>",
               "<link>http://www.zimbra.com</link>",
               "<description>description</description>",
               "<generator>generator</generator>",
               "<item>",
                   "<title>title</title>",
                   "<description>desc</description>" ,
                   "<author>auth</author>" ,
                   "<pubDate>Tue, 23 Aug 2011 14:02:08 -0500</pubDate>" ,
               "</item>" ,
                "</channel>" ,
           "</rss>" 
        );  	

    @Test
    public void empty_string_converts() {
        ForkedString expected = ForkedString.EMPTY;
        ForkedString actual = converter.transform(ForkedString.EMPTY);

        assertEquals(expected,actual);
    }
    
    @Test
    public void simple_one_item_converts() {
        String author = "auth";
        String title = "title";
        long date = 1314126128000L;
        String description = "desc";
        ZimbraEmail email = ZimbraEmail.of(author,title,date,description);
        List<?> list = Arrays.asList(email);
        String string = list.toString();
        ForkedString expected = ForkedString.of(string,list,ZimbraEmail.class);

        ForkedString actual = converter.transform(SIMPLE_1);

        assertEquals(expected.parts.get(0),actual.parts.get(0));
    }

    @Test
    public void real_sample_converts() {
        List<?> list = Arrays.asList(
            email(
                "jrebel@zeroturnaround.com",
                "JRebel takes off, lands on more expensive planet",
                "Fri, 19 Aug 2011 07:17:06 -0500",
                "Hey Curt, Just wanted to remind you that in only a few short weeks, JRebel is going through that price change we announced last month. Since that ..."),
            email(
                "'Jenna Dixon' &lt;jenna.dixon@asolutions.com&gt;",
                "Andi at Seibert tomorrow (8/24/11)",
                "Tue, 23 Aug 2011 14:02:08 -0500",
                "Hey guys, Tomorrow Andi will be here from 11am-2:45pm. Please stop by if you have any question for her J If I don't see you - have a great weekend! ...")		    
        );
        String string = list.toString();
        ForkedString expected = ForkedString.of(string,list,ZimbraEmail.class);

        ForkedString actual = converter.transform(ZimbraRssXmlSample.SAMPLE);

        assertEquals(expected.parts.get(0),actual.parts.get(0));
    }
    
    static ZimbraEmail email(String author, String title, String date, String description) {
        long when = ZimbraEmailHandler.parseDate(date);
        return ZimbraEmail.of(author,title,when,description);
    }



}
