package zimbra;

/**
 *
 * @author Curt
 */
final class FriendlyEmailFormatter {

    static String format(String author, boolean old) {
        if (author.contains("@asolutions.com")) {
            String[] names = author.split("@")[0].split("\\.");
            String first = capitalize(names[0]);
            String last = names.length < 2 ? "" : capitalize(names[1]);
            return green(first + " " + last,old);
        } else {
            return blue(author,old);
        }
    }

    static String capitalize(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1);
    }
    
    static String green(String text, boolean old) {
        return old ? font("66aa66",text) : font("00aa00",text);
    }

    static String blue(String text, boolean old) {
        return old ? font("6666aa",text) : font("0000aa",text);
    }
    
    static String font(String color, String text) {
        return String.format("<font color=#%s>%s</font>",color,text);
    }

}
