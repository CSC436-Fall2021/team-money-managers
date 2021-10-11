package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * This abstract class represents an information display within a BorderPane.
 * The content is titled with a given string during construction. This class
 * can be extended to display information in a uniform manner.
 *
 * @author Kris Rangel
 */
abstract public class InfoView extends BorderPane {

    private final double titleScale = 1.5;
    protected GridPane content;
    private String buttonText;

    /**
     * Constructor.
     * @param title the title to set in the info content pane.
     */
    public InfoView(String title, String buttonText){
        this.buttonText = buttonText;
        setPadding(MainUI.PADDING);

        Label titleLabel = new Label(title);
        titleLabel.setScaleX(titleScale);
        titleLabel.setScaleY(titleScale);

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
