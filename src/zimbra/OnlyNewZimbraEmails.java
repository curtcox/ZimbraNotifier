package zimbra;

import java.util.ArrayList;
import java.util.List;
import mapper.StringMapper;
import strings.ForkedString;

/**
 *
 * @author Curt
 */
final class OnlyNewZimbraEmails 
    implements StringMapper
{

    static OnlyNewZimbraEmails of() {
        return new OnlyNewZimbraEmails();
    }

    List<ZimbraEmail> previous = new ArrayList<ZimbraEmail>();
    
    @Override
    public ForkedString<ZimbraEmail> transform(ForkedString forked) {
        List<ZimbraEmail> list = new ArrayList();
        for (Object next : forked.parts) {
            ZimbraEmail email = (ZimbraEmail) next;
            if (previous.contains(email)) {
                list.add(email.withOld());
            } else {
                list.add(email);
            }
        }
        previous = forked.parts;
        return ForkedString.fromList(ZimbraEmail.class,list);
    }
    
}
