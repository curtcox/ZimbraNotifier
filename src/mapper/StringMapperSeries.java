package mapper;

import strings.ForkedString;

public class StringMapperSeries {

    public static StringMapper of(final StringMapper... mappers) {
        return new StringMapper(){
            @Override
            public ForkedString transform(ForkedString forked) {
                for (StringMapper mapper : mappers) {
                    forked = mapper.transform(forked);
                }
                return forked;
            }
        };
    }

}
