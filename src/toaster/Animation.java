package toaster;

import java.awt.Rectangle;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import prefs.PreferencesPersistence;
import threads.Threads;

/***
 * Class that manages the animation
 */
final class Animation
    implements Stopper
{

    final Timer timer;
    final Toaster toaster;
    final String user;
    final Rectangle bounds;
    static Animation current;
    private static final Object lock = new Object();

    enum State {
        start, attack, sustain, decay, stop
    }
    
    private Animation(String user, Toaster toaster,Rectangle bounds) {
        this.user = user;
        this.bounds = bounds;
        this.toaster = toaster;
        ActionListener taskPerformer = AnimationStepper.of(toaster,this,bounds);
        final int delay = 25;
        timer = new Timer(delay, taskPerformer);
    }

    static void of(String user, Toaster toaster, Rectangle dim) {
        synchronized (lock) {
            if (current!=null) {
                current.stop();
            }
            Animation animation = new Animation(user,toaster,dim);
            current = animation;
            animation.timer.start();
        }
    }

    @Override
    public void stop() {
        PreferencesPersistence.setBounds(user, bounds);
        timer.stop();
        Threads.runOnEDT(new Runnable() {
            @Override
             public void run() {
                toaster.setVisible(false);
                toaster.dispose();
             }
        });
    }

}