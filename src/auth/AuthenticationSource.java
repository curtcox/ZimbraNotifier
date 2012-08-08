package auth;

/**
 *
 * @author Curt
 */
public interface AuthenticationSource {
    
    String getUser();
    
    char[] getPassword();

    void refreshCredentials();
}
