package notify;

import strings.ForkedString;

/**
 * For notifying.
 */
public interface Notifiable {

    void deliverNotification(ForkedString notification);

}
