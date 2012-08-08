package strings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Curt
 */
public class ForkedStringTest {
    
    @Test
    public void of_string() {
        String string = "value";
        ForkedString forked = ForkedString.of(string);
        
        assertEquals(1, forked.parts.size());
        assertEquals(string,forked.string);
        assertEquals(string,forked.toString());
    }

    @Test
    public void from_empty_list() {
        List<?> list = Collections.EMPTY_LIST;
        ForkedString forked = ForkedString.fromList(String.class,list);

        assertEquals(0, forked.parts.size());
        assertEquals("",forked.string);
        assertEquals("",forked.toString());
    }

    @Test
    public void of_String_List_uses_given_values() {
        String string = "testing testing boo!";
        List<?> parts = Collections.singletonList("McCleoud");
        ForkedString forked = ForkedString.of(string, parts,String.class);
        
        assertEquals(string, forked.string);
        assertEquals(parts, forked.parts);
    }

    @Test
    public void forked_creates_forked_string_with_given_parts() {
        String[] lines = new String[] {"one","two"};
        ForkedString forked = ForkedString.forked(lines);
        
        assertEquals(Arrays.asList(lines), forked.parts);
    }
    
    @Test
    public void two_forked_strings_are_equal_if_given_the_same_input() {
        assertEquals(ForkedString.of("same"),ForkedString.of("same"));
        assertEquals(ForkedString.of("same").hashCode(),
                     ForkedString.of("same").hashCode());
    }
}
