package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.TransactionUI.CategoryList;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class WhatifUI extends ServicesView {

    private final int MAX_WHATIF_CONTENT = 5;
    private int contentCount;
    private double currentBudget;
    private Label currentBudgetAmount;
    private String currentBudgetDuration;
    private CategoryList categoryList;
    private double whatifBudget;
    private Label whatifBudgetLabel;
    private VBox categoryVBox;

    /**
     * Constructor.
     * @param currentBudget current (non-whatif) budget.
     */
    public WhatifUI(Double currentBudget, String currentBudgetDuration, CategoryList categoryList) {
        super("What-if?", "Return");
        this.currentBudget = currentBudget;
        this.currentBudgetAmount.setText(String.format("$%01.2f", currentBudget));
        updateWhatifBudget(currentBudget);
        this.currentBudgetDuration = currentBudgetDuration;
        this.categoryList = categoryList;
        this.contentCount = 0;

        addCategory();

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
        currentBudgetAmount = new Label(String.format("$%01.2f", currentBudget));
        currentBudgetBox.getChildren().addAll(currentBudgetHeader, currentBudgetAmount);

        VBox whatifBudgetBox = new VBox();
        whatifBudgetBox.setAlignment(Pos.CENTER);
        Label whatifBudgetHeader = new Label("What-if? Budget");
        whatifBudgetLabel = new Label();
        updateWhatifBudget(currentBudget);
        whatifBudgetBox.getChildren().addAll(whatifBudgetHeader, whatifBudgetLabel);

        budgetsBox.getChildren().addAll(currentBudgetBox, whatifBudgetBox);

        content.addRow(1, budgetsBox);

        // Header row
        HBox headerBox = new HBox();
        //headerBox.setPadding(MainUI.PADDING);
        headerBox.setSpacing(MainUI.PADDING.getLeft() * 2);

        Label headerSpacing = new Label ("");
        Label checkBoxHeader = new Label ("Enabled");
        Label categoryHeader = new Label ("Category");
        Label durationHeader = new Label ("     Duration");
        Label amountHeader = new Label ("      Amount");

        headerBox.getChildren().addAll(headerSpacing, checkBoxHeader, categoryHeader, durationHeader, amountHeader);
        content.addRow(2, headerBox);

        categoryVBox = new VBox();
        content.addRow(3, categoryVBox);
    }

    private void updateWhatifBudget(double budget){
        whatifBudget = budget;
        String budgetStr = "$" + BudgetUI.budgetToString(whatifBudget);
        whatifBudgetLabel.setText(budgetStr);
    }

    public void addCategory(){
        if(contentCount >= MAX_WHATIF_CONTENT){
            //TODO show alert (cannot add more categories)
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

        // Button for user to remove category
        Button removeCategory = new Button("Remove");
        removeCategory.setOnAction(e-> {
            if(contentCount > 1) {
                categoryVBox.getChildren().remove(catBox);
                contentCount--;
            }
        });

        catBox.getChildren().addAll(leftSpacing, enabled, categoryComboBox, durationComboBox, amountTF, removeCategory);
        categoryVBox.getChildren().add(catBox);
        contentCount++;

        enabled.setOnAction(e-> checkBoxUpdate(enabled.isSelected(), durationComboBox.getValue(), amountTF.getText() ) );
    }

    /**
     * Updates what-if budget base on whether or not checkbox is selected.
     * @param selected the checkbox state
     * @param duration the duration of the category
     * @param amountStr the amount of the category in String format
     */
    private void checkBoxUpdate(boolean selected, String duration, String amountStr) {
        double amount;
        String posDoubleRegex = "\\d+(\\.\\d+)?";
        if (!amountStr.matches(posDoubleRegex)){
            //TODO have alert
            return;
        }else{
            amount = Double.parseDouble(amountStr);
        }

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
