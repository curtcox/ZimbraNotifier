package frame;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

/**
 * A JFrame that bolts a fork of the Metal window decorations on, no matter
 * what the current look and feel is.  This is done because:
 * <ol>
 *   <li> We fade in and out by altering window position and transparency
 *   <li> We need window decorations to support user window sizing and positioning
 *   <li> Semi-transparent frames won't work with native window decorations.
 *   <li> We want to use Nimbus, rather than metal.
 *   <li> Metal supports non-native Window decorations, but Nimbus doesn't.
 * </ol>
 * @author Curt
 */
public class DecoratedFrame
    extends JFrame
{
    public DecoratedFrame() {
        this("");
    }
    
    public DecoratedFrame(String title) {
        super(title);
        setUndecorated(true);
        MetalRootPaneUI ui = new MetalRootPaneUI(rootPane);
        rootPane.setUI(ui);
        rootPane.setWindowDecorationStyle(JRootPane.FRAME);
    }
    
    public static void main(String[] args) {
        DecoratedFrame frame = new DecoratedFrame();
        frame.setTitle("Title");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10,1));
        for (int i=0; i<10; i++) {
            panel.add(new JLabel("------ Glass Frame label with lots of text ---- "));
        }
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
