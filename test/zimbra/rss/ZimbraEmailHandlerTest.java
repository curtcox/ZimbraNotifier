package zimbra.rss;

import zimbra.rss.ZimbraEmailHandler;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class ZimbraEmailHandlerTest {
	
    @Test
    public void pubDate_converts_to_long_and_back() {
        String expected = "Tue, 23 Aug 2011 14:02:08 -0500";
        long millis = ZimbraEmailHandler.parseDate(expected);
        String actual = ZimbraEmailHandler.format.format(new Date(millis));

        assertEquals(expected,actual);
    }

}
