package strings;

import net.URItoInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author Curt
 */
public final class MockURItoInputStream implements URItoInputStream {
    
    public URL url;

    @Override
    public InputStream getInputStream(URL url) throws IOException {
        this.url = url;
        return new ByteArrayInputStream(url.toString().getBytes());
    }
    
}
