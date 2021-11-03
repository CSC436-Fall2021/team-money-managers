package csc.arizona.moneymanager.TransactionUI;

import csc.arizona.moneymanager.Controller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides input functionality for creating transactions.
 *
 * Input for date of a transaction includes UI to pick a date.
 *
 *
 * Input for amount of transaction is a text field, with a dropbox to specify its category.
 *
 * This is displayed as a single row of graphical elements.
 *
 * A new transaction will be created if the user clicks on the enter button or
 * presses enter after inserting an amount. The amount field is cleared after entering.
 *
 */
public class TransactionUI extends GridPane {

    DatePicker dateInput;
    CategoryList categories; // combination: default + user
    ComboBox<String> categoryDropDown;
    TextField amountInput;
    private final Label totalAmount;


    // Initialize TransactionUI with customCategories passed in from a userSettings class.
    public TransactionUI(List<String> customCategories) {

        // date input
        dateInput = new DatePicker();
        dateInput.setEditable(false); // must pick date from UI.

        // transaction amount and category input
        // load in default categories list
        categories = new CategoryList("src/main/java/csc/arizona/moneymanager/TransactionUI/default_categories.txt");

        // load in additional categories from categories stored for user
        // from a userSettings.
        categories.addCategories(customCategories);

        categoryDropDown = new ComboBox(FXCollections.observableArrayList(categories.getCategories()));
        amountInput = new TextField();
        Button enterButton = new Button("Enter");

        amountInput.setPromptText("Amount");

        // disable custom input in dropboxes
        categoryDropDown.setEditable(false);

        amountInput.setOnAction(new EnterTransactionHandler());
        enterButton.setOnAction(new EnterTransactionHandler());

        totalAmount = getTotalAmountSpent();

        add(new Label("Transactions"), 1, 0);

        add(new Label("Date"), 0, 1);
        add(new Label("Category"), 1, 1);
        add(new Label("Amount"), 2, 1);

        add(dateInput, 0, 2);
        add(categoryDropDown, 1, 2);
        add(amountInput, 2, 2);
        add(enterButton, 3, 2);
        add(totalAmount, 4, 2);

        setHgap(5);
        setVgap(5);




    }

    public List<String> getDefaultCategories() {
        return categories.getDefaultCategories();
    }

    // Call this from mainUI when user adds a new custom category to their account.
    public void addNewCategory(String category) {
        categories.addCategory(category);
    }

    public void addCategory(String newCategory) {
        categories.addCategory(newCategory);
        categoryDropDown.setItems(FXCollections.observableArrayList(categories.getCategories()));
    }

    /**
     * goes into the User transaction history and adds up all transaction
     * @return Label of the transaction amount, red in it has gone over budget
     */
    private Label getTotalAmountSpent() {
        double totalAmount = Controller.getBudget();
        Label budgetDisplay = new Label('$' + Double.toString(totalAmount));
        if (totalAmount > Controller.getUser().getSettings().getBudget()) {
            budgetDisplay.setTextFill(Color.RED);
            budgetDisplay.setText(budgetDisplay.getText() + " OVER BUDGET");
        }
        return budgetDisplay;
    }

    /**
     * Creates a Transaction object using the information given by the user.
     */
    private class EnterTransactionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            LocalDate date = dateInput.getValue();
            String category = categoryDropDown.getValue();

            /*
                Error handling for missing information
             */
            if (date == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing transaction information");
                alert.setContentText("Enter a date.");
                alert.showAndWait();
                //System.out.println("Enter a date.");
                return;
            }

            if (category == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing transaction information");
                alert.setContentText("Select a category.");
                alert.showAndWait();
                //System.out.println("Pick a category.");
                return;
            }

            if (amountInput.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Missing transaction information");
                alert.setContentText("Enter an amount.");
                alert.showAndWait();
                //System.out.println("Enter an amount.");
                return;
            }

            double amount = Double.parseDouble(amountInput.getText());

            Transaction toAdd = new Transaction(date, category, amount);


            Controller.addTransaction(toAdd);
            Label newTotal = getTotalAmountSpent();
            totalAmount.setTextFill(newTotal.getTextFill());
            totalAmount.setText(newTotal.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(String.format("Transaction details:\n\nDate: %s\nCategory: %s\nAmount: %.2f",
                    toAdd.getDate().toString(), toAdd.getCategory(), toAdd.getAmount()));
            alert.setHeaderText("Transaction addded");
            alert.showAndWait();
            //System.out.println(toAdd.getDate());
            //System.out.println(toAdd.getCategory());
            //System.out.println(toAdd.getAmount());

            // reset input field for amount, but leave date and category as is.
            amountInput.clear();

        }
    }
}
