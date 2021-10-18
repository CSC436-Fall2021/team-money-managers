package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.TransactionUI.TransactionUI;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.List;

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

    final private int WIDTH = 850;
    final private int HEIGHT = 725;
    public static final Insets PADDING = new Insets(20);
    private Scene scene;
    private BorderPane mainPane;
    private BorderPane transactionPane;
    private BorderPane servicesPane;
    private Pane currentServicesPane;
    private BorderPane optionsPane;
    private Pane currentOptionsPane;

    private UserSetting userSettings;

    /**
     * Default Constructor.
     *
     * Sets width and height of the scene to default values.
     * And initializes UserSettings object.
     */
    public MainUI() {
        this.userSettings = new UserSetting();
        initializeScene(WIDTH, HEIGHT);
    }

    /**
     * Constructor. Allows the scene to initialize to
     * specified width and height values.
     * @param width the scene width.
     * @param height the scene height.
     */
    public MainUI(int width, int height){ // TODO remove method once UserSettings implemented within Controller
        this.userSettings = new UserSetting(); // initializing UserSetting object
        initializeScene(width, height);
    }

    /**
     * Constructor. Accepts the UserSetting object for the
     * currently logged in user.
     * @param userSettings the UserSetting object that contains
     *                     the currently logged-in user's settings.
     */
    public MainUI(UserSetting userSettings){
        this.userSettings = userSettings;
        initializeScene(WIDTH, HEIGHT);
    }

    /**
     * Constructor. Allows the scene to initialize to
     * specified width and height values and accepts
     * the UserSetting object for the currently logged in user.
     * @param width the scene width.
     * @param height the scene height.
     * @param userSettings the UserSetting object that contains
     *                     the currently logged-in user's settings.
     */
    public MainUI(int width, int height, UserSetting userSettings){
        this.userSettings = userSettings;
        initializeScene(width, height);
    }

    /**
     * Initializes the scene with the specified width and height.
     * @param width the width of the scene.
     * @param height the height of the scene.
     */
    private void initializeScene(int width, int height){

        // Initializing elements
        MainMenuBar menuBar = new MainMenuBar(this);
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
        this.scene.getStylesheets().add("file:src/main/java/csc/arizona/moneymanager/MainUI/mainUIStyle.css");
    }

    /**
     * Initializes the transaction pane.
     */
    private void initTransactionPane(){
        transactionPane = new BorderPane();
        transactionPane.setPadding(PADDING);
        transactionPane.setPrefWidth(WIDTH);
        BorderPane.setAlignment(transactionPane, Pos.CENTER);
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
        // Saving current service pane content
        currentServicesPane = services;
        // Setting content
        servicesPane.setCenter(currentServicesPane);
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
        // Saving current options pane content
        currentOptionsPane = options;
        // Setting content
        optionsPane.setCenter(currentOptionsPane);
    }

    /**
     * Displays the set bugdet UI and updates the user-entered budget amount in the
     * account settings class.
     */
    public void displayBudgetUI(){

        double currentBudget = userSettings.getBudget();
        String currentBudgetDuration = userSettings.getBudgetDuration();

        // Service pane Elements
        BudgetUI budgetUI = new BudgetUI(currentBudget, currentBudgetDuration);

        // Options pane elements
        HBox budgetOptions = createExitContentButtonOptionBox(budgetUI.getButtonText());
        Button okay = new Button("Set Budget");
        okay.setOnAction(e-> saveBudget(budgetUI.getBudget(), budgetUI.getDuration())); // if budget changed, saving new budget
        budgetOptions.getChildren().add(0, okay); // Adding confirmation button to leftmost position

        // Displaying budget UI in services pane
        servicesPane.setCenter( budgetUI );
        // Displaying option buttons in options pane
        optionsPane.setCenter(budgetOptions);

    }

    /**
     * Saves the given budget in Account Settings.
     * @param budget the budget amount to save in account settings.
     */
    private void saveBudget(double budget, String duration){

        // Saving budget into user settings
        userSettings.setBudget(budget);
        userSettings.setBudgetDuration(duration);

        // Showing alert for budget change confirmation
        duration = duration.toLowerCase();
        String message = "You have set the new " + duration + " budget of $" + BudgetUI.budgetToString(budget) + ".";
        Alert budgetSetConfimation = new Alert(Alert.AlertType.INFORMATION, message);
        budgetSetConfimation.showAndWait().filter(response -> response == ButtonType.OK);

        // Restoring content to previous content
        showCurrentContent();
    }

    /**
     * Displays an add custom categories UI and updates the account settings custom category list.
     */
    public void addCustomCategories(){
        HBox layout = new HBox();

        TextField userInput = new TextField();
        Button exitButton = new Button("Exit");

        userInput.setPromptText("Enter new category");
        userInput.setOnAction(e -> {
            String newCategory = userInput.getText();

            // if there is something to add.
            if (!newCategory.isEmpty()) {
                TransactionUI target = (TransactionUI)transactionPane.getCenter();

                List<String> userCategories = userSettings.getCustomCategory();
                List<String> defaultCategories = target.getDefaultCategories();

                // add only if the category does not already exist in the user's custom categories
                // or in the default categories
                if (!defaultCategories.contains(newCategory) && !userCategories.contains(newCategory)) {
                    userSettings.addCategoryName(newCategory); // add to userCategories in settings
                    target.addCategory(newCategory);  // add to dropdown in transactions pane
                }
            }


            showCurrentContent();
        });

        // reset to original display
        exitButton.setOnAction(e -> {
            showCurrentContent();
        });

        layout.getChildren().add(userInput);
        layout.getChildren().add(exitButton);
        servicesPane.setCenter(layout);

    }

    /**
     * Updates the Services pane to the given InfoView content and
     * updates the Options pane with a return button to return to previously
     * displayed content.
     * @param servicesView the InfoView object to display.
     */
    public void showInfo(ServicesView servicesView){
        // Setting services pane to About info
        servicesPane.setCenter(servicesView);
        // Getting options pane
        HBox aboutInfoOptions = createExitContentButtonOptionBox(servicesView.getButtonText());
        // Setting options pane
        optionsPane.setCenter(aboutInfoOptions);
    }

    /**
     * Creates a simple, one-button-return options pane for to return to previous content display.
     * @param buttonText the text to display on the return button.
     * @return the HBox object for display in the options pane.
     */
    private HBox createExitContentButtonOptionBox(String buttonText){
        // Initializing options
        HBox infoOptions = new HBox();
        Button exitInfo = new Button(buttonText);
        exitInfo.setOnAction(e -> showCurrentContent()); // Restores current content when user chooses to exit help

        HBox.setMargin(exitInfo, PADDING);
        infoOptions.setAlignment(Pos.CENTER);
        infoOptions.setPadding(PADDING);
        // Adding button to options pane
        infoOptions.getChildren().add(exitInfo);

        return infoOptions;
    }

    /**
     * Sets Services Pane and Options Pane to current content.
     *
     * Used to restore previous content when temporarily showing
     * different in the services and/or options pane.
     */
    private void showCurrentContent(){
        // Setting services pane to previous view
        servicesPane.setCenter(currentServicesPane);
        // Setting options pane to previous view
        optionsPane.setCenter(currentOptionsPane);
    }

    /**
     * @return the MainUI scene.
     */
    public Scene getScene(){
        return this.scene;
    }

    /**
     * @return the UserSetting object associated with the currently logged-in user.
     */
    public UserSetting getUserSettings(){ return this.userSettings; }

}
