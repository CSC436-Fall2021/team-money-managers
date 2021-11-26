package csc.arizona.moneymanager;

import javafx.scene.Scene;

/**
 * This class is to allow for uniform application of .css styling throughout the program.
 * This class is intended to contain the ONLY reference to the .css stylesheet.
 */
abstract public class Style {
//test
    /**
     * Applies uniform styling to Scenes within the program. Scene creation should be followed
     * by a call to this method to ensure application of .css styling.
     * @param scene the Scene object to apply styling to.
     */
    public static void addStyling(Scene scene){
        scene.getStylesheets().add("file:src/main/java/csc/arizona/moneymanager/main-style.css");
    }
}
