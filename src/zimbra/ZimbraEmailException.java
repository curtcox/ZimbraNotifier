package zimbra;

import java.io.IOException;
import java.net.NoRouteToHostException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author Curt
 */
public final class ZimbraEmailException 
    extends RuntimeException
{
    public final Type type;
    
    public enum Type {
        IO, SOCKET, AUTH, UNKNOWN_HOST, NO_ROUTE_TO_HOST, TIMEOUT, UNKNOWN
    }
    
    private ZimbraEmailException(Type type, Throwable t) {
        super(t);
        this.type = type;
    }

    public static ZimbraEmailException from(Exception e) {
        if (e instanceof UnknownHostException) {
            return new ZimbraEmailException(Type.NO_ROUTE_TO_HOST,e);
        }
        if (e instanceof NoRouteToHostException) {
            return new ZimbraEmailException(Type.UNKNOWN_HOST,e);
        }
        if (e instanceof SocketException) {
            return new ZimbraEmailException(Type.SOCKET,e);
        }
        if (e instanceof IOException) {
            String message = e.getMessage();
            if (message.contains("Connection timed out")) {
                return new ZimbraEmailException(Type.TIMEOUT,e);
            }
            if (message.contains("Server returned HTTP response code: 500")) {
                return new ZimbraEmailException(Type.AUTH,e);
            }
            return new ZimbraEmailException(Type.IO,e);
        }
        return new ZimbraEmailException(Type.UNKNOWN,e);
    }

}
