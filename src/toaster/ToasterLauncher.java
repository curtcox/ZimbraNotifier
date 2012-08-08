package toaster;

import auth.AuthenticationSource;
import java.awt.Rectangle;
import notify.Notifiable;
import prefs.PreferencesPersistence;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
final class ToasterLauncher 
    implements Runnable
{
    final String title;
    final String body;
    final Notifiable notifiable;
    final AuthenticationSource auth;
    
    static final Rectangle rect = defaultRectangle();
    static final AWTUtilitiesWrapper awt = new AWTUtilitiesWrapper();

    static Rectangle defaultRectangle() {
        int width = (int) (Screen.width * 0.5);
        int height = Screen.height;
        int x = Screen.width - width - 1;
        int startY = Screen.height;
        int stopY = startY - height - 1;
        int y = stopY;
        return new Rectangle(x,y,width,height);
    }

    private ToasterLauncher(
        String title, String body, Notifiable notifiable, AuthenticationSource auth)
    {
        this.title = title;
        this.body = body;
        this.notifiable = notifiable;
        this.auth = auth;
    }
    
    static ToasterLauncher of(
        ForkedString message, Notifiable notifiable, AuthenticationSource auth)
    {
        Class type = message.type;
        if (type==String.class) { 
            String title = (String) message.parts.get(0);
            String body = (String) message.parts.get(1);
            return new ToasterLauncher(title,body,notifiable,auth);
        }
        if (Throwable.class.isAssignableFrom(type)) {
            throw new IllegalArgumentException((Throwable) message.parts.get(0));
        }
        throw new IllegalArgumentException("type="+type);
    }
    
    @Override public void run() {
        Toaster toaster = Toaster.of(title,rect,body,notifiable);
        awt.setWindowOpaque(toaster, true);
        String user = auth.getUser();
        PreferencesPersistence.getBounds(user, rect);
        Animation.of(user,toaster,rect);	
    }

}
