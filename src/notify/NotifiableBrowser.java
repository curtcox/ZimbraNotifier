package notify;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import strings.ForkedString;

/**
 * Notifier that opens a browser to the given page.
 * @author curt
 */
public final class NotifiableBrowser implements Notifiable {

    final URI uri;

    private NotifiableBrowser(URI uri) {
        this.uri = uri;
    }

    public static NotifiableBrowser of(String uri) {
        try {
            return new NotifiableBrowser(new URI(uri));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deliverNotification(ForkedString message) {
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }	
    }

}
