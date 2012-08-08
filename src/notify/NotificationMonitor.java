package notify;

import java.util.Iterator;
import strings.ForkedString;
import zimbra.ZimbraEmail;

/**
 * Single point/thread of monitoring messages.
 * @author Curt
 */
public final class NotificationMonitor {
    
    private Iterator check;
    private Iterator<ForkedString<ZimbraEmail>> messages;
  ???  
    public static NotificationMonitor of() {
        return new NotificationMonitor();
    }
    
    private NotificationMonitor() {}
    
    public void startMonitoring(Iterator<ForkedString<ZimbraEmail>> messages,TimedNotifier.Builder builder) {
        startMonitoringInboxFeed(builder);
    }
    
    
    void startMonitoringInboxFeed(TimedNotifier.Builder builder) {
        check = builder.build();
    }

    public ForkedString nextMessage() {
        return messages.next();
    }
}
