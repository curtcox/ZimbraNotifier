package zimbra;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Curt
 */
final class FriendlyDateFormatter {
    
    static final DateFormat format = new SimpleDateFormat("hh:mm aaa EEE, MMM d yyyy");
    static String format(long date) {
        long delta = System.currentTimeMillis() - date;
        return friendly(delta) + " (" + format.format(new Date(date)) + ")";
    }

    static String friendly(long delta) {
        long minute = 60 * 1000;
        long hour = minute * 60;
        long day = hour * 24;
        if (delta<minute) {
            return "less than 1 minute ago";
        }
        if (delta<2 * minute) {
            return "1 minute ago";
        }
        if (delta<hour) {
            return (delta / minute) + " minutes ago";
        }
        if (delta<2 * hour) {
            return "1 hour ago";
        }
        if (delta<day) {
            return (delta / hour) + " hours ago";
        }
        if (delta<2 * day) {
            return "1 day ago";
        }
        return (delta / day) + " days ago";
    }

}
