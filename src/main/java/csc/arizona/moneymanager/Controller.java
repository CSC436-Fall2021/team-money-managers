package csc.arizona.moneymanager;

import csc.arizona.moneymanager.Login.LoginUI;
import csc.arizona.moneymanager.MainUI.MainUI;
import csc.arizona.moneymanager.database.DatabaseHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Carter Boyd
 * <p>
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

    /**
     * tells the program who is currently logged in
     * @param user the user that is logged in
     */
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
        currentUser = null;
        stage.setScene(LoginUI.createScene());
    }

    /**
     * removes the account from the database
     */
    public static void removeAccount() {
        Stage removeAccount = new Stage();
        removeAccount.setTitle("remove account");
        BorderPane pane = new BorderPane();
        Scene newScene = new Scene(pane, 300, 260);
        Label confirm = new Label("Are you sure you want to delete your account?");
        confirm.setAlignment(Pos.CENTER);
        confirm.setMinWidth(300);
        Label username = new Label("Username");
        username.setMinWidth(200);
        Label password = new Label("Password");
        password.setMinWidth(200);
        Label passwordCheck = new Label("Re-enter Password");
        passwordCheck.setMinWidth(200);
        TextField userField = new TextField();
        TextField passField = new TextField();
        TextField rePassField = new TextField();
        userField.setMinWidth(200);
        passField.setMinWidth(200);
        rePassField.setMinWidth(200);
        Button deleteUser = new Button("Delete account");
        deleteUser.setMinWidth(100);
        deleteUser.setOnMouseClicked(event -> {
            if (!userField.getText().equals(currentUser))
                failureAlert("username");
            else if (!correctCredentials(userField.getText(), passField.getText()))
                failureAlert("password");
            else if (!passField.getText().equals(rePassField.getText()))
                passwordsNotSame();
            else {
                database.deleteUser(userField.getText(), passField.getText());
                mainUIToLogin();
                removeAccount.close();
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setMinWidth(100);
        HBox buttons = new HBox(deleteUser, cancel);
        cancel.setOnMouseClicked(event -> removeAccount.close());
        VBox box = new VBox();
        box.setSpacing(10);
        box.getChildren().addAll(confirm, username, userField, password, passField, passwordCheck
                , rePassField, buttons);
        pane.getChildren().add(box);
        removeAccount.setScene(newScene);
        removeAccount.show();
    }

    /**
     * creates an alert and tells the user the specific error
     *
     * @param issue the specific issie the user caused
     */
    private static void failureAlert(String issue) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setContentText("please enter a valid " + issue);
        alert.setHeaderText(issue + " not valid");
        alert.showAndWait();
    }

    /**
     * sends an alert if the two passwords were not the same
     */
    private static void passwordsNotSame() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Alert");
        alert.setContentText("please enter passwords again");
        alert.setHeaderText("passwords are not the same");
        alert.showAndWait();
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
        // of it must be created, this will probably have to change //pass it through with the
        // user settings as the parameter
        connectToDatabase();
        stage = primaryStage;
        primaryStage.setScene(LoginUI.createScene());
        primaryStage.setTitle("Money Managers");
        primaryStage.show();
    }
}