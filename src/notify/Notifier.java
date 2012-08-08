package notify;

/**
 * Something that notifies.
 * @author Curt
 */
public interface Notifier {

    /**
     * This is used to request notification.
     */
    void registerForNotification(Notifiable notifiable);
    
}
