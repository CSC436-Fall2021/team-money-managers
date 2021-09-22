package csc.arizona.moneymanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setLeft(createAddUserButton());
        pane.setCenter(createLogin());
        Scene scene = new Scene(pane, 600, 500);
        primaryStage.setTitle("Money Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @return the text and text fields necessary for the user to login in to the money management
     * site
     */
    private static VBox createLogin() {
        VBox loginTextFields = new VBox();
        Text login = new Text("Username");
        TextField loginField = new TextField();
        loginField.setMaxWidth(150);
        Text password = new Text("Password");
        TextField passwordField = new TextField();
        passwordField.setMaxWidth(150);
        Button loginButton = new Button("Login"); //TODO make login button functional
        loginTextFields.getChildren().addAll(new Text("Welcome"), login, loginField, password,
                passwordField, loginButton);
        return loginTextFields;
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
        Button ok = new Button("Ok");
        ok.setMinWidth(40);
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
        ok.setOnMouseClicked(event -> {
            if (userField.getText().isBlank())
                failureAlert(username.getText());
            else if (passField.getText().isBlank())
                failureAlert(passwordCheck.getText());
            else if (password.getText().isBlank())
                failureAlert(password.getText());
            else if (!passField.getText().equals(rePassField.getText()))
                passwordsNotSame();
            else {
                //TODO add users information to the account class when that is created
                stage.close();
            }
        });
        box.getChildren().addAll(username, userField, password, passField,
                passwordCheck, rePassField, ok);
        pane.getChildren().add(box);
        stage.setScene(newScene);
        stage.show();
    }

    /**
     * @return button that will open a new window to add in a new user
     */
    private static Button createAddUserButton() {
        Button addUser = new Button("add user");
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
}
