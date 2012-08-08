package zimbra;

import java.util.Collections;
import org.junit.Test;
import strings.ForkedString;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class OnlyNewZimbraEmailsTest {

    @Test
    public void empty_transforms_to_empty() {
        OnlyNewZimbraEmails mapper = OnlyNewZimbraEmails.of();
        
        ForkedString actual = mapper.transform(ForkedString.EMPTY);
        
        assertEquals(ForkedString.EMPTY,actual);
    }

    @Test
    public void one_email_seen_once_is_new() {
        OnlyNewZimbraEmails mapper = OnlyNewZimbraEmails.of();
        ZimbraEmail email = ZimbraEmail.of("author", "title", 42, "description");
        ForkedString<ZimbraEmail> emails = ForkedString.fromList(ZimbraEmail.class,Collections.singletonList(email));
        ForkedString actual = mapper.transform(emails);
        
        assertEquals(emails,actual);
    }

    @Test
    public void one_email_seen_twice_is_old() {
        OnlyNewZimbraEmails mapper = OnlyNewZimbraEmails.of();
        ZimbraEmail email = ZimbraEmail.of("author", "title", 42, "description");
        ForkedString<ZimbraEmail> emails = ForkedString.fromList(ZimbraEmail.class,Collections.singletonList(email));
        mapper.transform(emails);
        ForkedString<ZimbraEmail> actual = mapper.transform(emails);
        
        assertEquals(1,actual.parts.size());
        ZimbraEmail old = actual.parts.get(0);
        assertTrue(email.isSameAs(old));
        assertTrue(old.old);
    }

}
