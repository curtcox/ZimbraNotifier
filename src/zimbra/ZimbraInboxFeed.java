package zimbra;

import auth.AuthenticationSource;
import java.util.Iterator;
import login.ILoginDialog;
import prefs.Preferences;
import strings.AbstractStringSource;
import strings.ForkedString;
import strings.SynchronizedStringSource;
import util.Check;
import util.Log;
import zimbra.soap.ZimbraEmailChecker;

/**
 * Supplies new inbox contents with every request.
 * @author Curt
 */
final class ZimbraInboxFeed
    extends AbstractStringSource
{
    boolean authFailedLastTime;
    long lastAuthenticated = System.currentTimeMillis();
    final Preferences prefs;
    final AuthenticationSource auth;
    ????
    private ZimbraInboxFeed(Preferences prefs, AuthenticationSource auth) {
        this.prefs = prefs;
        this.auth = auth;
    }

    static Iterator<ForkedString> of(ILoginDialog dialog) {
        return Check.isNotEDT(
               SynchronizedStringSource.of(
               new ZimbraInboxFeed(dialog,dialog)));
    }

    @Override
    public ForkedString next() {
        if (authFailedLastTime || credentialsHaveExpired()) {
            auth.refreshCredentials();
            lastAuthenticated = System.currentTimeMillis();
        }
        try {
            ForkedString email = nextCheck();
            authFailedLastTime = false;
            return email;
        } catch (ZimbraEmailException e) {
            Log.info("Failure type " + e.type);
            authFailedLastTime = e.type==ZimbraEmailException.Type.AUTH;
            throw e;
        }
    }
    
    boolean credentialsHaveExpired() {
        long now = System.currentTimeMillis();
        return now - lastAuthenticated > 12 * 60 * 60 * 1000;
    }
    
    /*
       This uses the Zimbra SOAP API.  To use the Zimbra RSS API,
       change to 
       return ZimbraRssInboxFeed.of(auth).next();
     */
    ForkedString nextCheck() {
        Log.info("Checking ");
        return ZimbraEmailChecker.of(auth).next();
    }

}
