package notify;

import strings.ForkedString;

/**
 *
 * @author Curt
 */
public final class DelegatingNotifiable
    implements Notifiable
{

    public Notifiable notifiable;

    @Override
    public void deliverNotification(ForkedString notification) {
        notifiable.deliverNotification(notification);
    }
    
}
