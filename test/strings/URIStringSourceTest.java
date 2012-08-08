package strings;

import net.URItoInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URI;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class URIStringSourceTest {
    
    final String EOL = URIStringSource.EOL;

    @Test
    public void next_returns_value_from_stream() throws Exception {
        String text = "http://example.com/";
        URL url = new URL(text);
        URItoInputStream uriStreamer = new MockURItoInputStream();
        URIStringSource source = new URIStringSource(url,uriStreamer);
        
        ForkedString forked = source.next();
        
        assertEquals(text + EOL,forked.string);
    }

    @Test
    public void readNext_returns_value_from_input_stream() throws Exception {
        String text = "blah blah blah";
        InputStream in = new ByteArrayInputStream(text.getBytes());
        ForkedString forked = URIStringSource.readNext(in);
        
        assertEquals(text + EOL,forked.string);
    }

    @Test
    public void testSupports() {
        assertTrue(URIStringSource.supports("http://www.google.com/"));
        assertTrue(URIStringSource.supports("https://www.google.com/"));
        assertFalse(URIStringSource.supports("unsupported://www.google.com/"));
    }

    @Test
    public void url_returns_url() throws Exception {
        String google = "http://www.google.com/";
        assertEquals(new URL(google), URIStringSource.url(google));
    }

    @Test
    public void uri_returns_uri() throws Exception {
        String google = "http://www.google.com/";
        assertEquals(new URI(google), URIStringSource.uri(google));
    }
}
