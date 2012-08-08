package zimbra;

import java.util.Collections;
import strings.ForkedString;
import static zimbra.ZimbraEmailException.Type.*;
import static zimbra.ZimbraExceptionFormatter.UserStatusUpdate.*;
/**
 *
 * @author Curt
 */
public final class ZimbraExceptionFormatter {
    
    enum UserStatusUpdate {
        
        AUTH_FAILED(
            "Authorization Failure",
            "<h1>Authorization Failure</h1>" +
            "<b>Verify your user name and password.</b>"
        ),
        
        NETWORK_ERROR(
           "Network Failure",
           "<h1>Network Failure</h1>" +
           "<b>Uable to access the Zimbra server due to network problems.</b>" 
        ),
        
        INTERNAL_ERROR(
          "Internal Error",
          "<h1>Internal Error</h1>"
        );

        final String status;
        final String page;
        
        UserStatusUpdate(String status, String page) {
            this.status = status;
            this.page = page;    
        }
    }

    static ForkedString errorPage(String message,ZimbraEmailException e) {
        return ForkedString.of(message,Collections.singletonList(e),ZimbraEmailException.class);
    }

    static ForkedString format(ZimbraEmailException e) {
        return errorPage(findUpdate(e).page,e);
    }

    static UserStatusUpdate findUpdate(ZimbraEmailException e) {
        if (e.type==AUTH) {
            return AUTH_FAILED;
        }
        if (e.type==UNKNOWN_HOST     ||
            e.type==SOCKET           ||
            e.type==TIMEOUT          ||
            e.type==NO_ROUTE_TO_HOST ||
            e.type==IO)
        {
            return NETWORK_ERROR;
        }
        return INTERNAL_ERROR;
    }

}
