package csc.arizona.moneymanager;

import csc.arizona.moneymanager.Login.LoginUI;
import csc.arizona.moneymanager.MainUI.MainUI;
import csc.arizona.moneymanager.database.DatabaseHandler;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Carter Boyd
 *
 * The Controller is the link between both the login and the Main UI's to the databse as well as
 * a way for the Scenes to switch between the two.
 */
public class Controller extends Application {

    private static final DatabaseHandler database = new DatabaseHandler();
    private static Stage stage;
    private static MainUI test;
    private static String currentUser;

    public static void connectToDatabase() {
        database.connectToDatabase();
        DatabaseHandler.turnLoggerOff();
    }

    public static void logInUser(String user) {
        currentUser = user;
    }

    /**
     * goes into the database handler to see if user does exist
     *
     * @param username the username being looked for
     * @return true if the database found the username, false otherwise
     */
    public static boolean userExists(String username) {
        return database.userExists(username);
    }

    /**
     * checks the database to see if the username's password is correct
     *
     * @param username the username of the account
     * @param password the password the user typed in
     * @return true if the user credentials are correct, false otherwise
     */
    public static boolean correctCredentials(String username, String password) {
        return database.validateUser(username, password);
    }

    /**
     * adds the newly created user into the database
     *
     * @param username the username of the new account
     * @param password the password of the new account
     */
    public static void addUser(String username, String password) {
        database.addUser(username, password);
    }

    /**
     * once the user has logged in the user's view should change so that the login view will
     * convert into the users main view account
     */
    public static void loginToMainUI() {
        //TODO this method will also have to include the data that the main UI will need from the
        // database
        stage.setScene(test.getScene());
    }

    /**
     * once the user is finished with the account and wishes to log out the main view should switch
     * to the login view to be ready for the next login
     */
    public static void mainUIToLogin() {
        stage.setScene(LoginUI.createScene());
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     */
    @Override
    public void start(Stage primaryStage) {
        test = new MainUI(); // because currently the MainUI is not static in any way, an instance
        // of it must be created, this will probably have to change
        connectToDatabase();
        stage = primaryStage;
        primaryStage.setScene(LoginUI.createScene());
        primaryStage.setTitle("Money Managers");
        primaryStage.show();
    }
}
