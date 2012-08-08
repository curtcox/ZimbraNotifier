package notify;

import java.util.Iterator;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
public final class NotifiableConditional
    implements Notifiable
{
    final Notifiable notifiable;
    final Iterator<Boolean> booleans;

    private NotifiableConditional(Notifiable notifiable, Iterator<Boolean> booleans) {
        this.notifiable = notifiable;
        this.booleans = booleans;
    }
    
    public static Notifiable of(Notifiable notifiable, Iterator<Boolean> booleans) {
        return new NotifiableConditional(notifiable,booleans);
    }
    
    @Override
    public void deliverNotification(ForkedString notification) {
        if (booleans.next()) {
            notifiable.deliverNotification(notification);
        }
    }
    
}
