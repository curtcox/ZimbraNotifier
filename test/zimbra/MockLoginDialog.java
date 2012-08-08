package zimbra;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.util.List;
import login.ILoginDialog;
import login.NotifyOnWindowIconified;
import notify.Notifiable;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
class MockLoginDialog
    implements ILoginDialog
{

    String user;
    String password = "";
    Notifiable notifiable;
    String status;
    String title;
    List<? extends Image> images;
    WindowListener listener;
    
    @Override public boolean isBeepEnabled() { return false;    }
    @Override public String getUser() { return user; }
    @Override public char[] getPassword() { return password.toCharArray(); }
    @Override public void refreshCredentials() {
        setTitle(user);
    }
    @Override public void registerForNotification(Notifiable notifiable) {
        this.notifiable = notifiable;
        NotifyOnWindowIconified.listenToAndNotify(this,notifiable);
    }
    @Override public boolean showWindowOnNewMail() { return false; }
    @Override public void setStatus(String status) { this.status = status; }
    @Override public boolean showWindowOnIconify() { return false; }

    @Override public void deliverNotification(ForkedString notification) {
        notifiable.deliverNotification(notification);
        // Note that this is a hack just for testing
        // We verify the image update in tests based on ordinality, because
        // we have no good way to test based on contents
        // Also, note that the type parameter on the image list is now a lie. 
        setIconImages(notification.parts);
    }
    @Override public void setTitle(String title) { this.title = title; }
    @Override public void setIconImages(List<? extends Image> images) { this.images = images; }

    @Override public void iconify() {
        listener.windowIconified(null);
    }

    @Override
    public void addWindowListener(WindowListener listener) {
        this.listener = listener;
    }

}
