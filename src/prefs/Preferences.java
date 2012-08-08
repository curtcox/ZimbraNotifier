package prefs;

import java.util.Iterator;
import util.InfiniteIterator;

/**
 *
 * @author Curt
 */
public interface Preferences {

    boolean isBeepEnabled();
    boolean showWindowOnNewMail();    
    boolean showWindowOnIconify();    

    public class Adapter {
        
        final Preferences prefs;
        
        private Adapter(Preferences prefs) {
            this.prefs = prefs;
        }
        
        public static Adapter of(Preferences prefs) {
            return new Adapter(prefs);
        }
        
        public final Iterator<Boolean> isBeepEnabled() {
            return new InfiniteIterator<Boolean>() {
                @Override public Boolean next() {
                    return prefs.isBeepEnabled();
                }
            };
        }
        
        public final Iterator<Boolean> showWindowOnNewMail() {
            return new InfiniteIterator<Boolean>() {
                @Override public Boolean next() {
                    return prefs.showWindowOnNewMail();
                }
            };
        }

        public final Iterator<Boolean> showWindowOnIconify() {
            return new InfiniteIterator<Boolean>() {
                @Override public Boolean next() {
                    return prefs.showWindowOnIconify();
                }
            };
        }

    }
}
