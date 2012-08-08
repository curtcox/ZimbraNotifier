package zimbra;

import auth.AuthenticationSource;

/**
 *
 * @author Curt
 */
class MockAuthenticationSource implements AuthenticationSource {

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public char[] getPassword() {
        return null;
    }

    @Override
    public void refreshCredentials() {
    }
    
}
