package notify;

import mapper.NullMapper;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
public class PartitioningChangeDetectorTest {

    @Test
    public void changed() {
        PartitioningChangeDetector detector = PartitioningChangeDetector.of(
                NullMapper.of());
        
        assertTrue(detector.changed(forked("old"), forked("new")));	
        assertTrue(detector.changed(forked("1"), forked("1","2")));	

        assertFalse(detector.changed(forked("new"), forked("new")));	
        assertFalse(detector.changed(forked("1","2"), forked("1")));	
        assertFalse(detector.changed(forked("1","2"), forked("2")));	
    }

    ForkedString forked(String... lines) {
        return ForkedString.forked(lines);
    }

}
