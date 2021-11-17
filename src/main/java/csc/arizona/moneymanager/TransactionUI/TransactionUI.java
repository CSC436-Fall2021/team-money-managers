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
import java.util.Optional;

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

    private DatePicker dateInput;
    private CategoryList categories; // combination: default + user
    private ComboBox<String> categoryDropDown;
    private TextField amountInput;
    private TextField memoInput;
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
        memoInput = new TextField();
        Button enterButton = new Button("Enter");

        amountInput.setPromptText("Amount");
        memoInput.setPromptText("Memo");


        // disable custom input in dropboxes
        categoryDropDown.setEditable(false);

        amountInput.setOnAction(new EnterTransactionHandler());
        memoInput.setOnAction(new EnterTransactionHandler());
        // TODO: add warning for memo cutoff / prevent entering new chars if more than Transaction's memo max.
        enterButton.setOnAction(new EnterTransactionHandler());


        totalAmount = getTotalAmountSpent();

        // Pane Header/Title
        add(new Label("Transactions"), 1, 0);

        // Top row
        add(new Label("Date"), 0, 1);
        add(new Label("Category"), 1, 1);
        add(new Label("Amount"), 2, 1);
        add(new Label("Memo"), 3, 1);

        // Bottom row
        add(dateInput, 0, 2);
        add(categoryDropDown, 1, 2);
        add(amountInput, 2, 2);
        add(memoInput, 3, 2);
        add(enterButton, 4, 2);
        add(totalAmount, 5, 2);

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
        double totalAmount = Controller.getTotalSpent();
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
            String missingInfoMsg = "";
            if (date == null) {
                missingInfoMsg += "Missing date.\n";
                //showAlert("Enter a date.");
                //return;
            }

            if (category == null) {
                missingInfoMsg += "Missing category.\n";
                //showAlert("Select a category.");
                //return;
            }

            if (amountInput.getText().isEmpty()) {
                missingInfoMsg += "Missing amount.\n";
                //showAlert("Enter an amount.");
                //return;
            }

            // show all errors (good for transactionUI with small # possible errors)
            if (!missingInfoMsg.isEmpty()) {
                showAlert(missingInfoMsg.stripTrailing());
                return;
            }

            double amount = Double.parseDouble(amountInput.getText());
            String memo = memoInput.getText();

            Transaction toAdd = new Transaction(date, category, amount, memo);
            if (amount + Controller.getTotalSpent() > Controller.getBudget() * .9) {
                Alert overBudgetWarning = new Alert(Alert.AlertType.CONFIRMATION);
                overBudgetWarning.setTitle("approaching budget");
                overBudgetWarning.setContentText("You are close to or already going over your budget");
                Optional<ButtonType> result = overBudgetWarning.showAndWait();
                ButtonType button = result.orElse(ButtonType.CANCEL);
                if (button == ButtonType.CANCEL)
                    return;
            }
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

        /**
         * sends an alert when the user fails to select all three of the transaction requirements
         *
         * @param content the specific context of what the user failed to add into the transaction
         */
        private static void showAlert(String content) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Missing transaction information");
            alert.setContentText(content);
            alert.showAndWait();
        }
    }
}
