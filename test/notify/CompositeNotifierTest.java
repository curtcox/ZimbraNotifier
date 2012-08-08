/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package notify;

import strings.ForkedString;
import notify.Notifiable;
import notify.NotifiableComposite;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Curt
 */
public class CompositeNotifierTest {

    class MockNotifier implements Notifiable {

        ForkedString note;
        
        @Override
        public void deliverNotification(ForkedString note) {
            this.note = note;
        }
        
    }
    
    @Test
    public void notify_notifies_given_notifier() {
        MockNotifier mock = new MockNotifier();
        Notifiable notifier = NotifiableComposite.of(mock);
        
        ForkedString note = ForkedString.of("note");
        notifier.deliverNotification(note);
        
        assertEquals(note,mock.note);
    }
}
