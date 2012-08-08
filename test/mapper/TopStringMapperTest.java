package mapper;

import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import strings.ForkedString;

/**
 */
public class TopStringMapperTest {

    final ForkedString EMPTY = ForkedString.fromList(String.class,Collections.EMPTY_LIST); 
    
    @Test
    public void top_1_returns_zero_parts_when_none_available() {
        TopStringMapper mapper = TopStringMapper.of(1);
        
        ForkedString actual = mapper.transform(EMPTY);
    
        assertEquals(0,actual.parts.size());
    }

    @Test
    public void top_10_returns_zero_parts_when_none_available() {
        TopStringMapper mapper = TopStringMapper.of(10);
        
        ForkedString actual = mapper.transform(EMPTY);
    
        assertEquals(0,actual.parts.size());
    }

}
