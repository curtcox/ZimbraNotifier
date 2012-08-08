package zimbra.soap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Test;
import strings.ForkedString;
import zimbra.ZimbraEmail;

/**
 *
 * @author Curt
 */
public class EmailDataToEmailTest {

    @Test
    public void can_create() {
        assertNotNull(EmailDataToEmail.of());
    }

    @Test
    public void returns_email_with_right_info_when_normal_email() {
        
        EmailDataToEmail transformer = EmailDataToEmail.of();
        EmailData[] emailData = new EmailData[]{
            EmailData.of("su",map("value=subject")),
            EmailData.of("fr",map("value=body")),
            EmailData.of("c",map("d=1329918795000")),
            EmailData.of("e",map("d=Eric","a=eric.neunaber@asolutions.com","p=Eric Neunaber")),
        };
        ForkedString<EmailData> data = ForkedString.fromList(EmailData.class,Arrays.asList(emailData));
        
        ForkedString<ZimbraEmail> emails = transformer.transform(data);
        
        assertEquals(1,emails.parts.size());
        ZimbraEmail zimbra = emails.parts.get(0);
        assertEquals("eric.neunaber@asolutions.com",zimbra.author);
        assertEquals("subject",zimbra.title);
        assertEquals(1329918795000L,zimbra.date);
    }

    @Test
    public void returns_email_with_right_info_when_forward() {
        
        EmailDataToEmail transformer = EmailDataToEmail.of();
        EmailData[] emailData = new EmailData[]{
            EmailData.of("c",map("f=swu","id=3275","d=1332197959000","value=null","n=6","sf=1332197959000")),
            EmailData.of("su",map("value=Fwd: Peer Review")),
            EmailData.of("fr",map("value=-- Forwarded Message -- It is time for Karl Mueller's performance review.")),
            EmailData.of("e",map("d=Melissa","t=f","a=melissa.reddo@asolutions.com","p=Melissa Reddo","value=null")),
            EmailData.of("e",map("d=Curt","t=f","a=curt.cox@asolutions.com","p=Curt Cox","value=null")),
            EmailData.of("m",map("id=3325","value=null")),
        };
        ForkedString<EmailData> data = ForkedString.fromList(EmailData.class,Arrays.asList(emailData));
        
        ForkedString<ZimbraEmail> emails = transformer.transform(data);
        
        assertEquals(1,emails.parts.size());
        ZimbraEmail zimbra = emails.parts.get(0);
        assertEquals("curt.cox@asolutions.com",zimbra.author);
        assertEquals("Fwd: Peer Review",zimbra.title);
        assertEquals(1332197959000L,zimbra.date);
    }

    private Map<String, String> map(String... pairs) {
        Map<String,String> map = new HashMap();
        for (String pair : pairs) {
            String key = pair.split("=")[0];
            String value = pair.split("=")[1];
            map.put(key, value);
        }
        return map;
    }

}
