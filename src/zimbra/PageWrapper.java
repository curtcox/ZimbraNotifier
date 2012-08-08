package zimbra;

import auth.AuthenticationSource;
import mapper.StringMapper;
import strings.ForkedString;

/**
 * Wraps the given HTML in tags and adds user info to make it a page.
 * @author Curt
 */
final class PageWrapper 
    implements StringMapper
{
    final AuthenticationSource authentication;
    
    private PageWrapper(AuthenticationSource authentication) {
        this.authentication = authentication;
    }
    
    static StringMapper of(AuthenticationSource authentication) {
        return new PageWrapper(authentication);
    }
    
    @Override
    public ForkedString transform(ForkedString html) {
        String title = authentication.getUser();
        return page(title,body(html));
    }

    ForkedString page(String user, String body) {
        return ForkedString.forked(user,"<html>" + body + "</html>");
    }

    String body(ForkedString html) {
        return "<body>" + html + "<body>";
    }
    
}
