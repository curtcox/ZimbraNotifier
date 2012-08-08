package login;

import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * A slightly more concise way to add things to a panel, with optional tooltips.
 * @author Curt
 */
final class UIBuilder {
    
    final JPanel panel;
    
    private UIBuilder(JPanel panel) {
        this.panel = panel;
    }
    
    static UIBuilder of(JPanel panel, int rows, int columns) {
        panel.setLayout(new GridLayout(rows,columns));
        return new UIBuilder(panel);
    }
    
    UIBuilder add(JComponent component) {
        panel.add(component);
        return this;
    }

    UIBuilder add(JComponent component, String tooltip) {
        panel.add(component);
        component.setToolTipText(tooltip);
        return this;
    }

}
