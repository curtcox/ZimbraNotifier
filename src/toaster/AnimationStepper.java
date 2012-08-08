package toaster;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import toaster.Animation.State;

/**
 *
 * @author Curt
 */
final class AnimationStepper
    implements ActionListener
{

    // these change due to animation
    State state = State.start;
    int percent = 0;
    int speed = 13;

    // these change if the user moves or resizes the window
    int stopY;
    int x;
    int deltaY;

    final ZoomWindow toaster;
    final Stopper stopper;
    final int startY = Screen.height;
    final Rectangle rect;

    private AnimationStepper(ZoomWindow toaster, Stopper stopper, Rectangle rect) {
        this.toaster = toaster;
        this.stopper = stopper;
        this.rect = rect;
        setFrom(rect);
    }
    
    void setFrom(Rectangle rect) {
        stopY = rect.y;
        x = rect.x;
        deltaY = stopY - startY;
    }
    
    static AnimationStepper of(ZoomWindow toaster, Stopper stopper, Rectangle dim) {
        return new AnimationStepper(toaster,stopper,dim);
    }
    
    @Override
    public void actionPerformed(ActionEvent nextFrame) {
        if (state==State.stop)    { stop();    return;}
        if (state==State.decay)   { decay();   return;}
        if (state==State.sustain) { sustain(); return;}
        if (state==State.attack)  { attack();  return;}
        if (state==State.start)   { start();   return;}
        throw new IllegalStateException("State = " + state);
    }

    void attack() {
        if (percent>=100) {
            state = State.sustain;
            return;
        }
        setWindow(percent);
        percent += speed;
        if (speed>1) {
            speed--;
        }
    }

    void decay() {
        if (percent<=0) {
            state = State.stop;
            return;
        }
        setWindow(percent);
        percent -= speed;
        speed++;
    }

    void sustain() {
        if (!toaster.isVisible()) {
            stop();
            return;
        }
        int end = 2800;
        setFrom(toaster.getBounds(rect));
        double sustained = percent - 100;
        toaster.setCountdown(1.0d - (sustained / end ));
        percent++;
        if (percent==end) {
            percent = 100;
            state = State.decay;	
        }
    }

    void start() {
        toaster.setLocation(x, Screen.height);
        setOpacity(0.0f);
        toaster.startShowing();
        state = State.attack;
    }

    void stop() {
        toaster.stopShowing();
        stopper.stop();
    }

    void setOpacity(double opacity) {
        float value = (float) opacity;
        value = 0.99f * value;
        toaster.setOpacity6(value);
    }

    void setWindow(int percent) {
        double fraction = ((double) percent) / 100.0d;
        final int y = (int) (startY + deltaY * fraction);
        setWindow(y,fraction);
    }

    void setWindow(int y,double opacity) {
        toaster.setLocation(x, y);
        setOpacity(opacity);
    }
    
}
