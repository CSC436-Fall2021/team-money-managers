package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.TransactionUI.CategoryList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;

public class CategoryBudget extends ServicesView {

    private static final double HGAP = 20.0;
    private static final double VGAP = 20.0;
    private Label categoryLabel;
    private Label categoryTotal;
    private String category;
    private TextField setCatBud;
    private boolean hasDisplayed = false;

    /**
     * Constructor.
     *
     * @param title      the title to set in the services content pane.
     * @param buttonText the text on the "Return" button
     */
    public CategoryBudget(String title, String buttonText) {
        super(title, buttonText);
    }

    /**
     * Take all the categories that the user has and puts them into a set where any duplicates will not appear and then
     * returns the set as a list
     *
     * @return a list of the transaction categories
     */
    private static List<String> getTransactionList() {
        CategoryList list = new CategoryList("src/main/java/csc/arizona/moneymanager/TransactionUI/default_categories.txt");
        list.addCategories(Controller.getUser().getSettings().getCustomCategory());
        return list.getCategories();
    }

    /**
     * @return the category that the user wants to budget
     */
    public String getCategory() {
        return category;
    }

    /**
     * @return the budget the user wanted to budget, if the user selected a non-numeral then it will return -1 as a
     * fail-safe
     */
    public double getSelectedBudget() {
        try {
            return Double.parseDouble(setCatBud.getText());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Sets up GridPane contents.
     */
    @Override
    void initContent() {
        Label selectLabel = new Label("What category would you like to budget?");
        ComboBox<String> category = new ComboBox<>();
        category.setItems(FXCollections.observableArrayList(getTransactionList()));
        HBox introBox = new HBox(selectLabel, category);
        introBox.setSpacing(5.0);
        content.setHgap(HGAP);
        content.setVgap(VGAP);
        content.addRow(0, introBox);
        category.setOnAction(event -> {
            this.category = category.getValue();
            if (hasDisplayed) {
                updateDisplay();
            } else {
                displayRestOfUI();
                hasDisplayed = true;
            }
        });
    }

    private void displayRestOfUI() {
        categoryLabel = new Label("Your current budget for " + category + " is: " + String.format("$%01.2f", Controller.getCategoryBudget(category)));
        content.addRow(1, categoryLabel);
        categoryTotal = new Label("the total amount that has been spent in " + category + " is " + String.format("$%01.2f", + Controller.getCategorySpent(category)));
        content.addRow(2, categoryTotal);
        Label setCatBudLabel = new Label("What would you like the budget for this category to be?");
        setCatBud = new TextField();
        HBox setBox = new HBox(setCatBudLabel, setCatBud);
        setBox.setSpacing(5.0);
        content.addRow(3, setBox);
    }

    /**
     * if the rest of the UI has already been created then
     */
    public void updateDisplay() {
        categoryLabel.setText("Your current budget for " + category + " is:" + String.format("$%01.2f", Controller.getCategoryBudget(category)));
        categoryTotal.setText("the total amount that has been spent in " + category + " is " + String.format("$%01.2f", + Controller.getCategorySpent(category)));
    }
}
