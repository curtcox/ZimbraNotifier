package net;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Curt
 */
public final class ProxyURItoInputStream 
    implements URItoInputStream
{
    private ProxyURItoInputStream() {}
    
    public static ProxyURItoInputStream of() {
        return new ProxyURItoInputStream();    
    }
    
    @Override
    public InputStream getInputStream(URL url) throws IOException {
        try {
            return getInputStream(url,ProxyUtil.getProxies(url.toURI()));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    InputStream getInputStream(URL url, List<Proxy> proxies) throws IOException {
        IOException problem = new IOException("No proxies");
        for (Proxy proxy : proxies) {
            try {
                return url.openConnection(proxy).getInputStream();
            } catch (IOException e) {
                problem = e;
            }
        }
        throw problem;
    }

}
