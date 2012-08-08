package zimbra;

import java.util.Iterator;
import login.ILoginDialog;
import strings.AbstractStringSource;
import strings.ForkedString;

/**
 * Transforms a ZimbraException into an error Page
 * @author Curt
 */
public final class ZimbraExceptionTransformStringSource
    extends AbstractStringSource
{
    final Iterator<ForkedString> source;
    final ILoginDialog login;
    
    private ZimbraExceptionTransformStringSource(
        Iterator<ForkedString> source, ILoginDialog login) {
        this.source = source;
        this.login = login;
    }

    public static AbstractStringSource of(
        ILoginDialog login,Iterator<ForkedString> source) {
        return new ZimbraExceptionTransformStringSource(source,login);
    }

    @Override
    public ForkedString next() {
        try {
            login.setStatus("Checking");
            ForkedString next = source.next();
            login.setStatus(" ");
            return next;
        } catch (ZimbraEmailException e) {
            login.setStatus(ZimbraExceptionFormatter.findUpdate(e).status);
            return ZimbraExceptionFormatter.format(e);
        }
    }

}
