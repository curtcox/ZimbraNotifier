package login;

import auth.AuthenticationSource;
import java.awt.EventQueue;
import notify.Notifiable;
import strings.ForkedString;
import zimbra.ZimbraEmail;
import zimbra.ZimbraEmailException;

/**
 * Updates a frame title and icon when requested.
 * This allows a Frame to use an AuthenticationSource for the source of
 * title and icons.  Whenever a notification is delivered to this notifiable,
 * it updates the frame based on the authentication source.
 * @author Curt
 */
public final class NotifiableFrame 
    implements Notifiable
{

    final ILoginDialog frame;
    final AuthenticationSource auth;
    
    private NotifiableFrame(ILoginDialog frame, AuthenticationSource auth) {
        this.frame = frame;
        this.auth = auth;
    }

    public static NotifiableFrame of(ILoginDialog frame, AuthenticationSource auth) {
        return new NotifiableFrame(frame,auth);
    }
    
    @Override
    public void deliverNotification(final ForkedString message) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                String user = auth.getUser();
                if (user.isEmpty()) {
                    user = "Zimbra";
                }
                frame.setTitle(user);
                frame.setIconImages(DynamicIcon.images(iconText(message),user));
            }
        });
    }
    
    String iconText(ForkedString message) {
        Class type = message.type;
        if (type == ZimbraEmailException.class) {
            return "!";
        }
        if (type == ZimbraEmail.class) {
            return Integer.toString(message.parts.size());
        }
        return "?";
    }

}
