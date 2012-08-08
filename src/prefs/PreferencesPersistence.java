package prefs;

import java.awt.Rectangle;
import login.ILoginDialog;

/**
 * For persisting things that the user can set.
 * @author Curt
 */
public final class PreferencesPersistence
{
    final static java.util.prefs.Preferences prefs =
                 java.util.prefs.Preferences.userNodeForPackage(PreferencesPersistence.class);
     
    final static String BEEP = ".beep";
    final static String ON_ICONIFY = ".on_iconify";
    final static String ON_NEW_EMAIL = ".on_new_email";
    final static String X = ".bounds.x";
    final static String Y = ".bounds.y";
    final static String W = ".bounds.w";
    final static String H = ".bounds.h";
    final static int UNSET = -1;
    
    public static PreferencesPersistence of() {
        return new PreferencesPersistence();
    }

    public Preferences getPrefsForUser(String user) {
        boolean beep = prefs.getBoolean(user + BEEP, true);
        boolean onNew = prefs.getBoolean(user + ON_NEW_EMAIL, true);
        boolean onIconify = prefs.getBoolean(user + ON_ICONIFY, true);
        return SimplePreferences.beepNewIconify(beep,onNew,onIconify);
    }

    public void setPrefsForUser(ILoginDialog login) {
        String user = login.getUser();
        prefs.putBoolean(user + BEEP, login.isBeepEnabled());
        prefs.putBoolean(user + ON_NEW_EMAIL, login.showWindowOnNewMail());
        prefs.putBoolean(user + ON_ICONIFY, login.showWindowOnIconify());
    }
    
    /**
     * If no preferred bounds exist, use the given rectangle.
     * If preferred bounds exist, store them into the given rectangle.
     * Either way, return the given rectangle.
     */
    public static Rectangle getBounds(String user, Rectangle rect) {
        int x = getInt(user,X);
        if (x==UNSET) {
            setBounds(user,rect);
        } else {
            int y = getInt(user,Y);
            int w = getInt(user,W);
            int h = getInt(user,H);
            rect.setBounds(x,y,w,h);
        }
        return rect;
    }

    public static Rectangle setBounds(String user,Rectangle rect) {
        setInt(user,X,rect.x);
        setInt(user,Y,rect.y);
        setInt(user,W,rect.width);
        setInt(user,H,rect.height);
        return rect;
    }
    
    private static void setInt(String user, String key, int value) {
        prefs.putInt(user + key, value);
    }
    
    private static int getInt(String user, String key) {
        return prefs.getInt(user+key, UNSET);
    }

}
