package csc.arizona.moneymanager.MainUI;
//
import javafx.scene.control.Label;

/**
 * This class represents the "About" this program info.
 *
 * @author Kris Rangel
 */
public class AboutInfo extends ServicesView {

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor.
     */
    public AboutInfo() {
        super("About Money Managers", "Exit About");
    }

    /**
     * Implements the content to for the "About" info.
     */
    @Override
    void initContent() { //TODO add about info
        // User help
        Label preHelpSpace = new Label(" ");
        Label tempLabel = new Label("Insert About Content Here");

        // Adding user help to content GridPane
        content.addRow(0, preHelpSpace);
        content.addRow(1, tempLabel);

    }
}
