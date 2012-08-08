package auth;

/**
 * Simple, immutable authentication source.
 * @author Curt
 */
public final class SimpleAuthenticationSource
    implements AuthenticationSource
{

    final String user;
    final String password;
    
    private SimpleAuthenticationSource(String user, String password) {
        this.user = user;
        this.password = password;
    }
    
    public static AuthenticationSource of(String user, String password) {
        return new SimpleAuthenticationSource(user,password);
    }
    
    @Override
    public String getUser() { 
        return user;
    }

    @Override
    public char[] getPassword() {
        return password.toCharArray();
    }

    @Override
    public void refreshCredentials() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
