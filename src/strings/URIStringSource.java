package strings;

import auth.AuthenticationSource;
import net.URItoInputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.*;

/**
 * Produces a sequence of StringS from a URI. 
 */
final class URIStringSource
    extends AbstractStringSource
{

    final URL url;
    final URItoInputStream uriStreamer;
    static final String EOL = "\r\n";
	
    URIStringSource(URL url, URItoInputStream uriStreamer) {
        this.url = url;
        this.uriStreamer = uriStreamer;
    }

    @Override
    public ForkedString next() {
        try {
            return readNext(uriStreamer.getInputStream(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static ForkedString readNext(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder out = new StringBuilder();
        String inputLine;

        List<String> lines = new ArrayList<String>();
        while ((inputLine = reader.readLine()) != null) {
            out.append(inputLine + EOL);
            lines.add(inputLine);
        }
        in.close();
        return ForkedString.of(out.toString(),lines,String.class);
    }
    
    static boolean supports(String source) {
        return source.startsWith("http://") || source.startsWith("https://");
    }

    public static Iterator<ForkedString> parse(
        String source, AuthenticationSource auth, URItoInputStream streamer) {
        URL url = url(source);
        return AuthenticatedStringSource.of(new URIStringSource(url,streamer),auth);
    }

    static URL url(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    static URI uri(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
