package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Charts.TransactionChart;
import csc.arizona.moneymanager.Controller;
import csc.arizona.moneymanager.Style;
import csc.arizona.moneymanager.TransactionUI.TransactionUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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

    private UserSetting userSettings; // TODO: remove this. Can get user and settings through static Controller

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

        // Showing landing page by default
        initLandingPage();

        // showing empty options pane
        clearOptionsPane();

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
        Style.addStyling(this.scene);
    }

    /**
     * Initializes the user landing page.
     */
    private void initLandingPage(){
        UserLandingPage userLandingPage = new UserLandingPage();
        String userNickname = userSettings.getUserNickname();

        // If user nickname already set, updating welcome message
        if(userNickname != null && !userNickname.equals("")){
            userLandingPage.setWelcomeMessage("Welcome, " + userNickname + ".");
        }

        // Adding update user nickname to landing page
        HBox updateNicknameBox = createUserNicknameUpdateBox(userNickname);
        userLandingPage.addContent(updateNicknameBox);


        //TODO add other content to landing page here

        // Setting landing page as active page
        setServicesPane(userLandingPage);
    }

    private HBox createUserNicknameUpdateBox(String currentNickname){
        if(currentNickname == null || currentNickname.equals("")) { // user nickname not set
            currentNickname = "none";
        }

        // Update user nickname row
        HBox updateNicknameBox = new HBox();
        updateNicknameBox.setPadding(PADDING);
        updateNicknameBox.setSpacing(PADDING.getLeft());
        Label currentLabel = new Label("Current Nickname :");
        Label nickname = new Label(currentNickname);
        TextField nicknameTF = new TextField();
        Button updateNickname = new Button("Update");
        updateNickname.setOnAction( e-> saveNickname(nicknameTF.getText()) ); // update button saves nickname
        nicknameTF.setOnAction(e-> updateNickname.fire());
        updateNicknameBox.getChildren().addAll(currentLabel, nickname, nicknameTF, updateNickname);

        return updateNicknameBox;
    }

    /**
     * Updates the user nickname stored in settings and reloads landing page.
     * @param nickname the user nickname string to store.
     */
    public void saveNickname(String nickname){

        // Checking for blank nickname from user and confirming update, if so.
        if(nickname == null || nickname.equals("")){
            Alert blankNicknameAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to have no nickname?");
            blankNicknameAlert.showAndWait()
                    .filter   (response -> response == ButtonType.OK ) // if user pressed "OK"
                    .ifPresent(response -> userSettings.setUserNickname(nickname) ); // updating username to blank, if confirmed
        }else {
            userSettings.setUserNickname(nickname);
        }
        initLandingPage();
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
     * Displays the category budget UI where the user is able to set a budget on a category
     */
    public void displayCategoryBudgetUI() {
        CategoryBudget categoryBudget = new CategoryBudget("Set Category Budget", "Cancel");
        servicesPane.setCenter(categoryBudget);
        Button setCatBudBut = new Button("Set Category Budget");
        setCatBudBut.setOnMouseClicked(event -> {
            double budget = categoryBudget.getSelectedBudget();
            if (budget <= 0)
                Controller.failureAlert("budget");
            else {
                Controller.setCategoryBudget(categoryBudget.getCategory(), budget);
                categoryBudget.updateDisplay();
            }
        });
        HBox budgetOptions = createExitContentButtonOptionBox(categoryBudget.getButtonText());
        budgetOptions.getChildren().add(0, setCatBudBut);
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

        CustomCategoryUI categoryUI = new CustomCategoryUI();

        Button addButton = new Button("Add");


        //userInput.setOnAction(e -> {});

        addButton.setOnAction(e -> {
            String newCategory = categoryUI.getInput();

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

        HBox optionsActions = createExitContentButtonOptionBox("Cancel");
        optionsActions.getChildren().add(0, addButton);

        servicesPane.setCenter(categoryUI);
        optionsPane.setCenter(optionsActions);

    }

    /**
     * Updates the Services pane to the given ServicesView content and
     * updates the Options pane with a return button to return to previously
     * displayed content.
     * @param servicesView the ServicesView object to display.
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
    public UserSetting getUserSettings() {
      return this.userSettings;
    }


    // gives userSetting information to MainUI
    public void setUserSettings(UserSetting userSettings) {
        this.userSettings = userSettings;
    }

    /**
     * Sets the options pane to display no content.
     */
    private void clearOptionsPane(){
        String transparentStyle = "-fx-background-color: rgba(0,0,0,0); ";
        HBox emptyOptions = createExitContentButtonOptionBox("");

        // Setting default button to disabled and transparent
        emptyOptions.getChildren().get(0).setStyle(transparentStyle);
        emptyOptions.getChildren().get(0).setDisable(true);
        // Setting options HBox to transparent
        emptyOptions.setStyle(transparentStyle);

        setOptionsPane(emptyOptions);
    }

    /**
     * Shows the ChartUI in the services pane.
     *
     * @param chart
     */ //TODO maybe add a parameter to select which chart type?
    public void showChartUI(TransactionChart chart){
        showInfo(new ChartUI(chart));
    }

    /**
     * Test method to create and show a dummy pie chart
     */
    public void testShowPieChart(){ //TODO remove test method when not needed
        ObservableList<PieChart.Data> testData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Food", 20),
                        new PieChart.Data("Other", 10));
        PieChart testPieChart = new PieChart(testData);
        testPieChart.setTitle("Test Pie Chart");
        //showInfo(new ChartUI(testPieChart));
    }

}
