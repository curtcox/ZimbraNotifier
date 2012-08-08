package util;

import java.util.Iterator;

/**
 *
 * @author Curt
 */
public abstract class InfiniteIterator<T> 
    implements Iterator<T>
{
    final @Override public boolean hasNext() { return true; }
    final @Override public void remove() {
        throw new UnsupportedOperationException();
    }
}
