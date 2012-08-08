package login;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import util.Log;

/**
 *
 * @author Curt
 */
class WindowListenerAdapter implements WindowListener {

    static void of(Frame frame) {
        frame.addWindowListener(new WindowListenerAdapter());
    }
    
    @Override public void windowClosed(WindowEvent e)      { log("Closed"); }
    @Override public void windowClosing(WindowEvent e)     { log("Closing"); }
    @Override public void windowOpened(WindowEvent e)      { log("Opened");}
    @Override public void windowIconified(WindowEvent e)   { log("Iconified");}
    @Override public void windowDeiconified(WindowEvent e) { log("Deiconified");}
    @Override public void windowActivated(WindowEvent e)   { log("Activated");}
    @Override public void windowDeactivated(WindowEvent e) { log("Deactivated");}

    void log(String string) {
        Log.info(string);
    }
}
