package csc.arizona.moneymanager.Login;

/**
 * @author Carter Boyd
 */
public class Account {

    private final String username;
    private final String password;
    //TODO add in the users account data here

    /**
     * Basic constructor that will set the username and password of the account
     *
     * @param username the username of the account
     * @param password the password of the account
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    /**
     * When the user wants to sign in this will be the verifier of whether
     * they are the user with the correct password
     *
     * @param input the users input for what should be the password
     * @return true if the input was the password, false otherwise
     */
    public boolean isCorrectPassword(String input) {
        return password.equals(input);
    }
}
