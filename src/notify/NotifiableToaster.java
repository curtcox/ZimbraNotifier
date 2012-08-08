package notify;

import auth.AuthenticationSource;
import strings.ForkedString;
import toaster.Toaster;

public final class NotifiableToaster
    implements Notifiable
{

    final Notifiable notifier;
    final AuthenticationSource auth;

    private NotifiableToaster(Notifiable notifier, AuthenticationSource auth) {
        this.notifier = notifier;
        this.auth = auth;
    }

    @Override
    public void deliverNotification(ForkedString html) {
         Toaster.show(html,notifier,auth);
    }

    public static Notifiable of(Notifiable notifier,AuthenticationSource auth) {
        return new NotifiableToaster(notifier,auth);
    }

    public static Notifiable of(AuthenticationSource auth) {
        return new NotifiableToaster(NotifiableSysout.of(),auth);
    }

}
