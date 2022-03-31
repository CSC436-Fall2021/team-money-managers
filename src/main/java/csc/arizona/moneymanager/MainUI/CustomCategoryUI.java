package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class CustomCategoryUI extends ServicesView {

    TextField userInput;

    public CustomCategoryUI() {
        super("Add Category", "Cancel");
    }

    @Override
    void initContent() {
        userInput = new TextField();
        userInput.setPromptText("Enter new category");
        content.addRow(14, userInput);
        content.setAlignment(Pos.CENTER);
    }

    public String getInput() {
        return userInput.getText();
    }
}
