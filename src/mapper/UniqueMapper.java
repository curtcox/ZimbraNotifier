package mapper;

import java.util.ArrayList;
import java.util.List;

import strings.ForkedString;

public final class UniqueMapper
    implements StringMapper
{
 
    private UniqueMapper() {}

    public static UniqueMapper of() {
        return new UniqueMapper();
    }

    @Override
    public ForkedString transform(ForkedString forked) {
        List list = new ArrayList();
        for (Object part : forked.parts) {
            if (!list.contains(part)) {
                list.add(part);
            }
        }
        return ForkedString.of(list.toString(),list,forked.type);
    }

}
