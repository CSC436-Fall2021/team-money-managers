package csc.arizona.moneymanager.MainUI;
//
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

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
        System.out.printf("Content count = %d\n", contentCount);
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
        addContent(space);
        addContent(welcomeLabel);
    }

    /**
     * Adds content row as the new bottom row of the landing page;
     * @param region The Region object to add (to include any subclass which includes Panes and Controls).
     */
    public void addContent(Region region){
        content.addRow(contentCount++, region);
    }

    /**
     * Sets the welcome message on the landing page.
     * @param msg the message to display.
     */
    public void setWelcomeMessage(String msg) {
        welcomeLabel.setText(msg);
    }

}
