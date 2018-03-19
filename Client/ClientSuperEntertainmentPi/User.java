package Client.ClientSuperEntertainmentPi;


/**
 * Contains the user information.
 *
 * @author Simon
 */
public class User {

    private static String username;
    private static String password;
    private static String token;


    public static String getUsername() {
        return username;
    }


    public static void setUsername(String newUsername) {
        username = newUsername;
    }


    public static String getPassword() {
        return password;
    }


    public static void setPassword(String newPassword) {
        password = newPassword;
    }


    public static String getToken() {
        return token;
    }


    public static void setToken(String newToken) {
        token = newToken;
    }
}
