package zimbra;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class FriendlyDateFormatterTest {
    

    @Test
    public void friendly_times() {
        when(-10,"less than 1 minute ago");
        when(-1000 * 66, "1 minute ago");
        when(-1000 * 126, "2 minutes ago");
        when(-1000 * 601, "10 minutes ago");
        when(-1000 * 60 * 61, "1 hour ago");
        when(-1000 * 60 * 121, "2 hours ago");
        when(-1000 * 60 * 60 * 25, "1 day ago");
        when(-1000 * 60 * 60 * 49, "2 days ago");
    }

    void when(int timeDelta, String string) {
        long time = System.currentTimeMillis() + timeDelta;
        String formatted = FriendlyDateFormatter.format(time);
        String message = formatted + " should contain " + string; 
        assertTrue(message,formatted.contains(string));
    }
}
