package zimbra;

import java.util.Iterator;
import notify.Notifiable;
import notify.NotifiableList;
import notify.NotificationMonitor;
import notify.TimedNotifier;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import strings.ForkedString;
import util.All;

/**
 *
 * @author Curt
 */
public class AsolutionsZimbraNotifierAppTest {
    
    final MockLoginDialog login = new MockLoginDialog();
    final Notifiable toaster = NotifiableList.of();
    final NotificationMonitor monitor = NotificationMonitor.of();


    @Test
    public void configure_creates_builder() {
        Iterator<ForkedString<ZimbraEmail>> messages = null;

        AsolutionsZimbraNotifierApp app = AsolutionsZimbraNotifierApp.of(login,toaster,messages,monitor,0);
        assertNotNull(app.configureTimedNotifier());
    }

    @Test
    public void monitor_calls_uri() {
        Iterator messages = All.of(ForkedString.EMPTY);
        AsolutionsZimbraNotifierApp app = AsolutionsZimbraNotifierApp.of(login,toaster,messages,monitor,0);
        TimedNotifier.Builder builder = app.configureTimedNotifier();
        builder.messages(messages);
        builder.running(All.times(2));
        app.startMonitoring();
    }

}
