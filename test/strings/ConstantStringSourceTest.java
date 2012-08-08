package strings;

import java.util.Iterator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class ConstantStringSourceTest {
    
    @Test
    public void next_always_returns_constant() {
        ForkedString value = ForkedString.of("unlikely value");
        Iterator<ForkedString> same = ConstantStringSource.of(value);
        assertTrue(same.hasNext());
        assertEquals(value,same.next());
        assertTrue(same.hasNext());
        assertEquals(value,same.next());
    }
}
