package csc.arizona.moneymanager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * This class represents the main UI of the Money Management Application.
 *
 * The actions for MenuItems are separated from the MenuBar initialization and setup
 * into individual methods for ease of action implementation and system integration.
 *
 * @author Kris Rangel
 */
public class MainUI {

    final private int WIDTH = 700;
    final private int HEIGHT = 500;
    private Scene scene;
    private BorderPane transactionPane;
    private BorderPane servicesPane;
    private HBox optionsPane;

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
        initTransactionPane();
        initServicesPane();
        initOptionsPane();

        // Adding Test Elements
        BorderPane testTransactionPane = new BorderPane();
        testTransactionPane.setCenter(new Label("Test Transaction Pane"));
        setTransactionPane(testTransactionPane);
        BorderPane testServicesPane = new BorderPane();
        testServicesPane.setCenter(new Label("Test Services Pane"));
        setServicesPane(testServicesPane);

        // Setting up main pane
        BorderPane mainPane = new BorderPane();
        BorderPane centerPane = new BorderPane();
        mainPane.setTop(getMenuBar());
        centerPane.setTop(transactionPane);
        centerPane.setCenter(servicesPane);
        mainPane.setCenter(centerPane);
        mainPane.setBottom(optionsPane);

        // creating scene
        this.scene = new Scene(mainPane, width, height);
    }

    /**
     * Sets up menu options to a MenuBar and returns it.
     * Each menu item calls a separate method as the OnAction. This
     * isolates the action portion from the setup code.
     * @return the initialized MenuBar object.
     */
    public MenuBar getMenuBar(){
        MenuBar menu = new MenuBar();

        // File menu setup
        Menu file = new Menu("_File");
        SeparatorMenuItem fileMenuSeparator = new SeparatorMenuItem();
        // --> Save Data option
        MenuItem saveData = new MenuItem("_Save Data");
        saveData.setOnAction(e-> saveDataMenuAction() );
        // --> Logout option
        MenuItem logout = new MenuItem("_Logout");
        logout.setOnAction(e-> logoutMenuAction() );
        // Adding items to File menu
        file.getItems().addAll(saveData, fileMenuSeparator, logout);

        // Report menu setup
        Menu reports = new Menu("_Reports");
        // --> Show Report option //TODO maybe make showReport a submenu with types of reports as menu items
        MenuItem showReport = new MenuItem("Show _Report");
        showReport.setOnAction(e-> showReportsMenuAction() );
        // --> Save... (report) option
        MenuItem saveReport = new MenuItem("_Save...");
        saveReport.setOnAction(e-> saveReportMenuAction() );
        // Adding items to Reports menu
        reports.getItems().addAll(showReport, saveReport);

        // Help menu setup
        Menu help = new Menu("_Help");
        SeparatorMenuItem helpMenuSeparator = new SeparatorMenuItem();
        // --> Help option
        MenuItem showHelp = new MenuItem("_Help");
        showHelp.setOnAction(e-> showHelpMenuAction() );
        // --> About option
        MenuItem about = new MenuItem("_About");
        about.setOnAction(e-> aboutMenuAction() );
        // Adding items to Help menu
        help.getItems().addAll(showHelp, helpMenuSeparator, about);

        // Adding menus to menu bar
        menu.getMenus().addAll(file, reports, help);

        return menu;
    }

    /**
     * Initializes the transaction pane.
     */
    private void initTransactionPane(){
        transactionPane = new BorderPane();
        transactionPane.setStyle("-fx-border-color: black");
    }

    public void setTransactionPane(Pane pane){
        transactionPane.setCenter(pane);
    }

    private void initServicesPane(){
        servicesPane = new BorderPane();
        servicesPane.setStyle("-fx-border-color: black");
    }

    public void setServicesPane(Pane pane){
        servicesPane.setCenter(pane);
    }

    /**
     * Sets up the Options pane.
     */
    private void initOptionsPane(){
        Label testLabel = new Label("Options Pane");
        HBox.setMargin(testLabel, new Insets(20));
        Button testButton = new Button("Test Button");
        testButton.setOnAction(e-> System.out.println("test button pressed") );

        optionsPane = new HBox();
        optionsPane.getChildren().addAll(testLabel, testButton);
        optionsPane.setAlignment(Pos.CENTER);
        optionsPane.setPadding(new Insets(20));
        optionsPane.setStyle("-fx-border-color: black");
    }

    /**
     * @return the MainUI scene.
     */
    public Scene getScene(){
        return this.scene;
    }

    /* **********************************
     * Event Action Methods
     ************************************/

    /**
     * Contains the actions performed when the Menu option "Save Data" is selected.
     */
    private void saveDataMenuAction(){
        System.out.println("Save Data Selected");
    }

    /**
     * Contains the actions performed when the Menu option "Logout" is selected.
     */
    private void logoutMenuAction(){
        System.out.println("Logout Selected");
    }

    /**
     * Contains the actions performed when the Menu option "Show Report" is selected.
     */
    private void showReportsMenuAction(){
        System.out.println("Show Report selected");
    }

    /**
     * Contains the actions performed when the Menu option "Save..."(Report)  is selected.
     */
    private void saveReportMenuAction(){
        System.out.println("Save Report selected");
    }

    /**
     * Contains the actions performed when the Menu option "Help" is selected.
     */
    private void showHelpMenuAction(){
        System.out.println("Show Help selected");
    }

    /**
     * Contains the actions performed when the Menu option "About" is selected.
     */
    private void aboutMenuAction(){
        System.out.println("About selected");
    }

}
