package strings;
import auth.AuthenticationSource;
import net.URItoInputStream;
import java.util.Iterator;

/**
 * Something that produces a sequence of StringS.
 */
public final class StringSource
    extends AbstractStringSource
{
    final Iterator<ForkedString> inner;

    private StringSource(String source, URItoInputStream streamer, AuthenticationSource auth) {
        this.inner = getSource(source,streamer,auth);
    }

    public static Iterator<ForkedString> of(String source, URItoInputStream streamer, AuthenticationSource auth) {
        return new StringSource(source,streamer,auth);
    }

    private Iterator<ForkedString> getSource(
            String source, URItoInputStream streamer, AuthenticationSource auth) {
        if (URIStringSource.supports(source)) {
            return wrapped(URIStringSource.parse(source,auth,streamer));
        } else {
            return wrapped(ConstantStringSource.of(ForkedString.of(source)));
        }
    }

    Iterator<ForkedString> wrapped(Iterator<ForkedString> source) {
        return ExceptionTransformStringSource.of(source);
    }

    @Override public ForkedString next() { return inner.next(); }

}
