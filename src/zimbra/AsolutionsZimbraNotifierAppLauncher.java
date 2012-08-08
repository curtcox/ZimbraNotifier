package zimbra;

import java.util.Iterator;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import login.ILoginDialog;
import login.LoginDialog;
import notify.Notifiable;
import notify.NotifiableBrowser;
import notify.NotifiableToaster;
import notify.NotificationMonitor;

/**
 * Polls a Zimbra inbox and briefly displays a notification window when contents change.
 * @author curt
 */
final class AsolutionsZimbraNotifierAppLauncher {
    
    final ILoginDialog login;
    final Notifiable toaster;
    final Iterator messages;
    final int everyNSeconds;
    static final String ZIMBRA_SERVER = "https://zimbra.asolutions.com/";

    private AsolutionsZimbraNotifierAppLauncher(ILoginDialog login, Notifiable toaster, Iterator messages,int everyNSeconds) {
        this.login = login;
        this.toaster = toaster;
        this.messages = messages;
        this.everyNSeconds = everyNSeconds;
    }

    static AsolutionsZimbraNotifierAppLauncher of(ILoginDialog login, Notifiable toaster, Iterator messages, int everyNSeconds) {
       return new AsolutionsZimbraNotifierAppLauncher(login,toaster,messages,everyNSeconds);    
    }

    static AsolutionsZimbraNotifierAppLauncher of() {
       final ILoginDialog login = LoginDialog.of();
       return new AsolutionsZimbraNotifierAppLauncher(
           login,
           NotifiableToaster.of(NotifiableBrowser.of(ZIMBRA_SERVER),login),
           ZimbraInboxFeed.of(login),
           10);    
    }
    
    static boolean installNimbus() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return true;
                }
            }
        } catch (Exception e) {
            // Just live with the look and feel given
        }    
        return false;
    }
    
    AsolutionsZimbraNotifierApp connectComponents() {
        final AsolutionsZimbraNotifierApp app =
            AsolutionsZimbraNotifierApp.of(login,toaster,messages,everyNSeconds);
        
        app.showToasterOnDialogIconified();
        return app;
    }
        
    AsolutionsZimbraNotifierApp launch() {
        final AsolutionsZimbraNotifierApp app = connectComponents();
        app.login();
        app.startMonitoring();
        return app;
    }
    
    public static void main(String[] args) {
        installNimbus();
        of().launch(); 
    }

}
