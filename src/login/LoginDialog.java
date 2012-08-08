package login;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URI;
import javax.swing.*;
import notify.DelegatingNotifiable;
import notify.Notifiable;
import notify.NotifiableSysout;
import prefs.Preferences;
import prefs.PreferencesPersistence;
import strings.ForkedString;
import util.Check;
import util.Log;

public final class LoginDialog 
     extends JFrame
     implements ILoginDialog 
{
    volatile boolean userIsSupplyingCredentials;
    volatile boolean userHasChangedPreferences;
    
    final Notifiable notifiable;
    final JPanel cards = new JPanel();
    final CardLayout layout = new CardLayout();
    final JPanel credentials = new JPanel();
    final JLabel userLabel = new JLabel("User :");
    final JLabel passwordLabel = new JLabel("Password :");
    final JTextField userField = new JTextField(10);
    final JPasswordField passwordField = new JPasswordField(10);
    final JLabel configure = DynamicIcon.newGear(8);
    final JLabel help = DynamicIcon.newLabel("?", 16, Color.WHITE, Color.BLUE);
    final JLabel status = new JLabel(" ");
    
    final JPanel settings = new JPanel();
    final JCheckBox beep = new ActionBox("Beep?") {
        @Override public void doChecked(boolean checked) {
            updatePersistedPreferences();
        }
    };
    final JCheckBox onNewMail = new ActionBox("On new mail?") {
        @Override public void doChecked(boolean checked) {
            updatePersistedPreferences();
        }
    };
    final JCheckBox onIconify = new ActionBox("On iconify?") {
        @Override public void doChecked(boolean checked) {
            updatePersistedPreferences();
        }
    };
    final JButton back = new ActionButton("Back") {
        @Override void doAction() {
            layout.show(cards, CREDENTIALS);
        }
    };

    static final String CREDENTIALS = "credentials";
    static final String SETTINGS = "settings";
    final PreferencesPersistence persist = PreferencesPersistence.of();
    private static final long serialVersionUID = 1L;
    
    final KeyListener loginOnEnter = new KeyAdapter() {
        @Override
        public void keyReleased( KeyEvent e ) {
            if( e.getKeyCode() == KeyEvent.VK_ENTER ) {
                restorePreferences();
                login();
            }
        }
    };

    final KeyListener updateIcon = new KeyAdapter() {
        @Override
        public void keyReleased( KeyEvent e ) {
            LoginDialog.this.deliverNotification(ForkedString.EMPTY);
        }
    };

    void updatePersistedPreferences() {
        persist.setPrefsForUser(LoginDialog.this);
        userHasChangedPreferences = true;
    }
    
    void restorePreferences() {
        Preferences prefs = persist.getPrefsForUser(getUser());
        if (prefs!=null && !userHasChangedPreferences) {
            beep.setSelected(prefs.isBeepEnabled());
            onNewMail.setSelected(prefs.showWindowOnNewMail());
        }
    }
    
    void login() {
        if (OS_supports_programatically_iconifying_frame_without_deadlock()) {
            iconify();
        }
        userIsSupplyingCredentials = false;
        Log.info("Login finished");
    }

    @Override
    public void iconify() {
        setExtendedState(Frame.ICONIFIED);
    }
    
    boolean OS_supports_programatically_iconifying_frame_without_deadlock() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.startsWith("win");
    }

    private static LoginDialog of(Notifiable notifiable) {
        LoginDialog dialog = new LoginDialog(notifiable);
        SystemExitOnWindowClose.of(dialog);
        return dialog;
    }

    public static LoginDialog of() {
        DelegatingNotifiable notifiable = new DelegatingNotifiable();
        final LoginDialog dialog = of(notifiable);
        notifiable.notifiable = NotifiableFrame.of(dialog, dialog);
        return dialog;
    }
    
    private LoginDialog(Notifiable notifiable) {
        this.notifiable = notifiable;
        userField.addKeyListener(loginOnEnter);
        userField.addKeyListener(updateIcon);
        passwordField.addKeyListener(loginOnEnter);
        setTitle("Zimbra");
        configureCards();
        add(cards);
        setResizable(false);
        pack();
    }

    void configureCards() {
        configureCredentials();
        configureSettings();
        cards.setLayout(layout);
        cards.add(credentials,CREDENTIALS);
        cards.add(settings,SETTINGS);
        layout.show(cards, CREDENTIALS);
    }
    
    void configureCredentials() {
        final JPanel userAndPassword = new JPanel();
        UIBuilder.of(userAndPassword,2,2)
            .add(new JPanel() {{
                add(userLabel);
                add(configure);
                setLayout(new GridLayout(1,2));
            }})
            .add(userField)	
            .add(passwordLabel)
            .add(passwordField);
        configure.setToolTipText("Change Settings");
        configure.addMouseListener(new MouseAction() {
            @Override void doAction() { layout.show(cards, SETTINGS); }
        });
        credentials.add(BorderPanel.of()
            .center(userAndPassword)
            .south(panel(status))
        );
    }

    JPanel panel(final JComponent contents) {
        return new JPanel() {{
            add(contents);
        }};    
    }
    
    void configureSettings() {
        UIBuilder.of(settings,3,2)
            .add(back,"Back to login")
            .add(BorderPanel.of()
                .east(panel(tip(help,"About Zimbra Notifier")))
                .center(panel(tip(new JLabel("Window?"),"Show a notification window?"))))
            .add(beep,"Beep when new mail arrives?")
            .add(onIconify,"Show current mail every time this window is iconified.")
            .add(new JLabel(""))
            .add(onNewMail,"Show a notification window when new mail arrives.");
        beep.setSelected(true);
        onNewMail.setSelected(true);
        help.addMouseListener(new MouseAction() {
            @Override void doAction() { showWikiPage(); }
        });
    }
    
    JComponent tip(JComponent component, String tooltip) {
        component.setToolTipText(tooltip);
        return component;
    }

    void showWikiPage() {
        try {
            URI uri = new URI("https://mediawiki.asolutions.com/index.php/Zimbra_Notifier");
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void setVisible(boolean flag) {
        Check.isEDT();
        super.setVisible(flag);
    }
	
    @Override
    public String getUser() {
        return userField.getText();
    }

    @Override
    public char[] getPassword() {
        return passwordField.getPassword();
    }

    @Override
    public boolean isBeepEnabled() {
        return beep.isSelected();
    }

    @Override
    public boolean showWindowOnNewMail() {
        return onNewMail.isSelected();
    }

    @Override
    public boolean showWindowOnIconify() {
        return onIconify.isSelected();
    }

    @Override
    public void refreshCredentials() {
        Log.info("Refresh credentials");
        Check.isNotEDT();
        askUserForCredentials();
        waitForUserToFinishSupplyingCredentials();
    }

    @Override
    public void setStatus(final String value) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                status.setText(value);
            }
        });
    }
    
    void askUserForCredentials() {
        Check.isNotEDT();
        notifiable.deliverNotification(ForkedString.EMPTY);
        userIsSupplyingCredentials = true;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                status.setText("Login Required");
                setVisible(true);
            }
        });
    }

    void waitForUserToFinishSupplyingCredentials() {
        Check.isNotEDT();
        while (userIsSupplyingCredentials) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        setStatus("Validating Login");
    }

    @Override
    public void registerForNotification(Notifiable notifiable) {
        //Iterator<Boolean> show = Preferences.Adapter.of(this).showWindowOnIconify();
        NotifyOnWindowIconified.listenToAndNotify(this,notifiable);
    }

    @Override
    public String toString() {
        return String.format(
            "user=%s password=%s userIsSupplyingCredentials=%s",
            getUser(),getPassword(), userIsSupplyingCredentials);    
    }

    @Override
    public void deliverNotification(final ForkedString message) {
        notifiable.deliverNotification(message);
    }
    
    public static void main(String[] args) {
    	LoginDialog dialog = of(NotifiableSysout.of());
    	dialog.refreshCredentials();
    	System.out.println(String.format("user = %s password = %s",dialog.getUser(),dialog.getPassword()));
    }

}
