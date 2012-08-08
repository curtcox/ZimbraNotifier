package net;

import java.io.*;
import java.net.*;

/**
 *
 * @author Curt
 */
public interface URItoInputStream {

    InputStream getInputStream(URL url) throws IOException;
    
}
