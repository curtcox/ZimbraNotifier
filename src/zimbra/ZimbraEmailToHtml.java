package zimbra;

import java.util.ArrayList;
import java.util.List;
import mapper.StringMapper;
import strings.ForkedString;

public class ZimbraEmailToHtml
    implements StringMapper
{

    public static StringMapper of() {
        return new ZimbraEmailToHtml();
    }

    private ZimbraEmailToHtml() {}

    @Override
    public ForkedString transform(ForkedString string) {
        return emails(string);
    }

    ForkedString emails(ForkedString string) {
        if (string.parts.isEmpty()) {
            return ForkedString.of("<h1>No unread mail</h1>");
        }
        List<String> list = new ArrayList<String>();
        for (Object part : string.parts) {
            list.add(format((ZimbraEmail) part));
        }
        return ForkedString.fromList(String.class,list);
    }

    String format(ZimbraEmail email) {
        boolean old = email.old;
        return String.format(
            "<b>%s</b> -- %s <br/><b>%s</b> <br/>%s<br/><br/>",
            FriendlyEmailFormatter.format(email.author,email.old),
            red(FriendlyDateFormatter.format(email.date),old),
            black(email.title,old),black(email.description,old)
        );
    }

    static String black(String text, boolean old) {
        return old ? gray(text) : text;
    }

    static String red(String text, boolean old) {
        return old ? font("aa6666",text) : font("aa0000",text);
    }
    
    static String gray(String text) {
        return font("888888",text);
    }

    static String font(String color, String text) {
        return String.format("<font color=#%s>%s</font>",color,text);
    }

}
