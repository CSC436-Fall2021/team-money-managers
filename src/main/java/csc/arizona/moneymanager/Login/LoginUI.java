package csc.arizona.moneymanager.Login;

import csc.arizona.moneymanager.MainUI.TestMainUI;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @apiNote the dummy username is "username" and the password is "1"
 */
public class LoginUI extends Application {

    private static final DatabaseHandler database = new DatabaseHandler();
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return the text and text fields necessary for the user to login in to the money management
     * site
     */
    private static VBox createLogin() {
        VBox loginTextFields = new VBox();
        loginTextFields.setAlignment(Pos.CENTER);
        loginTextFields.setSpacing(10);
        Text loginStatus = new Text();
        Text login = new Text("Username");
        TextField loginField = new TextField();
        loginField.setMaxWidth(150);
        Text password = new Text("Password");
        TextField passwordField = new TextField();
        passwordField.setMaxWidth(150);
        HBox hBox = new HBox(createLoginButton(loginField, passwordField, loginStatus),
                createAddUserButton());
        hBox.setAlignment(Pos.CENTER);
        loginTextFields.getChildren().addAll(loginStatus, new Text("Welcome!"), login, loginField,
                password, passwordField, hBox);
        return loginTextFields;
    }

    /**
     * creates the button that will log the user into the main ui by checking if the login and
     * password are valid
     *
     * @param loginField    the username the user types
     * @param passwordField the password the user uses
     * @param loginStatus   the message board that will tell the user any issues with the login
     * @return the login button
     */
    private static Button createLoginButton(TextField loginField, TextField passwordField,
                                            Text loginStatus) {
        Button loginButton = new Button("Login");
        loginButton.setOnMouseClicked(event -> {
            if (loginField.getText().equals("username") && passwordField.getText().equals("1")) {
                TestMainUI test = new TestMainUI();
                test.start(stage);
            } else if (database.userExists(loginField.getText())) {
                loginStatus.setText("username does not exist");
            } else if (!database.validateUser(loginField.getText(), passwordField.getText())) {
                loginStatus.setText("invalid password");
            } else {
                loginStatus.setText("");
                //TODO move view into the main UI with this accounts specific data
            }
        });
        return loginButton;
    }

    /**
     * creates a new stage that will ask for the new users' username, password, and a retyping of
     * the password
     */
    private static void newUser() {
        Stage stage = new Stage();
        stage.setTitle("Add user");
        BorderPane pane = new BorderPane();
        Scene newScene = new Scene(pane, 300, 160);
        VBox box = new VBox();
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
        Button addUser = createAddUserButton(userField, passField, rePassField, stage);
        box.getChildren().addAll(username, userField, password, passField,
                passwordCheck, rePassField, addUser);
        pane.getChildren().add(box);
        stage.setScene(newScene);
        stage.show();
    }

    /**
     * creates the button that will tell the user whether the credentials given are valid
     * for the new account
     *
     * @param userField   the username the user decided they wanted
     * @param passField   the password the user wants to use to login in with
     * @param rePassField the password again in case the user made a spelling error the first time
     * @param stage       the stage to close when the users type in a valid account
     * @return a button that will allow the users credentials to make a new account
     */
    private static Button createAddUserButton(TextField userField, TextField passField,
                                              TextField rePassField, Stage stage) {
        Button addUser = new Button("Add User");
        addUser.setMinWidth(100);
        addUser.setOnMouseClicked(event -> {
            if (userField.getText().isBlank())
                failureAlert("username");
            else if (passField.getText().isBlank())
                failureAlert("password");
            else if (rePassField.getText().isBlank())
                failureAlert("re-enter password");
            else if (!passField.getText().equals(rePassField.getText()))
                passwordsNotSame();
            else {
                database.addUser(userField.getText(), passField.getText());
                stage.close();
            }
        });
        return addUser;
    }

    /**
     * @return button that will open a new window to add in a new user
     */
    private static Button createAddUserButton() {
        Button addUser = new Button("new user");
        addUser.setOnMouseClicked(event -> newUser());
        return addUser;
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

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        BorderPane pane = new BorderPane();
        pane.setCenter(createLogin());
        Scene scene = new Scene(pane, 600, 500);
        primaryStage.setTitle("Money Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
