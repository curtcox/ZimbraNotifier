package notify;

import java.util.Iterator;
import strings.AbstractStringSource;
import strings.ForkedString;

/**
 * Wraps a ForkedString Source to allow it to Notify and do something else.
 * @author Curt
 */
public final class NotifiableTee {
    
    public static Iterator<ForkedString> to(
            final Iterator<ForkedString> messages, final Notifiable notifiable) {
        return new AbstractStringSource(){

            @Override
            public ForkedString next() {
                ForkedString next = messages.next();
                notifiable.deliverNotification(next);
                return next;
            }
        };
    }
}
