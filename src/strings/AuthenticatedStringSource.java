package strings;

import auth.AuthenticationSource;
import java.net.*;

/**
 * Wraps another StringSource to provide authentication. 
 */
final class AuthenticatedStringSource
    extends AbstractStringSource
{

    final AbstractStringSource inner;
    final AuthenticationSource authentication;
	
    private AuthenticatedStringSource(AbstractStringSource inner, AuthenticationSource authentication) {
        this.inner = inner;
        this.authentication = authentication;
    }

    static AuthenticatedStringSource of(AbstractStringSource inner, AuthenticationSource authentication) {
        return new AuthenticatedStringSource(inner,authentication);
    }
    
    @Override
    public ForkedString next() {
        setAuthenticator();
        return inner.next();
    }
    
    void setAuthenticator() {
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    authentication.getUser(),
                    authentication.getPassword()
                );
            }
        });
    }    

}
