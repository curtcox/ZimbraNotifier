package zimbra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mapper.StringMapper;
import strings.ForkedString;

public final class ZimbraEmailTimeSorter
    implements StringMapper
{

    static final Comparator<ZimbraEmail> TIME_COMPARATOR = new Comparator<ZimbraEmail>(){

        @Override
        public int compare(ZimbraEmail a, ZimbraEmail b) {
            if (a.old && !b.old) return +1;
            if (!a.old && b.old) return -1;
            if (a.date > b.date) return -1;
            if (b.date > a.date) return +1;
            return 0;
        }
    };

    public static StringMapper of() {
        return new ZimbraEmailTimeSorter();
    }

    private ZimbraEmailTimeSorter() {}

    @Override
    public ForkedString<ZimbraEmail> transform(ForkedString forked) {
        List<ZimbraEmail> list = new ArrayList<ZimbraEmail>();
        for (Object part : forked.parts) {
            list.add((ZimbraEmail)part);
        }
        Collections.sort(list,TIME_COMPARATOR);
        return ForkedString.of(list.toString(),list,ZimbraEmail.class);
    }

}
