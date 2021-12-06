package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Style;
import csc.arizona.moneymanager.TransactionUI.CategoryList;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class WhatifUI extends ServicesView {

    private final int MAX_WHATIF_CONTENT = 5;// maximum number of concurrent expense rows displayed
    private int expenseRowCount;             // count of current rows of expenses displayed
    private double currentBudget;            // current budget
    private Label currentBudgetAmount;       // label that displays current budget amount
    private String currentBudgetDuration;    // current budget duration
    private LocalDate currentBudgetStartDate;// budget start date for calculating remaining time for expenses
    private CategoryList categoryList;       // list of expense categories
    private double whatifspending;           // current what-if spending amount
    private Label whatifSpendingLabel;       // label that displays current what-if spending amount
    private VBox expenseVBox;                // outer container for expense rows

    /**
     * Constructor.
     * @param currentBudget current (non-whatif) budget.
     */
    public WhatifUI(double currentBudget, String currentBudgetDuration, LocalDate currentBudgetStartDate, double currentSpending, CategoryList categoryList) {
        super("What-if?", "Return");
        this.currentBudget = currentBudget;
        this.currentBudgetAmount.setText(String.format("$%01.2f", currentBudget));
        updateWhatifSpending(currentSpending);
        this.currentBudgetDuration = currentBudgetDuration;
        this.currentBudgetStartDate = currentBudgetStartDate;
        this.categoryList = categoryList;
        this.expenseRowCount = 0;

        addExpenseRow();

    }

    @Override
    void initContent() {
        // Budget and what-if spending box
        HBox budgetsBox = new HBox();
        budgetsBox.setAlignment(Pos.CENTER);
        budgetsBox.setPadding(MainUI.PADDING);
        budgetsBox.setSpacing(MainUI.PADDING.getLeft() * 2);

        // Current budget amount box
        VBox currentBudgetBox = new VBox();
        currentBudgetBox.setAlignment(Pos.CENTER);
        Label currentBudgetHeader = new Label("Current Budget");
        currentBudgetAmount = new Label();
        currentBudgetBox.getChildren().addAll(currentBudgetHeader, currentBudgetAmount);

        // What-if spending amount box
        VBox whatifSpendingBox = new VBox();
        whatifSpendingBox.setAlignment(Pos.CENTER);
        Label whatifSpendingHeader = new Label("What-if? Spending");
        whatifSpendingLabel = new Label();
        updateWhatifSpending(currentBudget);
        whatifSpendingBox.getChildren().addAll(whatifSpendingHeader, whatifSpendingLabel);

        budgetsBox.getChildren().addAll(currentBudgetBox, whatifSpendingBox);

        content.addRow(1, budgetsBox);

        // Header row for expenses
        HBox headerBox = new HBox();
        headerBox.setSpacing(MainUI.PADDING.getLeft() * 2);

        Label headerSpacing = new Label ("");
        Label checkBoxHeader = new Label ("Enabled");
        Label categoryHeader = new Label ("Category");
        Label durationHeader = new Label ("     Duration");
        Label amountHeader = new Label ("      Amount");

        headerBox.getChildren().addAll(headerSpacing, checkBoxHeader, categoryHeader, durationHeader, amountHeader);
        content.addRow(2, headerBox);
        expenseVBox = new VBox();
        content.addRow(3, expenseVBox);
    }

    /**
     * Updates the what-if spending label.
     * @param spending the amount to update the label to.
     */
    private void updateWhatifSpending(double spending){
        whatifspending = spending;
        String spendingStr = "$" + BudgetUI.budgetToString(whatifspending);
        whatifSpendingLabel.setText(spendingStr);
    }

    /**
     * Shows an alert dialog.
     * @param type the Alert.AlertType
     * @param title the title of the dialog
     * @param header the main header text
     * @param content the content text
     */
    public void showAlert(Alert.AlertType type, String title, String header, String content){
        Alert alert = new Alert(type);
        Style.addStyling(alert);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Adds an expense row to the what-if UI for use by user.
     */
    public void addExpenseRow(){
        if(expenseRowCount >= MAX_WHATIF_CONTENT){
            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Help",
                    "Cannot add more expenses.",
                    "A maximum of "+ MAX_WHATIF_CONTENT +" expenses may be added a time."
            );
            return;
        }

        HBox catBox = new HBox();
        catBox.setSpacing(MainUI.PADDING.getLeft() * 2);
        catBox.setAlignment(Pos.BOTTOM_CENTER);

        Label leftSpacing = new Label("   ");

        // Checkbox to enable or disable use in calculating what-if budget
        CheckBox enabled = new CheckBox();
        enabled.setIndeterminate(false);

        // Combo box for user to select category (sole function is to allow user to organize what-if expenses)
        ComboBox<String> categoryComboBox = new ComboBox<>(FXCollections.observableArrayList(categoryList.getCategories()));

        String subDuration = getSubDuration();
        String[] durationList = {"Once", subDuration};

        // ComboBox for user to select duration of expense (one-time expense or each sub-duration period)
        ComboBox<String> durationComboBox = new ComboBox<>(FXCollections.observableArrayList(durationList));

        TextField amountTF = new TextField();
        //Label amountLabel = new Label(String.format("$%01.2f", amount));

        // Button for user to remove expense category row
        Button removeExpenseRow = new Button("Remove");
        removeExpenseRow.setOnAction(e-> {
            if(expenseRowCount > 1) { // cannot remove last expense
                if(enabled.isSelected()) { // if expense to be removed is checked, then uncheck to remove from spending
                    checkBoxUpdate(false, durationComboBox.getValue(), amountTF.getText());
                }
                expenseVBox.getChildren().remove(catBox); // remove from UI
                expenseRowCount--; // decrease row count
            }
        });

        // Adding elements to UI
        catBox.getChildren().addAll(leftSpacing, enabled, categoryComboBox, durationComboBox, amountTF, removeExpenseRow);
        expenseVBox.getChildren().add(catBox);
        expenseRowCount++; // increase row count

        // Setting action for checkbox to enable expense calculation (if invalid amount, will deselect box)
        enabled.setOnAction(e-> enabled.setSelected(checkBoxUpdate(enabled.isSelected(), durationComboBox.getValue(), amountTF.getText() )));
    }

    /**
     * Updates what-if budget for an expense based on enabled checkbox state.
     * @param selected the checkbox state
     * @param duration the duration of the expense (Once, Daily, or Weekly)
     * @param amountStr the expense amount. This should be a String representation of a double.
     * @return the selected state if amountStr format is valid, false otherwise.
     */
    private boolean checkBoxUpdate(boolean selected, String duration, String amountStr) {
        double amount;
        String posDoubleRegex = "\\d+(\\.\\d+)?";
        if (!amountStr.matches(posDoubleRegex)){
            showAlert(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Invalid amount.",
                    "Amount must be in currency format (i.e. 1.34 )"
            );
            return false;
        }else{
            amount = Double.parseDouble(amountStr);
        }

        double overDurationAmount = getOverDurationAmount(duration, amount);
        double newWhatifBudget = whatifspending;
        if(selected){
            newWhatifBudget += overDurationAmount;
        }else{
            newWhatifBudget -= overDurationAmount;
        }
        updateWhatifSpending(newWhatifBudget);
        return selected;
    }

    /**
     * Calculates the total amount over the given duration left in the current budget period
     * based on a specified amount per duration.
     * @param duration duration of expense
     * @param amount amount of expense
     * @return the total amount of the expense over remaining expense duration periods remaining in the budget.
     */
    private double getOverDurationAmount(String duration, Double amount){
        double totalOverDuration = 0.0;
        LocalDate today = LocalDate.now();
        long daysPassed = ChronoUnit.DAYS.between(currentBudgetStartDate, today); // number of days into current budget
        int weeksPassed = (int) Math.ceil(daysPassed / 7.0); // number of days into current budget

        // Calculating over-duration amount (this obviously could be more rigorous)
        switch (duration) {
            case "Once" -> // the amount as is
                    totalOverDuration = amount;
            case "Weekly" -> {
                int weeksRemaining = 4 - weeksPassed; // the amount times number of weeks left in budget period
                totalOverDuration = amount * weeksRemaining;
            }
            case "Daily" -> {
                int daysRemaining = 30 - (int) daysPassed;  // the amount times number of days left in budget period
                totalOverDuration = amount * daysRemaining;
            }
            default -> System.out.println("Invalid duration during WhatifUI.getOverDuration().");
        }

        return totalOverDuration;
    }

    /**
     * Returns the sub-duration period of the current budget duration.
     * @return "Weekly" if the budget is "Monthly", otherwise "Daily".
     */
    private String getSubDuration(){
        String subDuration;
        if(currentBudgetDuration.equals("Monthly")){
            subDuration = "Weekly";
        }else{
            subDuration = "Daily";
        }
        return subDuration;
    }
}
