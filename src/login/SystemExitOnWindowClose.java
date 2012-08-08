package login;

import java.awt.Frame;
import java.awt.event.WindowEvent;

/**
 *
 * @author Curt
 */
final class SystemExitOnWindowClose extends WindowListenerAdapter {

    static void of(Frame frame) {
        frame.addWindowListener(new SystemExitOnWindowClose());
    }
    
    @Override public void windowClosed(WindowEvent e)      { exit(); }
    @Override public void windowClosing(WindowEvent e)     { exit(); }

    void exit() {
        log("Exiting");
        System.exit(0);
    }

}
