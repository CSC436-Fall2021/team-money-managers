package csc.arizona.moneymanager.MainUI;

import csc.arizona.moneymanager.Controller;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 * This class represents the MenuBar and associated menu items
 * for the Money Manager application.
 *
 * This class contains methods for the on action events for each
 * menu item on the menu bar. This framework allows easy modification
 * of individual events, minimizing secondary effects.
 *
 * @author Kris Rangel
 */
public class MainMenuBar extends MenuBar {

    private MainUI mainUI;

    /**
     * Constructor. Sets up the menu items and associated
     * OnAction events to call related methods.
     *
     * @author Kris Rangel
     */
    public MainMenuBar(MainUI mainUI){
        // Do not change OnActions in this function.
        super();

        // File menu setup
        Menu file = new Menu("_File");
        SeparatorMenuItem fileMenuSeparator = new SeparatorMenuItem();
        // --> Set Budget option
        MenuItem setBudget = new MenuItem("Set _Budget");
        setBudget.setOnAction( e-> budgetMenuAction() );
        // --> Add Custom Categories option
        MenuItem addCategories = new MenuItem("Add Custom _Categories");
        addCategories.setOnAction( e -> addCategoriesAction() );
        // --> Save Data option
        MenuItem saveData = new MenuItem("_Save Data");
        saveData.setOnAction(e-> saveDataMenuAction() );
        // --> Logout option
        MenuItem logout = new MenuItem("_Logout");
        logout.setOnAction(e-> logoutMenuAction() );
        // --> remove account
        MenuItem removeAccount = new MenuItem("Delete account");
        removeAccount.setOnAction(e -> removeAccountAction());
        // --> Exit option
        MenuItem exit = new MenuItem("E_xit");
        exit.setOnAction(e-> exitMenuAction() );
        // Adding items to File menu
        file.getItems().addAll(setBudget, addCategories, saveData, fileMenuSeparator, logout,
         removeAccount, exit);
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
        this.getMenus().addAll(file, reports, help);

        // Saving reference to MainUI
        this.mainUI = mainUI;
    }

    /* **********************************
     * Event Action Methods
     ************************************/

    /**
     * Contains the actions performed when the Menu option "Add Custom Categories" is selected.
     */
    private void addCategoriesAction(){
        mainUI.addCustomCategories();
    }

    /**
     * Contains the actions performed when the Menu option "Set Budget" is selected.
     */
    private void budgetMenuAction(){
       mainUI.displayBudgetUI();
    }

    /**
     * Contains the actions performed when the Menu option "Save Data" is selected.
     */
    private void saveDataMenuAction(){
        System.out.println("Save Data Selected"); //TODO remove when action implemented
    }

    /**
     * Contains the actions performed when the Menu option "Logout" is selected.
     */
    private void logoutMenuAction(){
        Controller.mainUIToLogin();
    }

    /**
     * removes the account from the database as well as logs the user out
     */
    private void removeAccountAction() {
        Controller.removeAccount();
    }

    /**
     * Contains the actions performed when the Menu option "Exit" is selected.
     */
    private void exitMenuAction(){
        // ensures logout prior to exit
        logoutMenuAction();
        System.out.println("Exit Selected"); //TODO remove when action implemented
    }


    /**
     * Contains the actions performed when the Menu option "Show Report" is selected.
     */
    private void showReportsMenuAction(){
        System.out.println("Show Report selected"); //TODO remove when action implemented
    }

    /**
     * Contains the actions performed when the Menu option "Save..."(Report)  is selected.
     */
    private void saveReportMenuAction(){
        System.out.println("Save Report selected"); //TODO remove when action implemented
    }

    /**
     * Contains the actions performed when the Menu option "Help" is selected.
     *
     * @author Kris Rangel
     */
    private void showHelpMenuAction(){
        mainUI.showInfo(new UserHelp());
    }

    /**
     * Contains the actions performed when the Menu option "About" is selected.
     *
     * @author Kris Rangel
     */
    private void aboutMenuAction(){
        mainUI.showInfo(new AboutInfo());
    }
}
