package notify;

import java.awt.Toolkit;
import strings.ForkedString;

public final class NotifiableBeep
    implements Notifiable
{

    private NotifiableBeep() {}
    
    public static Notifiable of() {
        return new NotifiableBeep();
    }

    @Override
    public void deliverNotification(ForkedString html) {
        beep();
    }

    static void beep() {
         Toolkit.getDefaultToolkit().beep();     
    }
    
  public static void main(String args[]) {
      beep();
  }


}
