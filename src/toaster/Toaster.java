package toaster;

import auth.AuthenticationSource;
import frame.DecoratedFrame;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import login.DynamicIcon;
import notify.Notifiable;
import strings.ForkedString;


/**
 * Class that represents a single toaster
 * 
 * @author daniele piras
 * 
 */
public final class Toaster
    //extends JFrame
    extends DecoratedFrame
    implements ZoomWindow
{
    static final Color borderColor = Color.DARK_GRAY;
    static final Color toasterColor = Color.WHITE;
    static final Color messageColor = Color.BLACK;
    static final int margin = 0;

    static final AWTUtilitiesWrapper awt = new AWTUtilitiesWrapper();
    private static final long serialVersionUID = 1L;

    // Text area for the message
    private final JEditorPane message = new JEditorPane(){{
        this.setEditable(false);
        setEditorKit(new HTMLEditorKit());
        setMargin( new Insets( 2,2,2,2 ) );
        setForeground( messageColor );
    }};
    
    private final JProgressBar bar = new JProgressBar() {{
            setMinimum(0);
            setMaximum(1000);
        }
        @Override
        public Dimension getPreferredSize() {
            Dimension max = super.getPreferredSize();
            max.height = 5;
            return max;
        }
    };

    /***
     * Simple constructor that initialized components...
     */
    private Toaster(String title, Rectangle rect, String msg, Notifiable notifier) {
        super(title);
        setIconImages(DynamicIcon.images("",title));
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        setSize(rect.width, rect.height);
        message.setText( msg );
        message.addMouseListener(new MouseClickListener(this,notifier));

        getContentPane().add(
            new JPanel(new BorderLayout(1, 1)) {{
                setBackground( borderColor );
                setBorder(BorderFactory.createEtchedBorder());
                add(bar,BorderLayout.NORTH);
                add(
                    new JPanel(new BorderLayout( margin, margin )) {{
                        setBackground( toasterColor );
                        add(message, BorderLayout.CENTER);
                }});
            }}
        );
    }
    
    static Toaster of(String title, Rectangle rect, String msg, Notifiable notifier) {
        return new Toaster(title,rect,msg,notifier);    
    }
    
    public static void show(ForkedString msg,Notifiable notifiable,AuthenticationSource auth) {
        EventQueue.invokeLater(ToasterLauncher.of(msg,notifiable,auth));
    }

    @Override
    public void startShowing() {
        setVisible(true);
        setAlwaysOnTop(true);
    }

    @Override
    public void stopShowing() {
        setVisible(false);
        dispose();
    }
    
    @Override
    public void setOpacity6(float value) {
        awt.setWindowOpacity(this, value);	
    }

    @Override
    public void setCountdown(double value) {
        bar.setValue((int)Math.floor(value * 1000));
    }
}