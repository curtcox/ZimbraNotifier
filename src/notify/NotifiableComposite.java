package notify;

import strings.ForkedString;

public final class NotifiableComposite 
    implements Notifiable
{

    final Notifiable[] notifiers;
    
    private NotifiableComposite(Notifiable... notifiers) {
        this.notifiers = notifiers;
    }

    public static Notifiable of(Notifiable... notifiers) {
        return new NotifiableComposite(notifiers);
    }

    @Override
    public void deliverNotification(ForkedString notification) {
        for (Notifiable notifier : notifiers) {
            notifier.deliverNotification(notification);
        }
    }
    
}
