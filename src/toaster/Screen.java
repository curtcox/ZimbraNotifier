package toaster;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

final class Screen {

    static int width;
    static int height;

    static {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle screenRect = ge.getMaximumWindowBounds();
        width = screenRect.width;
        height = screenRect.height;
    }
}
