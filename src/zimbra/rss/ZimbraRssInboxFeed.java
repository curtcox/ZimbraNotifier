package zimbra.rss;

import auth.AuthenticationSource;
import java.util.Iterator;
import mapper.Apply;
import net.ProxyURItoInputStream;
import net.URItoInputStream;
import strings.ForkedString;
import strings.StringSource;

/**
 *
 * @author Curt
 */
public final class ZimbraRssInboxFeed {

    static final String ZIMBRA_SERVER = "https://zimbra.asolutions.com/";

    static Iterator<ForkedString> inboxFeed(String user, URItoInputStream streamer, AuthenticationSource auth) {
        return StringSource.of(ZIMBRA_SERVER + "home/" + user +"/inbox.rss?fmtl=html",streamer,auth);
    }

    public static Iterator<ForkedString> of (AuthenticationSource auth) {
        return Apply.to(
                inboxFeed(auth.getUser(),ProxyURItoInputStream.of(),auth),
                ZimbraRssToZimbraEmails.of());
    }

}
