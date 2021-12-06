package csc.arizona.moneymanager;

import csc.arizona.moneymanager.Login.LoginUI;
import csc.arizona.moneymanager.MainUI.MainUI;
import csc.arizona.moneymanager.MainUI.UserSetting;
import csc.arizona.moneymanager.TransactionUI.TableTransaction;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import csc.arizona.moneymanager.TransactionUI.TransactionUI;
import csc.arizona.moneymanager.database.DatabaseHandler;
import csc.arizona.moneymanager.database.User;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Carter Boyd
 * <p>
 * The Controller is the link between both the login and the Main UI's to the databse as well as
 * a way for the Scenes to switch between the two.
 * </p>
 */
public class Controller extends Application {

    private static final DatabaseHandler database = new DatabaseHandler();
    private static Stage stage;
    private static MainUI test;
    private static User currentUser;

    public static void connectToDatabase() {
        database.connectToDatabase();
//        DatabaseHandler.turnLoggerOff();
    }

    /**
     * tells the program who is currently logged in
     *
     * @param user the user that is logged in
     */
    public static void logInUser(String user) {
        try {
            currentUser = database.getUserData(user, false);
            if (currentUser == null) {
                currentUser = new User(user);
                updateUserData(currentUser, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        // read from database: budget, category.

        UserSetting userSettings = currentUser.getSettings();


        test.setUserSettings(userSettings);
        test.setTransactionPane(new TransactionUI(userSettings.getCustomCategory()));
        //test.setServicesPane(new AboutInfo());

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
     * Shows transaction history for all transactions.
     */
    public static void showHistory(){
        showHistory(null, null); // non-specified dates
    }

    public static void showHistory(LocalDate start, LocalDate end)  {
        Stage reportPopUp = new Stage();
        reportPopUp.setTitle("Report");
        BorderPane bp = new BorderPane();
        Scene reportScene = new Scene(bp, 600,600);
        Style.addStyling(reportScene);

        try{
            currentUser = database.getUserData(currentUser.getUsername(), false);
        } catch (Exception e){
            System.err.println("Getting user data failed.");
        } finally {
            TextField searchBox = new TextField();
            TableView reportTable = getHistoryTable(start, end, searchBox);
            VBox topBox = new VBox();

            HBox labelBox = new HBox();
            labelBox.setAlignment(Pos.CENTER);
            Label label = new Label("Transaction History");
            label.setFont(new Font("Arial", 20));
            label.setPadding(new Insets(0, 0, 10, 0));
            labelBox.getChildren().add(label);

            HBox searchHBox = new HBox();
            searchHBox.setAlignment(Pos.CENTER_RIGHT);
            searchBox.setPrefSize(120,3);
            searchHBox.getChildren().addAll(new Label("Search: "), searchBox);

            topBox.getChildren().addAll(labelBox, searchHBox);

            HBox removeTransactionBox = new HBox();
            removeTransactionBox.setAlignment(Pos.CENTER);
            Button removeTransactionButton = new Button("Remove Transaction");
            removeTransactionButton.setOnAction(e -> {
                TableTransaction tableTransaction = (TableTransaction) reportTable.getSelectionModel().getSelectedItem();
                if (tableTransaction != null && confirmTransactionDeletion()) {
                    removeTransactionFromHistory(tableTransaction);
                    bp.setCenter(getHistoryTable(start, end, searchBox)); // updating table
                }
            });
            removeTransactionBox.getChildren().add(removeTransactionButton);

            bp.setTop(topBox);
            bp.setCenter(reportTable);
            bp.setBottom(removeTransactionBox);
            bp.setPadding(new Insets(10, 10, 10, 10));

            reportPopUp.setScene(reportScene);
            reportPopUp.show();
        }
    }

    /**
     * Displays a pop-up alert informing the user that they are about to delete
     * one of their transactions and allowing them to confirm if they want to do that.
     *
     * @return true if user clicks confirm, false if user clicks cancel.
     */
    private static boolean confirmTransactionDeletion() {
        Alert popup = new Alert(Alert.AlertType.CONFIRMATION);
        popup.setTitle("Deleting Transaction");
        popup.setContentText("You are about to delete a transaction");

        Optional<ButtonType> result = popup.showAndWait();
        ButtonType clicked = result.orElse(ButtonType.CANCEL);
        return clicked == ButtonType.OK;
    }

    /**
     * Gets a table containing transaction history between the specified start and end dates.
     * @param start the report start date
     * @param end the report end date
     * @return the TableView with the transaction between the given dates.
     */
    private static TableView getHistoryTable(LocalDate start, LocalDate end, TextField searchBox){
        List<Transaction> transactions = new ArrayList<>(currentUser.getTransactions());

        for (Transaction t: currentUser.getTransactions()){
            if (start != null) {
                if (!(t.getDate().isAfter(start))) {
                    transactions.remove(t);
                }
            }
            if (end != null) {
                if (!(t.getDate().isBefore(end))) {
                    transactions.remove(t);
                }
            }
        }

        return transactionTableView(transactions, searchBox);
    }

    /**
     * Removes the given transaction from the database.
     * @param transaction the transaction to remove.
     */
    private static void removeTransactionFromHistory(TableTransaction transaction){
        System.out.printf("Removing transaction with date: %s category: %s amount: %s\n", transaction.getDate(), transaction.getCategeroy(), transaction.getAmount());
        database.removeTransaction(currentUser, transaction, false);
    }

    private static TableView transactionTableView(List<Transaction> transactions,TextField searchBox){
        TableView tv = new TableView();
        tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv.setPlaceholder(new Label("No report to display"));

        TableColumn<TableTransaction, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        TableColumn<TableTransaction, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categeroyProperty());

        TableColumn<TableTransaction, String> memoColumn = new TableColumn<>("Memo");
        memoColumn.setCellValueFactory(cellData -> cellData.getValue().memoProperty());

        // Using Number object and ReadOnlyDoubleWrapper to format 'Amount' column in dollars format
        TableColumn<TableTransaction, Number> amountColumn = new TableColumn<>("Amount");
        amountColumn.setCellValueFactory(cellData ->
                new ReadOnlyDoubleWrapper(Double.parseDouble(cellData.getValue().getAmount())));
        amountColumn.setCellFactory(tc -> new TableCell<TableTransaction, Number>() {
            @Override
            protected void updateItem(Number value, boolean empty) {
                super.updateItem(value, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%01.2f", value.doubleValue()));
                }
            }
        });
        amountColumn.setStyle( "-fx-alignment: CENTER-RIGHT;");

        ObservableList<TableTransaction> observableList;

        ArrayList<TableTransaction> temp = new ArrayList<>();

        for (Transaction t: transactions){
            temp.add(new TableTransaction(t.getDate().toString(), t.getCategory(), Double.toString(t.getAmount()), t.getMemo()));
        }



        observableList = FXCollections.observableList(temp);

        FilteredList<TableTransaction> filtered = new FilteredList<>(observableList, b-> true);

        searchBox.textProperty().addListener((obsv, old, newVal) -> {
            filtered.setPredicate(tableTransaction ->{
                if (!(newVal == null || newVal.isBlank() || newVal.isEmpty())) {
                    String searchKeyword = newVal.toLowerCase();
                    if (tableTransaction.getMemo().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    } else if (tableTransaction.getCategeroy().toLowerCase().indexOf(searchKeyword) > -1){
                        return true;
                    }
                    return false;
                } else {
                    return true;
                }
            });
            SortedList<TableTransaction> sorted = new SortedList<>(filtered);
            sorted.comparatorProperty().bind(tv.comparatorProperty());
            tv.setItems(sorted);
        });

        tv.setItems(observableList);
        tv.getColumns().addAll(dateColumn, categoryColumn,memoColumn, amountColumn);

        return tv;
    }

    /**
     * removes the account from the database
     */
    public static void removeAccount() {
        Stage removeAccount = new Stage();
        removeAccount.setTitle("remove account");
        BorderPane pane = new BorderPane();
        Scene newScene = new Scene(pane, 400, 375);
        Style.addStyling(newScene);

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
        Button deleteUser = deleteButton(userField, passField, rePassField, removeAccount);
        Button cancel = new Button("Cancel");
        cancel.setMinWidth(100);
        HBox buttons = new HBox(deleteUser, cancel);
        cancel.setOnMouseClicked(event -> removeAccount.close());
        buttons.setAlignment(Pos.CENTER);

        VBox box = new VBox();
        box.setPadding(new Insets(25)); // adding outside padding
        box.setSpacing(10);
        box.getChildren().addAll(confirm, username, userField, password, passField, passwordCheck,
                rePassField, buttons);
        pane.setCenter(box);
        removeAccount.setScene(newScene);
        removeAccount.show();
    }

    /**
     * iterates through transactions to add up the total amount of the user
     * @return the total spending of the user
     */
    public static double getTotalSpent() {
        double totalAmount = 0;
        for (Transaction trans : currentUser.getTransactions())
            totalAmount += trans.getAmount();
        return totalAmount;
    }

    /**
     * iterates through transactions to add up the total amount of the user
     * @return the total spending of the user
     */
    public static double getBudget() {
        return currentUser.getSettings().getBudget();
    }

    /**
     * @return grabs the user
     */
    public static User getUser() {
        return currentUser;
    }

    /**
     * button that will verify that the user wants to delete his account
     *
     * @param userField     the input of the username
     * @param passField     the input of the password
     * @param rePassField   the re-entered password
     * @param removeAccount the stage that will close if the user successfully deletes the account
     * @return a button that can delete the account
     */
    private static Button deleteButton(TextField userField, TextField passField, TextField rePassField, Stage removeAccount) {
        Button deleteUser = new Button("Delete account");
        deleteUser.setMinWidth(100);
        deleteUser.setOnMouseClicked(event -> {
            if (!userField.getText().equals(currentUser.getUsername()))
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
        return deleteUser;
    }

    /**
     * creates an alert and tells the user the specific error
     *
     * @param issue the specific issie the user caused
     */
    public static void failureAlert(String issue) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Style.addStyling(alert);
        alert.setTitle("Alert");
        alert.setContentText("please enter a valid " + issue);
        alert.setHeaderText(issue + " not valid");
        alert.showAndWait();
    }

    /**
     * sends an alert if the two passwords were not the same
     */
    public static void passwordsNotSame() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Style.addStyling(alert);
        alert.setTitle("Alert");
        alert.setContentText("please enter passwords again");
        alert.setHeaderText("passwords are not the same");
        alert.showAndWait();
    }

    /**
     * adds a transaction from the transaction.java to be added to the current user
     * @param transaction the transaction being added to the user
     */
    public static void addTransaction(Transaction transaction) {
        currentUser.addTransactions(transaction);
        database.updateUserData(currentUser, false);
    }

    /**
     * @return the percentage that has been spent to the user
     */
    public static double getBudgetPercent() {
        return getTotalSpent() / currentUser.getSettings().getBudget();
    }

    /**
     * adds up the total amount of money spent on a category
     *
     * @param category the category being checked for
     * @return the amount of money spent in that category
     */
    public static double getCategorySpent(String category) {
        double totalAmount = 0;
        for (Transaction trans : currentUser.getTransactions())
            if (trans.getCategory().equals(category))
                totalAmount += trans.getAmount();
        return totalAmount;
    }

    public static boolean updateUserData(User user, boolean testing) {
        return database.updateUserData(user, testing);
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
