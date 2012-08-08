package zimbra;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class ZimbraEmailSorterTest {

    @Test
    public void most_recent_first() {
        List<ZimbraEmail> emails = new ArrayList<ZimbraEmail>();
        ZimbraEmail email0 = ZimbraEmail.of("", "", 1, "");
        ZimbraEmail email1 = ZimbraEmail.of("", "", 0, "");
        
        emails.add(email0);
        emails.add(email1);
        
        Collections.sort(emails,ZimbraEmailTimeSorter.TIME_COMPARATOR);
        
        assertEquals(email0,emails.get(0));
        assertEquals(email1,emails.get(1));
    }

    @Test
    public void new_first() {
        List<ZimbraEmail> emails = new ArrayList<ZimbraEmail>();
        ZimbraEmail email0 = ZimbraEmail.of("", "", 0, "");
        ZimbraEmail email1 = ZimbraEmail.of("", "", 0, "").withOld();
        
        emails.add(email1);
        emails.add(email0);
        
        Collections.sort(emails,ZimbraEmailTimeSorter.TIME_COMPARATOR);
        
        assertEquals(email0,emails.get(0));
        assertEquals(email1,emails.get(1));
    }

}
