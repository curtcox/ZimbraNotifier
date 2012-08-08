package notify;


import java.util.ArrayList;
import java.util.List;
import strings.ForkedString;

/**
 * Notifier that just adds notifications to a list.
 * @author curt
 */
public final class NotifiableList implements Notifiable {

    public final List<ForkedString> list = new ArrayList<ForkedString>();

    private NotifiableList() {}
    
    public static NotifiableList of() {
        return new NotifiableList();
    }
    
    @Override
    public void deliverNotification(ForkedString html) {
        list.add(html);
    }

}
