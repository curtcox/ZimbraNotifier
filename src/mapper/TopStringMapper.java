package mapper;

import java.util.ArrayList;
import java.util.List;
import strings.ForkedString;

public final class TopStringMapper
    implements StringMapper
{
 
    final int size;

    private TopStringMapper(int size) {
        this.size = size;
    }

    public static TopStringMapper of(int size) {
        return new TopStringMapper(size);
    }

    @Override
    public ForkedString transform(ForkedString forked) {
        List list = new ArrayList();
        List<?> parts = forked.parts;
        if (size>=parts.size()) {
            return ForkedString.of(parts.toString(),parts,forked.type);
        }
        list.addAll(parts.subList(0, size -1));
        return ForkedString.of(list.toString(),list,forked.type);
    }

}
