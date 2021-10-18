package csc.arizona.moneymanager.MainUI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.util.List;

public class CustomCategoryUI extends HBox {

    List<String> dropDownCategories;
    List<String> userCategories;

    TextField userInput;
    Button enterButton;

    public CustomCategoryUI(MainUI callback, List<String> addTo) {
        userCategories = addTo;

        userInput = new TextField();
        userInput.setPromptText("Enter new category");

        enterButton = new Button("Enter");

        userInput.setOnAction(new EnterCategoryHandler());
        enterButton.setOnAction(new EnterCategoryHandler());


    }

    private class EnterCategoryHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }
}
