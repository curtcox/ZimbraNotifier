package net;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

/**
*/
final class ProxyUtil {

    static final Logger LOG = Logger.getLogger(ProxyUtil.class.getName());
    
    static List<Proxy> getProxies(URI uri) {
        set("java.net.useSystemProxies", "true");
        print("detecting proxies");
        return ProxySelector.getDefault().select(uri);
    }

    static void print(String message) {
        LOG.fine(message);
    }

    static void set(String key, String value) {
        System.setProperty(key,value);
    }
    
}
