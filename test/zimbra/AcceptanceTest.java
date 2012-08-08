package zimbra;

import strings.ForkedString;
import notify.NotifiableList;
import java.util.*;
import auth.AuthenticationSource;
import notify.Notifiable;
import notify.NotificationMonitor;
import notify.TimedNotifier;
import util.All;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class AcceptanceTest {

    final byte[] NO_EMAIL = zimbraEmails(""); 
    
    final byte[] ONE_EMAIL = 
        zimbraEmails(
               "<item>" +
                   "<title>title</title>" +
                   "<description>What this is about</description>"  +
                   "<author>curt.cox@asolutions.com</author>"  +
                   "<pubDate>Tue, 23 Aug 2011 14:02:08 -0500</pubDate>" +
               "</item>");  	

    byte[] zimbraEmails(String items) {
        return ("<?xml version='1.0'?>" +
        "<rss version='2.0'>" +
          "<channel>" +
               "<title>title</title>" +
               "<link>http://www.zimbra.com</link>" +
               "<description>description</description>" +
               "<generator>generator</generator>" +
               items +
             "</channel>" +   
           "</rss>")
        .getBytes();  	
    }
    
    @Test
    public void user_is_notified_when_new_mail_arrives() throws Exception {
        MockLoginDialog dialog = new MockLoginDialog();
        dialog.user = "user";
        NotifiableList toaster = NotifiableList.of();
        NotificationMonitor monitor = NotificationMonitor.of();
        long date = 0L;
        String author = "curt.cox@asolutions.com";
        Iterator<ForkedString<ZimbraEmail>> messages = All.ofTimes(
                ForkedString.from(ZimbraEmail.class,ZimbraEmail.of(author, "title", date, "description")),2);
        
        AsolutionsZimbraNotifierAppLauncher.of(dialog,toaster,messages,monitor,0)
                .launch();
        sleep(200);
                
        List<ForkedString> notifications = toaster.list;
        assertTrue(notifications.toString(),notifications.get(0).string.contains("Curt Cox"));
    }
    
    @Test
    public void count_is_updated_on_new_email() throws Exception {
        MockLoginDialog dialog = new MockLoginDialog();
        dialog.user = "user";
        Notifiable toaster = NotifiableList.of();
        NotificationMonitor monitor = NotificationMonitor.of();
        long date = 0L;
        String author = "curt.cox@asolutions.com";
        Iterator<ForkedString<ZimbraEmail>> messages = All.ofTimes(
                ForkedString.from(ZimbraEmail.class,ZimbraEmail.of(author, "title", date, "description")),2);
        
        AsolutionsZimbraNotifierAppLauncher.of(dialog,toaster,messages,monitor,0).launch();
        
        sleep(200);
        assertEquals("user",dialog.title);
        assertEquals(1,dialog.images.size());
    }

    @Test
    public void toaster_is_shown_on_new_email() {
        MockLoginDialog dialog = new MockLoginDialog();
        dialog.user = "user";
        NotifiableList toaster = NotifiableList.of();
        NotificationMonitor monitor = NotificationMonitor.of();

        long date = 0L;
        String author = "curt.cox@asolutions.com";
        Iterator<ForkedString<ZimbraEmail>> messages = All.of(
                ForkedString.from(ZimbraEmail.class,ZimbraEmail.of(author, "title", date, "description")));
        
        AsolutionsZimbraNotifierAppLauncher.of(dialog,toaster,messages,monitor,0).launch();
        
        assertEquals(1,toaster.list.size());
    }

    @Test
    public void toaster_is_shown_on_iconify() {
        MockLoginDialog dialog = new MockLoginDialog();
        dialog.user = "user";
        NotifiableList toaster = NotifiableList.of();
        NotificationMonitor monitor = NotificationMonitor.of();
        long date = 0L;
        String author = "curt.cox@asolutions.com";
        Iterator<ForkedString<ZimbraEmail>> messages = All.of(
                ForkedString.from(ZimbraEmail.class,ZimbraEmail.of(author, "title", date, "description")));
        
        AsolutionsZimbraNotifierAppLauncher.of(dialog,toaster,messages,monitor,0).launch();
    
        dialog.iconify();
        
        assertEquals(1,toaster.list.size());
    }

    @Test
    public void toaster_is_not_shown_on_new_email_when_user_prefers_not() {
        fail();
    }

    @Test
    public void toaster_is_not_shown_on_iconify_when_user_prefers_not() {
        fail();
    }

    @Test
    public void login_message_is_presented_when_credentials_expire() {
        fail();
    }

    @Test
    public void polling_stops_when_authorization_is_rejected() {
        fail();
    }

    @Test
    public void count_is_updated_when_rejected_authorization_is_corrected() {
        fail();
    }

    @Test
    public void count_is_updated_when_expired_authorization_is_resupplied() {
        fail();
    }

    private void sleep(int i) throws InterruptedException {
        Thread.sleep(i);
    }


}
