package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.TransactionUI.CategoryList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WhatifUI extends ServicesView {

    private final int MAX_WHATIF_CONTENT = 5;
    private double currentBudget;
    private String currentBudgetDuration;
    private double whatifBudget;
    private Label whatifBudgetLabel;
    private final int CATEGORY_ROW_START = 5;
    private int currentCategoryRow;

    /**
     * Constructor.
     * @param currentBudget current (non-whatif) budget.
     */
    public WhatifUI(Double currentBudget, String currentBudgetDuration) {
        super("What-if?", "Return");
        this.currentBudget = whatifBudget;
        this.currentBudgetDuration = currentBudgetDuration;
        this.currentCategoryRow = CATEGORY_ROW_START;

        addCategory("Food", "Weekly", 10.0);
        addCategory("Food", "Weekly", 10.0);
        addCategory("Food", "Weekly", 10.0);
        addCategory("Food", "Weekly", 10.0);
        addCategory("Food", "Weekly", 10.0);

    }

    @Override
    void initContent() {
        HBox budgetsBox = new HBox();
        budgetsBox.setAlignment(Pos.CENTER);
        budgetsBox.setPadding(MainUI.PADDING);
        budgetsBox.setSpacing(MainUI.PADDING.getLeft() * 2);

        VBox currentBudgetBox = new VBox();
        currentBudgetBox.setAlignment(Pos.CENTER);
        Label currentBudgetHeader = new Label("Current Budget");
        Label currentBudgetAmount = new Label(String.format("$%01.2f", currentBudget));
        currentBudgetBox.getChildren().addAll(currentBudgetHeader, currentBudgetAmount);

        VBox whatifBudgetBox = new VBox();
        whatifBudgetBox.setAlignment(Pos.CENTER);
        Label whatifBudgetHeader = new Label("What-if? Budget");
        whatifBudgetLabel = new Label();
        updateWhatifBudget(currentBudget);
        whatifBudgetBox.getChildren().addAll(whatifBudgetHeader, whatifBudgetLabel);

        budgetsBox.getChildren().addAll(currentBudgetBox, whatifBudgetBox);

        content.addRow(1, budgetsBox);

        // TODO get number of categories
        // TODO for loop for # of categories to create label for each
        // TODO check mark to disable/enable each category
    }

    private void updateWhatifBudget(double budget){
        whatifBudget = budget;
        String budgetStr = "$" + BudgetUI.budgetToString(whatifBudget);
        whatifBudgetLabel.setText(budgetStr);
    }

    private void addCategory(String category, String duration, Double amount){
        HBox catBox = new HBox();
        //catBox.setPadding(MainUI.PADDING);
        catBox.setSpacing(MainUI.PADDING.getLeft());
        catBox.setAlignment(Pos.BOTTOM_CENTER);

        // Checkbox to enable or disable use in calculating what-if budget
        CheckBox enabled = new CheckBox();
        enabled.setIndeterminate(false);
        enabled.setOnAction(e-> checkBoxUpdate(enabled.isSelected(), duration, amount) );

        ComboBox<String> categoryComboBox = new ComboBox<>();

        Label categoryLabel = new Label(category);
        Label durationLabel = new Label("(" + duration + ")");
        Label amountLabel = new Label(String.format("$%01.2f", amount));

        // Button for user to remove category
        Button removeCategory = new Button("Remove");
        removeCategory.setOnAction(e-> content.getChildren().remove(catBox));

        catBox.getChildren().addAll(enabled, categoryLabel, durationLabel, amountLabel, removeCategory);
        content.addRow(currentCategoryRow++, catBox);
    }

    /**
     * Updates what-if budget base on whether or not checkbox is selected.
     * @param selected the checkbox state
     * @param duration the duration of the category
     * @param amount the amount of the category
     */
    private void checkBoxUpdate(boolean selected, String duration, Double amount) {
        double overDurationAmount = getOverDurationAmount(duration, amount);
        if(selected){
            whatifBudget += overDurationAmount;
        }else{
            whatifBudget -= overDurationAmount;
        }
        //TODO update whatifBudget label
    }

    private double getOverDurationAmount(String duration, Double amount){
        double totalOverDuration = 0.0;
        //TODO get how many days into current cycle:  days(today - duration start)

        int subDurationsRemaining = calculateSubDurationsRemaining(duration); // number of sub-durations (days or weeks) remaining

        // Calculating over-duration amount
        switch(duration){ //TODO should be: amount * ( sub-durations remaining)
            case "Monthly":

                break;
            case "Weekly":

                break;
            default:
                System.out.println("Invalid duration during WhatifUI.getOverDuration().");
        }


        return totalOverDuration;
    }

    private int calculateSubDurationsRemaining(String duration){
        int subDurationsRemaining = 0;

        //TODO calculate sub-durations remaining

        return subDurationsRemaining;
    }
}
