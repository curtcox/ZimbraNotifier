package login;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Slightly more concise way of taking the same action on different mouse events
 * @author Curt
 */
public abstract class MouseAction extends MouseAdapter {

    @Override public void mousePressed(MouseEvent e) { doAction(); }
    @Override public void mouseClicked(MouseEvent e) { doAction(); }

    abstract void doAction();
}
