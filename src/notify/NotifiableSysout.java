package notify;

import strings.ForkedString;

/**
 * Notifier that prints to the console.
 * @author curt
 */
public final class NotifiableSysout implements Notifiable {

    private NotifiableSysout() {}

    public static Notifiable of() {
        return new NotifiableSysout();
    }

    @Override
    public void deliverNotification(ForkedString message) {
        System.out.println(message);
    }

}
