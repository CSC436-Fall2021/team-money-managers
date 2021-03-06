package csc.arizona.moneymanager.Login;

import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.Style;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @apiNote the dummy username is "username" and the password is "1"
 */
public class LoginUI {

    /**
     * @return the text and text fields necessary for the user to login in to the money management
     * site
     */
    private static VBox createLogin() {
        VBox loginTextFields = new VBox();
        loginTextFields.setAlignment(Pos.CENTER);
        loginTextFields.setSpacing(10);
        VBox welcomeBanner = getWelcomeBanner();
        Text loginStatus = new Text();
        Text login = new Text("Username");
        TextField loginField = new TextField();
        loginField.setMaxWidth(150);
        Text password = new Text("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(150);

        Button loginButton = createLoginButton(loginField, passwordField, loginStatus);
        loginField.setOnAction(e-> loginButton.fire());   // if user presses ENTER, will fire the login button
        passwordField.setOnAction(e->loginButton.fire()); // if user presses ENTER, will fire the login button

        HBox hBox = new HBox(loginButton, createAddUserButton());

        hBox.setAlignment(Pos.CENTER);
        loginTextFields.getChildren().addAll(
                loginStatus,
                welcomeBanner,
                login,
                loginField,
                password,
                passwordField,
                hBox);  // buttons

        return loginTextFields;
    }

    private static VBox getWelcomeBanner(){
        VBox welcomeBanner = new VBox();
        welcomeBanner.setAlignment(Pos.CENTER);
        welcomeBanner.setPadding(new Insets(10));
        welcomeBanner.setId("welcome-banner");
        welcomeBanner.getChildren().addAll(
            new Text("Welcome"),
            new Text("to"),
            new Text("Money Managers!"),
            new Text(" ") // spacing
        );

        return welcomeBanner;
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
    private static Button createLoginButton(TextField loginField, PasswordField passwordField,
                                            Text loginStatus) {
        Button loginButton = new Button("Login");

        loginButton.setOnAction(event -> {
            if (!Controller.userExists(loginField.getText()))
                loginStatus.setText("username does not exist");
            else if (!Controller.correctCredentials(loginField.getText(), passwordField.getText()))
                loginStatus.setText("invalid password");
            else {
                Controller.logInUser(loginField.getText());
                Controller.loginToMainUI();
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
        Scene newScene = new Scene(pane, 400, 300);
        Style.addStyling(newScene);

        VBox box = new VBox();
        box.setPadding(new Insets(25)); // adding outside padding
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

        // HBox to allow for centering of button
        HBox buttonBox = new HBox();
        Button addUser = createAddUserButton(userField, passField, rePassField, stage);
        buttonBox.getChildren().add(addUser);
        buttonBox.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonBox, new Insets(20));

        box.getChildren().addAll(username, userField, password, passField,
                passwordCheck, rePassField, buttonBox);
        pane.setCenter(box);
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
                Controller.failureAlert("username");
            else if (passField.getText().isBlank())
                Controller.failureAlert("password");
            else if (rePassField.getText().isBlank())
                Controller.failureAlert("re-enter password");
            else if (!passField.getText().equals(rePassField.getText()))
                Controller.passwordsNotSame();
            else if (Controller.userExists(userField.getText()))
                existingUsername();
            else {
                Controller.addUser(userField.getText(), passField.getText());
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
     * sends an alert if the username already exists in the database
     */
    private static void existingUsername() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Style.addStyling(alert);
        alert.setTitle("Alert");
        alert.setContentText("please pick a different username");
        alert.setHeaderText("username already exists");
        alert.showAndWait();
    }

    /**
     * creates a rectangle and the icon to appear in the beginning and then fade to the login functions
     *
     * @return the splash screen pane
     */
    private static Pane createSplashScreen() {
        Rectangle splashCover = new Rectangle(600, 500);
        splashCover.setFill(Color.LIGHTCYAN);
        splashCover.setX(0);
        splashCover.setY(0);
        File img = new File("src/main/java/csc/arizona/moneymanager/IMG/Icon.png");
        Image image = new Image(img.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setTranslateX(250);
        imageView.setTranslateY(200);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        createFade(splashCover);
        createFade(imageView);
        Pane pane = new Pane();
        pane.getChildren().addAll(splashCover, imageView);
        return pane;
    }

    /**
     * Creates a transition to keep the node still for three seconds, once that is over it will begin to fade in 2
     * seconds
     *
     * @param fade whatever object needs to disappear
     */
    private static void createFade(Node fade) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), fade);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> {
            FadeTransition end = new FadeTransition(Duration.seconds(2), fade);
            end.setFromValue(1);
            end.setToValue(0);
            end.setCycleCount(1);
            end.play();
        });
    }

    public static Scene createScene() {
        BorderPane pane = new BorderPane();
        pane.setCenter(createLogin());
        pane.getChildren().add(createSplashScreen());
        removeSplashScreen(pane);
        // Adding .css styling to login scene
        Scene scene = new Scene(pane, 600, 500);
        Style.addStyling(scene);
        return scene;
    }

    /**
     * removes the splash pane from the login so the user can press the buttons
     *
     * @param splashScreen the pane that holds all the splash screen contents
     */
    private static void removeSplashScreen(BorderPane splashScreen) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> splashScreen.getChildren().remove(1));
            }
        }, 5000); // time until the splash screen ends
    }
}
