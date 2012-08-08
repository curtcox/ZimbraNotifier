package login;

import java.awt.event.WindowEvent;
import notify.Notifiable;
import strings.ForkedString;

/**
 * Notifies the given notifiable when the frame is iconified.
 * @author Curt
 */
public final class NotifyOnWindowIconified extends WindowListenerAdapter {

    final Notifiable notifiable;
    
    public static void listenToAndNotify(ILoginDialog frame, Notifiable notifiable) {
        frame.addWindowListener(new NotifyOnWindowIconified(notifiable));
    }

    private NotifyOnWindowIconified(Notifiable notifiable) {
        this.notifiable = notifiable;
    }
    
    @Override public void windowIconified(WindowEvent e)   {
        notifiable.deliverNotification(ForkedString.of("Iconified"));
    }

}
