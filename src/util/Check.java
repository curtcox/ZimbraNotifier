package util;

import java.awt.EventQueue;
import java.util.Iterator;
import strings.AbstractStringSource;
import strings.ForkedString;


/**
 *
 * @author cerdec-dev1
 */
public final class Check {

    public static <O> O notNull(O o) {
        if (o==null) {
            throw new IllegalArgumentException();
        }
        return o;
    }

    public static final String ONLY_CALL_ON_EDT = "Only call this method on the EDT";
    public static final String NEVER_CALL_ON_EDT = "Never call this method on the EDT";
    
    public static void isNotEDT() {
        if (EventQueue.isDispatchThread()) {
            throw new IllegalStateException(NEVER_CALL_ON_EDT);
        }
    }

    public static void isEDT() {
        if (!EventQueue.isDispatchThread()) {
            throw new IllegalStateException(ONLY_CALL_ON_EDT);
        }
    }

    public static AbstractStringSource isNotEDT(final Iterator<ForkedString> iterator) {
        return new AbstractStringSource() {
            @Override
            public ForkedString next() {
                if (EventQueue.isDispatchThread()) {
                    throw new IllegalStateException(NEVER_CALL_ON_EDT);
                }
                return iterator.next();
            }
        };
    }

}
