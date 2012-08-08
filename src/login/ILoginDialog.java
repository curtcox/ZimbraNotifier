package login;

import auth.AuthenticationSource;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.util.List;
import notify.Notifiable;
import notify.Notifier;
import prefs.Preferences;

/**
 * The interface that LoginDialog implements.
 * @author Curt
 */
public interface ILoginDialog extends 
    AuthenticationSource, Preferences, Notifier, Notifiable
{
    void setStatus(String status);

    void setTitle(String user);

    void setIconImages(List<? extends Image> icons);
    
    void iconify();
    
    void addWindowListener(WindowListener listener);
}
