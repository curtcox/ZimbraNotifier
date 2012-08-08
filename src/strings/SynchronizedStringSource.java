package strings;

import java.util.Iterator;

/**
 * Wraps a given string source to make access single threaded.
 * @author Curt
 */
public final class SynchronizedStringSource
    extends AbstractStringSource
{
    final Iterator<ForkedString> source;

    private SynchronizedStringSource(Iterator<ForkedString> source) {
        this.source = source;
    }
    
    public static SynchronizedStringSource of(Iterator<ForkedString> source) {
        return new SynchronizedStringSource(source);
    }
    
    @Override
    public synchronized ForkedString next() {
        return source.next();
    }
    
}
