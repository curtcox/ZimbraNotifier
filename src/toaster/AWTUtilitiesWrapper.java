package toaster;

import java.awt.Window;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

final class AWTUtilitiesWrapper {

    final Method mSetWindowOpacity = getMethod("setWindowOpacity", Window.class, float.class);
    final Method mSetWindowOpaque = getMethod("setWindowOpaque", Window.class, boolean.class);
    static final Logger LOG = Logger.getLogger(AWTUtilitiesWrapper.class.getName());

    Method getMethod(String name, Class<?>... parameterTypes) {
        try {
            return classForName("com.sun.awt.AWTUtilities")
                    .getMethod(name, parameterTypes);
        } catch (Exception e) {
            severe(e);
            return null;
        }
    }
    
    Class classForName(String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            severe(e);
            return null;
        }
    }
    
    private void set(Method method, Window window, Object value) {
        if (method == null) {
            return;
        }
        try {
            method.invoke(null, window, value);
        } catch (Exception e) {
            severe(e);
        }
    }
    
    static void severe(Throwable t) {
        LOG.log(Level.SEVERE, null, t);
    }

    void setWindowOpacity(Window window, float opacity) {
        set(mSetWindowOpacity, window, Float.valueOf(opacity));
    }
    
    void setWindowOpaque(Window window, boolean opaque) {
        set(mSetWindowOpaque, window, Boolean.valueOf(opaque));
    }
}