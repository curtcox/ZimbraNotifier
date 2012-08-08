package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author Curt
 */
public abstract class ActionButton
    extends JButton
{
    ActionButton(String text) {
        super(text);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAction();
            }
        });
    }
    
    abstract void doAction();
}
