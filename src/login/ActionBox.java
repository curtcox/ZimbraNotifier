package login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Curt
 */
public abstract class ActionBox extends JCheckBox {

    ActionBox(String text) {
        super(text);
        addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent actionEvent) {
                doChecked(getModel().isSelected());
            }
        });
    }
    
    abstract void doChecked(boolean checked);
}
