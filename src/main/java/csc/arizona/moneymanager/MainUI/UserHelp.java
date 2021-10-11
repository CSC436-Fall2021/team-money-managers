package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This class represents user help displayed.
 */
public class UserHelp extends BorderPane {

    private double titleScale = 1.5;
    private GridPane content;

    public UserHelp(){
        setPadding(MainUI.PADDING);

        Label titleLabel = new Label("Money Manager Help");
        titleLabel.setScaleX(titleScale);
        titleLabel.setScaleY(titleScale);

        BorderPane.setAlignment(titleLabel, Pos.TOP_CENTER);
        setTop(titleLabel);

        initContent();
        BorderPane.setAlignment(content, Pos.CENTER_LEFT);
        setCenter(content);
    }

    private void initContent(){
        content = new GridPane();

        // User help
        Label preHelpSpace = new Label(" ");
        Label tempLabel = new Label("Insert Help Content Here");

        // Adding user help to content GridPane
        content.addRow(0, preHelpSpace);
        content.addRow(1, tempLabel);
    }
}
