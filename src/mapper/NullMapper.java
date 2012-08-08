package mapper;

import strings.ForkedString;

public final class NullMapper implements StringMapper {

    private NullMapper() {}

    public static NullMapper of() {
        return new NullMapper();	
    }

    @Override
    public ForkedString transform(ForkedString string) {
        return string;
    }

}
