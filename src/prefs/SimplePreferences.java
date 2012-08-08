package prefs;

/**
 *
 * @author Curt
 */
public final class SimplePreferences
    implements Preferences
{
    final boolean beep;
    final boolean newEmail;
    final boolean iconify;

    private SimplePreferences(boolean beep, boolean newEmail, boolean iconify) {
        this.beep = beep;
        this.newEmail = newEmail;
        this.iconify = iconify;
    }
    
    public static Preferences beepNewIconify(boolean beep, boolean newEmail,boolean iconify) {
        return new SimplePreferences(beep,newEmail,iconify);
    } 
    
    @Override public boolean isBeepEnabled() { return beep; }
    @Override public boolean showWindowOnNewMail() { return newEmail; }
    @Override public boolean showWindowOnIconify() { return iconify; }
    
}
