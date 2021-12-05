package csc.arizona.moneymanager.MainUI;

import javafx.scene.control.Label;

/**
 * This class represents user help displayed.
 *
 * @author Kris Rangel
 */
public class UserHelp extends ServicesView {

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
        Label tempLabel = new Label("""
                The top of the page will contain everything you need to store the amount of money you want
                you start by selecting what day you spent the money on.
                then you select what you spent the money on (if there is no item for what you spent money on then you can make it """);

        // Adding user help to content GridPane
        content.addRow(0, preHelpSpace);
        content.addRow(1, tempLabel);

    }

}
