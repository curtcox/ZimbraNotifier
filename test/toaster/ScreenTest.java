package toaster;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class ScreenTest {
    
    @Test
    public void width_is_greater_than_zero() {
        assertTrue(Screen.width > 0);
    }

    @Test
    public void height_is_greater_than_zero() {
        assertTrue(Screen.height > 0);
    }

}
