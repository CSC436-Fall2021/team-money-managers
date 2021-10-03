package csc.arizona.moneymanager.TransactionUI;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.Month;

public class TransactionUI extends BorderPane {

    ComboBox<Month> monthDropDown;
    ComboBox<Integer> dayDropDown;
    TextField yearInput;
    Integer days[];

    ComboBox<Category> categoryDropDown;
    TextField amountInput;

    public TransactionUI() {
        HBox transactionInput = new HBox();

        days = new Integer[31];
        for (int i = 1; i <= 31; i++) {
            days[i] = i;
        }

        monthDropDown = new ComboBox<>(FXCollections.observableArrayList(Month.values()));
        dayDropDown = new ComboBox<>(FXCollections.observableArrayList(days));
        yearInput = new TextField("Year");

        categoryDropDown = new ComboBox(FXCollections.observableArrayList(Category.values()));
        amountInput = new TextField("Enter amount");

        Button enterButton = new Button("Enter");

        amountInput.setOnAction(new EnterTransactionHandler());
        enterButton.setOnAction(new EnterTransactionHandler());

        //date
        transactionInput.getChildren().add(monthDropDown);
        transactionInput.getChildren().add(dayDropDown);
        transactionInput.getChildren().add(yearInput);
        transactionInput.getChildren().add(categoryDropDown);
        transactionInput.getChildren().add(amountInput);
        transactionInput.getChildren().add(enterButton);


        setCenter(transactionInput);




    }

    private class EnterTransactionHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

            Month month = monthDropDown.getValue();
            int day = dayDropDown.getValue();
            int year = Integer.parseInt(yearInput.getText());

            LocalDate date = LocalDate.of(year, month, day);


            Category category = categoryDropDown.getValue();
            double amount = Double.parseDouble(amountInput.getText());

            Transaction toAdd = new Transaction(date, category, amount);

            System.out.println(toAdd.getDate());
            System.out.println(toAdd.getCategory());
            System.out.println(toAdd.getAmount());

            amountInput.clear();

        }
    }
}
