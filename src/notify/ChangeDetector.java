package notify;

import strings.ForkedString;

/**
 *
 * @author Curt
 */
public interface ChangeDetector {
    
     boolean changed(ForkedString old, ForkedString newValue);

}
