package mapper;

import java.util.Iterator;
import strings.AbstractStringSource;
import strings.ForkedString;

/**
 * Applies a mapper to a string source to produce another string source.
 * @author Curt
 */
public final class Apply {
    
    public static Iterator<ForkedString> to(
            final Iterator<ForkedString> inner, final StringMapper mapper) {
        return new AbstractStringSource() {

            @Override
            public ForkedString next() {
                return mapper.transform(inner.next());
            }
        };
    } 
}
