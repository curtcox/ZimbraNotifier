package mapper;

import strings.ForkedString;

/**
 * Maps instances of a specific type using a given mapper while letting
 * others pass through.
 * @author Curt
 */
public final class TypeMapper
    implements StringMapper
{
    final Class clazz;
    final StringMapper mapper;
    
    private TypeMapper(Class clazz, StringMapper mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }
    
    public static TypeMapper of(Class clazz, StringMapper mapper) {
        return new TypeMapper(clazz,mapper);
    }
    
    @Override
    public ForkedString transform(ForkedString fork) {
        if (!clazz.isAssignableFrom(fork.type)) {
            return fork;
        }
        return mapper.transform(fork);
    }
    
}
