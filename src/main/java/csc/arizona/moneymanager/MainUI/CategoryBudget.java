package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.TransactionUI.Transaction;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CategoryBudget extends ServicesView {
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
        Set<String> set = new HashSet<>();
        for (Transaction trans : Controller.getUser().getTransactions())
            set.add(trans.getCategory());
        return set.stream().toList();
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
        content.addRow(0, introBox);
        category.setOnAction(event -> displayRestOfUI(category.getValue()));
    }

    private void displayRestOfUI(String value) {
        Label test = new Label("You selected " + value);
        content.addRow(1, test);
    }
}
