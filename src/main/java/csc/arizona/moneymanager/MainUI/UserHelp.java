package csc.arizona.moneymanager.MainUI;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * This class represents user help displayed.
 *
 * @author Kris Rangel
 */
public class UserHelp extends InfoView {

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor.
     */
    public UserHelp() {
        super("Money Manager Help", "Exit Help");
    }

    /**
     * Implements the content to for the "Help" info.
     */
    @Override
    void initContent() { //TODO add help info
        // User help
        Label preHelpSpace = new Label(" ");
        Label tempLabel = new Label("Insert Help Content Here");

        // Adding user help to content GridPane
        content.addRow(0, preHelpSpace);
        content.addRow(1, tempLabel);

    }

}
