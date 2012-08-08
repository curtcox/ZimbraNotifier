package login;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author Curt
 */
class BorderPanel
    extends JPanel
{
    private BorderPanel() {
        setLayout(new BorderLayout());
    }
 
    static BorderPanel of() {
        return new BorderPanel();
    }
    
    BorderPanel center(Component component) {
        add(component,BorderLayout.CENTER);
        return this;
    }

    BorderPanel west(Component component) {
        add(component,BorderLayout.WEST);
        return this;
    }

    BorderPanel east(Component component) {
        add(component,BorderLayout.EAST);
        return this;
    }
    
    BorderPanel south(Component component) {
        add(component,BorderLayout.SOUTH);
        return this;
    }
}
