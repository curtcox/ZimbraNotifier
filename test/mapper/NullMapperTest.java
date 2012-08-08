package mapper;

import org.junit.Test;
import static org.junit.Assert.*;
import strings.ForkedString;

/**
 *
 */
public class NullMapperTest {
    

    @Test
    public void transform_returns_input() {
        ForkedString input = ForkedString.forked("1","2");
        NullMapper instance = NullMapper.of();
        ForkedString expected = input;
        ForkedString result = instance.transform(input);
        assertEquals(expected, result);
    }
}
