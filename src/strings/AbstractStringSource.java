package strings;
import java.util.Iterator;

/**
 * Produces an endless sequence of StringS.
 */
public abstract class AbstractStringSource
    implements Iterator<ForkedString>
{

    @Override
    final public boolean hasNext() {
        return true;
    }

    @Override
    final public void remove() {
        throw new UnsupportedOperationException();
    }

}
