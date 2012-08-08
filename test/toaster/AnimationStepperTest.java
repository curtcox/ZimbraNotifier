package toaster;

import java.awt.Rectangle;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import static toaster.Animation.State.*;

/**
 *
 * @author Curt
 */
public class AnimationStepperTest {

    class MockWindow implements ZoomWindow {
        @Override public void startShowing() {}
        @Override public void setLocation(int x, int y) {}
        @Override public void stopShowing() {}
        @Override public void setOpacity6(float value) {}
        @Override public Rectangle getBounds(Rectangle rv) { return rv; }
        @Override public void setCountdown(double value) {}
        @Override public boolean isVisible() { return true; }
    }
    
    class MockStopper implements Stopper {

        boolean stop;
        
        @Override
        public void stop() {
            stop = true;
        }
        
    }
    
    MockWindow toaster = new MockWindow();
    MockStopper stopper = new MockStopper();
    AnimationStepper stepper = AnimationStepper.of(toaster,stopper,new Rectangle());
    
    @Test(expected=IllegalStateException.class)
    public void actionPerformed() {
        stepper.state = null;
        stepper.actionPerformed(null);
    }

    @Test
    public void attack_leads_to_sustain() {
        stepper.state = attack;
        waitFor(stepper,sustain);
    }

    
    @Test
    public void decay_leads_to_stop() {
        stepper.state = decay;
        waitFor(stepper,stop);
    }

    @Test
    public void sustain_leads_to_decay() {
        stepper.state = sustain;
        waitFor(stepper,decay);
    }

    @Test
    public void start_leads_to_attack() {
        stepper.state = start;
        waitFor(stepper,attack);
    }

    @Test
    public void stop_calls_stopper() {
        stepper.state = stop;
        stopper.stop = false;
        stepper.actionPerformed(null);
        assertTrue(stopper.stop);
    }

    void waitFor(AnimationStepper stepper, Animation.State target) {
        for (int i=0; i<10000; i++) {
            stepper.actionPerformed(null);
            if (stepper.state == target) {
                return;
            }
        }
        fail();
    }

}
