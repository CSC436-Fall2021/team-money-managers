package csc.arizona.moneymanager.MainUI;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * This class represents user help displayed.
 *
 * @author Kris Rangel
 */
public class UserLandingPage extends ServicesView {

    Label welcomeLabel;
    static int contentCount = 0;

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor.
     */
    public UserLandingPage() {
        super("Money Manager", "Exit Help");

        // Setting welcome text
        welcomeLabel.setText("Welcome.");
    }

    /**
     * Implements the base content for the landing page.
     */
    @Override
    void initContent() {
        // Welcome label setup
        Label space = new Label(" ");
        welcomeLabel = new Label();

        // Adding user help to content GridPane
        addLabel(space);
        addLabel(welcomeLabel);
    }

    /**
     * Adds a label to the landing page. The label added with have 'heading' indention.
     * @param label the label to add.
     */
    private void addLabel(Label label){
        content.addRow(contentCount++, label);
    }

    /**
     * Adds an HBox as the new bottom row of the landing page. Guarantees uniform padding.
     * @param contentRow The HBox object containing the content to add.
     */
    public void addContent(HBox contentRow){
        // Adding padding to content
        contentRow.setPadding(MainUI.PADDING);
        contentRow.setSpacing(MainUI.PADDING.getLeft());

        content.addRow(contentCount++, contentRow);
    }

    /**
     * Sets the welcome message on the landing page.
     * @param msg the message to display.
     */
    public void setWelcomeMessage(String msg) {
        welcomeLabel.setText(msg);
    }

}
