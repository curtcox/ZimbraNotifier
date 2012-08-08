package mapper;

import strings.ForkedString;
import util.ExceptionFormatter;

/**
 * Wraps another mapper to provide exception reporting.
 */
public final class ExceptionTransformStringMapper
    implements StringMapper
{

    final StringMapper mapper;
    
    private ExceptionTransformStringMapper(StringMapper mapper) {
        this.mapper = mapper;
    }

    public static StringMapper of(StringMapper mapper) {
        return new ExceptionTransformStringMapper(mapper);
    }

    @Override
    public ForkedString transform(ForkedString string) {
        try {
            if (isError(string)) {
                return string;
            }
            return mapper.transform(string);
        } catch (Exception e) {
            return ExceptionFormatter.format(e);
        }
    }

    private boolean isError(ForkedString forked) {
        String string = forked.string;
        return string.contains(ExceptionFormatter.HEADER);
    }

}
