package zimbra;

import notify.ChangeDetector;
import strings.ForkedString;

/**
 * Determines when the contents of Zimbra are considered to have changed..
 * @author Curt
 */
public final class ZimbraChangeDetector
    implements ChangeDetector
{
    final ChangeDetector email;
    
    private ZimbraChangeDetector(ChangeDetector email) {
        this.email = email;    
    }
    
    static ZimbraChangeDetector of(ChangeDetector email) {
        return new ZimbraChangeDetector(email);
    }
    
    @Override
    public boolean changed(ForkedString old, ForkedString newValue) {
        if (old.type!=newValue.type) {
            return true;
        }
        if (old.type==ZimbraEmailException.class) {
            return !old.string.equals(newValue.string);
        }
        return email.changed(old, newValue);
    }
    
}
