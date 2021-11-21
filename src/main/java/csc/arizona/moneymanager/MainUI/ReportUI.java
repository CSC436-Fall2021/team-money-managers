package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.database.Report;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ReportUI extends ServicesView{
    /**
     * Constructor.
     *
     * @param title      the title to set in the info content pane.
     * @param buttonText
     */


    public ReportUI(String title, String buttonText) {
        super(title, buttonText);
    }

    @Override
    void initContent() {
        String[] reportTypes = new String[]{"Transaction History", "...", "..."};

        DatePicker start = new DatePicker();
        start.setEditable(false);

        DatePicker end = new DatePicker();
        end.setEditable(false);



        HBox datePickerBox = new HBox();
        datePickerBox.setSpacing(30);
        datePickerBox.setAlignment(Pos.CENTER);
        datePickerBox.getChildren().addAll(new Label("From"),start,new Label("To"),end);

        ComboBox reportType = new ComboBox(FXCollections.observableArrayList(reportTypes));

        Button generateButton = new Button("Generate Report");
        generateButton.setOnAction( e -> {
            if(reportType.getValue() != null) {
                if (reportType.getValue().equals("Transaction History")) {
                    Controller.showReport("history", start.getValue(), end.getValue());
                }
            }
        });



        content.addRow(0, new Label(""),new Label("Timeframe"),new Label(""));
        content.addRow(1, new Label(""));
        content.addRow(2, new Label(""),datePickerBox,new Label(""));
        content.addRow(3, new Label(""));
        content.addRow(4, new Label(""),new Label("Report Type"),new Label(""));
        content.addRow(5, new Label(""));
        content.addRow(6, new Label(""),reportType,new Label(""));
        content.addRow(7, new Label(""));
        content.addRow(8, new Label(""));
        content.addRow(9, new Label(""));
        content.addRow(10, new Label(""));
        content.addRow(11,new Label(""),generateButton,new Label(""));

    }
}
