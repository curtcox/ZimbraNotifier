package toaster;

import auth.SimpleAuthenticationSource;
import notify.NotifiableBrowser;
import strings.ForkedString;

final class ToasterExample
{

    /**
     * Simple Example...
     */
    public static void main(String[] args) throws Exception {
         Toaster.show(
             ForkedString.forked(
                 "Title","<html>" + body() + "</html>"),
             NotifiableBrowser.of("http://www.google.com/"),
             SimpleAuthenticationSource.of("user", "password")
        );
    }

    static String body() {
        String body = "";
         for (int i=0; i<15; i++) {
             body += "<h1>JToaster</h1>\r ";
         }
        return body;
    }
}
