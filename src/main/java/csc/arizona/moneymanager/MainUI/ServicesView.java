package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This abstract class represents content within a BorderPane with the intent
 * to display the content in the servicesPane of the MainUI class.
 * The content is titled with a given string during construction. This class
 * can be extended to display content in a uniform manner within the MainUI.
 *
 * @author Kris Rangel
 */
abstract public class ServicesView extends BorderPane {

    protected GridPane content;
    private String buttonText;

    /**
     * Constructor.
     * @param title the title to set in the services content pane.
     * @param buttonText the text on the "Return" button
     */
    public ServicesView(String title, String buttonText){
        this.buttonText = buttonText;
        setPadding(MainUI.PADDING);

        Label titleLabel = new Label(title);
        titleLabel.setId("title");

        BorderPane.setAlignment(titleLabel, Pos.TOP_CENTER);
        setTop(titleLabel);

        content = new GridPane();
        initContent();
        BorderPane.setAlignment(content, Pos.CENTER_LEFT);
        setCenter(content);
    }

    public String getButtonText(){
        return this.buttonText;
    }

    /**
     * Sets up GridPane contents.
     */
    abstract void initContent();
}
