package threads;

import java.awt.EventQueue;

/**
 *
 * @author Curt
 */
public final class Threads {
    
    public static void runOnEDT(Runnable runnable) {
        EventQueue.invokeLater(runnable);
    }
}
