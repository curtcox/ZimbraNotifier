package strings;
import java.util.Iterator;
import util.ExceptionFormatter;

/**
 * Wraps another source and turns any exceptions thrown into an HTML table.
 */
public final class ExceptionTransformStringSource
    extends AbstractStringSource
{

    final Iterator<ForkedString> source;
    
    private ExceptionTransformStringSource(Iterator<ForkedString> source) {
        this.source = source; 
    }

    public static Iterator<ForkedString> of(Iterator<ForkedString> source) {
        return new ExceptionTransformStringSource(source);
    }

    @Override
    public ForkedString next() {
        try {
            return source.next();
        } catch (Exception e) {
            return ExceptionFormatter.format(e);
        }
    }

}
