package strings;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class StringSourceTest {
    
    @Test
    public void next_always_returns_constant_when_not_url() {
        String value = "unlikely value";
        Iterator<ForkedString> same = StringSource.of(value,null,null);
        assertTrue(same.hasNext());
        assertEquals(value,same.next().string);
        assertTrue(same.hasNext());
        assertEquals(value,same.next().string);
    }
}
