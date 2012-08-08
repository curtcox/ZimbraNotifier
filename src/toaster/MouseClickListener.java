package toaster;

import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import notify.Notifiable;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
final class MouseClickListener extends MouseAdapter {
    final Notifiable notifier;
    final Window window;

    MouseClickListener(Window window, Notifiable notifier) {
        this.window = window;
        this.notifier = notifier;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            notifier.deliverNotification(ForkedString.EMPTY);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            window.setVisible(false);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            window.setVisible(false);
        }
    }
    
}
