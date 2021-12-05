package csc.arizona.moneymanager;

import javafx.scene.Scene;
import javafx.scene.control.Alert;

/**
 * This class is to allow for uniform application of .css styling throughout the program.
 * This class is intended to contain the ONLY reference to the .css stylesheet.
 */
abstract public class Style {

    private static final String styleSheet = "file:src/main/java/csc/arizona/moneymanager/main-style.css";

    /**
     * Applies uniform styling to Scenes within the program. Scene creation should be followed
     * by a call to this method to ensure application of .css styling.
     * @param scene the Scene object to apply styling to.
     */
    public static void addStyling(Scene scene){
        scene.getStylesheets().add(styleSheet);
    }

    /**
     * Applies uniform styling to Alert dialog boxes within the program.
     * Alert creation should be followed by a call to this method to ensure
     * application of .css styling.
     * @param alert the alert dialog to apply styling to.
     */
    public static void addStyling(Alert alert){
        alert.getDialogPane().getStylesheets().add(styleSheet);
    }
}
