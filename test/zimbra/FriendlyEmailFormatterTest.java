package zimbra;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class FriendlyEmailFormatterTest {
    
    @Test
    public void asolutions_email() {
        String author = "curt.cox@asolutions.com";
        String actual = FriendlyEmailFormatter.format(author,false);
        
        assertTrue(actual,actual.contains("Curt Cox"));
    }

    @Test
    public void asolutions_email_with_no_dot() {
        String author = "curt@asolutions.com";
        String actual = FriendlyEmailFormatter.format(author,false);
        
        assertTrue(actual,actual.contains("Curt"));
    }

    @Test
    public void non_asolutions_email() {
        String author = "curtcox@gmail.com";
        String actual = FriendlyEmailFormatter.format(author,false);
        
        assertTrue(actual.contains("curtcox@gmail.com"));
    }

}
