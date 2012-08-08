package zimbra.soap;

import java.util.ArrayList;
import java.util.List;
import mapper.StringMapper;
import strings.ForkedString;
import zimbra.ZimbraEmail;

/**
 *
 * @author Curt
 */
final class EmailDataToEmail implements StringMapper {
    
    public static EmailDataToEmail of() {
        return new EmailDataToEmail();
    }

    @Override
    public ForkedString<ZimbraEmail> transform(ForkedString fork) {
        List<ZimbraEmail> list = new ArrayList<ZimbraEmail>();
        String author; String title = ""; long date = 0; String description = "";
        int size = fork.parts.size();
        for (int i=0; i<size; i++) {
            EmailData data = data(fork,i);
            if (data.nodeName.equals("fr")) {
                description = get(data,"value");
            }
            if (data.nodeName.equals("su")) {
                title = get(data,"value");
            }
            if (data.nodeName.equals("c")) {
                date = Long.parseLong(get(data,"d"));
            }
            if (data.nodeName.equals("e")) {
                while (i + 1 < size && data(fork,i+1).nodeName.equals("e")) {
                    i++;
                }
                data = data(fork,i);
                author = get(data,"a");
                list.add(ZimbraEmail.of(author, title, date, description));
                title = ""; date = 0; description = "";
            }
        }
        return ForkedString.fromList(ZimbraEmail.class,list);
    }
    
    EmailData data(ForkedString fork, int i) {
        Object part = fork.parts.get(i);
        return (EmailData) part;
    }
    
    String get(EmailData data, String key) {
        String value = data.values.get(key);
        if (value==null) {
            return "";
        }
        return value;
    }
}
