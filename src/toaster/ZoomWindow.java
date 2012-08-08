package toaster;

import java.awt.Rectangle;

/**
 *
 * @author Curt
 */
interface ZoomWindow {

    Rectangle getBounds(Rectangle rv);
    void startShowing();
    void setLocation(int x, int y);
    void stopShowing();
    void setOpacity6(float value);
    void setCountdown(double value);
    boolean isVisible();
}
