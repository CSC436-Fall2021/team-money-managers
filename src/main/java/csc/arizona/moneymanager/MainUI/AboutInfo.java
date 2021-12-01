package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;

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
        // "Who Is" header
        HBox whoIsBox = new HBox();
        Label whoIsLabel = new Label("Created By");
        whoIsLabel.setScaleX(1.5);
        whoIsLabel.setScaleY(1.5);
        whoIsBox.getChildren().add(whoIsLabel);
        whoIsBox.setAlignment(Pos.CENTER);

        // Divider line
        HBox lineBox = new HBox();
        Rectangle line = new Rectangle(250.0, 2.0);
        lineBox.getChildren().add(line);

        // Members
        HBox memberBox1 = getMemberBox("Mason Baier");
        HBox memberBox2 = getMemberBox("Carter Boyd");
        HBox memberBox3 = getMemberBox("Joseph Clancy");
        HBox memberBox4 = getMemberBox("Kris Rangel");

        // Logo
        Label preLogoSpace = new Label(" ");
        HBox logoBox = new HBox();
        logoBox.getChildren().add(getLogo());
        logoBox.setAlignment(Pos.CENTER);

        // Adding elements to content GridPane
        content.addRow(0, whoIsBox);
        content.addRow(1, lineBox);
        content.addRow(2, memberBox1);
        content.addRow(3, memberBox2);
        content.addRow(4, memberBox3);
        content.addRow(5, memberBox4);
        content.addRow(6, preLogoSpace);
        content.addRow(7, logoBox);

        content.setAlignment(Pos.CENTER);

    }

    /**
     * Gets a formatted HBox containing the specified member in a label.
     * @param member the member to create the label for.
     * @return the HBox object containing the formatted label.
     */
    private HBox getMemberBox(String member){
        HBox memberBox = new HBox();
        Label memberLabel = new Label(member);
        memberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        memberBox.getChildren().add(memberLabel);
        memberBox.setAlignment(Pos.CENTER);
        return memberBox;
    }

    /**
     * Loads the Logo from file.
     * @return the ImageView object representing the logo.
     *
     * @author Carter Boyd
     */
    private ImageView getLogo(){
        File img = new File("src/main/java/csc/arizona/moneymanager/IMG/Icon.png");
        Image image = new Image(img.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}
