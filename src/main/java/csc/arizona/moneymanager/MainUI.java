package csc.arizona.moneymanager;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * This class represents the main UI of the Money Management Application.
 *
 * The main UI consists of scene with a menu bar and 3 main sections of content:
 *     1. Transaction Pane
 *     2. Services Pane
 *     3. Options Pane
 * The content of these sections can be set via an individual method. These methods
 * accept any subclass of the javafx Pane class (GridPane, BorderPane, HBox, etc.)
 * as the container of content to display. It was set up this way in the interest
 * of modularity.
 *
 * The menu bar is represented by an instance of the separate MainMenuBar class.
 * This was done to separate menu OnActionEvents from the core UI code. The code in this
 * class need not be changed in the event menu options are added or updated due to
 * external code revision.
 *
 * Dependencies: MainMenuBar
 *
 * @author Kris Rangel
 */
public class MainUI {

    final private int WIDTH = 700;
    final private int HEIGHT = 500;
    private Scene scene;
    private BorderPane mainPane;
    private BorderPane transactionPane;
    private BorderPane servicesPane;
    private BorderPane optionsPane;

    /**
     * Default Constructor.
     *
     * Sets width and height of the scene to default values.
     */
    public MainUI() {
        initializeScene(WIDTH, HEIGHT);
    }

    /**
     * Constructor. Allows the scene to initialize to
     * specified width and height values.
     * @param width the scene width.
     * @param height the scene height.
     */
    public MainUI(int width, int height){
        initializeScene(width, height);
    }

    /**
     * Initializes the scene with the specified width and height.
     * @param width the width of the scene.
     * @param height the height of the scene.
     */
    private void initializeScene(int width, int height){

        // Initializing elements
        MainMenuBar menuBar = new MainMenuBar();
        initTransactionPane();
        initServicesPane();
        initOptionsPane();

        // Adding Default (empty indicating) Elements
        BorderPane emptyTransactionPane = new BorderPane();
        emptyTransactionPane.setCenter(new Label("Empty Transaction Pane"));
        setTransactionPane(emptyTransactionPane);
        BorderPane emptyServicesPane = new BorderPane();
        emptyServicesPane.setCenter(new Label("Empty Services Pane"));
        setServicesPane(emptyServicesPane);
        BorderPane emptyOptionsPane = new BorderPane();
        emptyOptionsPane.setCenter(new Label("Empty Options Pane"));
        setOptionsPane(emptyOptionsPane);

        // Setting up main pane
        mainPane = new BorderPane();
        BorderPane centerPane = new BorderPane(); // sub-container for transaction and services pane
        centerPane.setTop(transactionPane);
        centerPane.setCenter(servicesPane);
        // Main pane elements
        mainPane.setTop(menuBar);
        mainPane.setCenter(centerPane);
        mainPane.setBottom(optionsPane);

        // Setting .css styling IDs
        transactionPane.setId("transaction-pane");
        servicesPane.setId("services-pane");
        optionsPane.setId("options-pane");

        // Creating scene
        this.scene = new Scene(mainPane, width, height);
        // Adding .css stylesheet
        this.scene.getStylesheets().add("file:src/main/java/csc/arizona/moneymanager/mainUIStyle.css");
    }

    /**
     * Initializes the transaction pane.
     */
    private void initTransactionPane(){
        transactionPane = new BorderPane();
        transactionPane.setStyle("-fx-border-color: black");
    }

    /**
     * Sets the transaction pane content.
     * @param transaction the content to show in the transaction pane.
     */
    public void setTransactionPane(Pane transaction){
        transactionPane.setCenter(transaction);
    }

    /**
     * Initializes the services pane.
     */
    private void initServicesPane(){
        servicesPane = new BorderPane();
        servicesPane.setStyle("-fx-border-color: black");
    }

    /**
     * Sets the services pane content.
     * @param services the content to show in the services pane.
     */
    public void setServicesPane(Pane services){
        servicesPane.setCenter(services);
    }

    /**
     * Initializes the Options pane.
     */
    private void initOptionsPane(){
        optionsPane = new BorderPane();
        optionsPane.setStyle("-fx-border-color: black");

    }

    /**
     * Sets the options pane content.
     * @param options the content to show in the options pane.
     */
    public void setOptionsPane(Pane options){
        optionsPane.setCenter(options);
    }

    /**
     * @return the MainUI scene.
     */
    public Scene getScene(){
        return this.scene;
    }

}
