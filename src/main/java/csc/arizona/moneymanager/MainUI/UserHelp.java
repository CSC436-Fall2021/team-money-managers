package csc.arizona.moneymanager.MainUI;

import javafx.scene.control.Label;

/**
 * This class represents user help displayed.
 *
 * @author Kris Rangel
 * @author Carter Boyd
 */
public class UserHelp extends ServicesView {


    private static final Label CREATE_TRANSACTION = new Label("""
            To create a transaction
            The top of the page is where you can perform a transaction, to successfully perform a transaction you will have to
            select a date, what you are spending on, and the amount you are spending, the memo is optional
                        
            """);

    private static final Label CREATE_CATEGORY = new Label("""
            To create a custom category
            At the menubar select file and in the selections add custom category and there the main menu will have a text box
            for the new category you want to create
                        
            """);

    private static final Label SETTING_BUDGET = new Label("""
            To set a budget
            At the menubar select file and in the selections select "set Budget" there the main menu will show the current
            budget and how long the budget is set for, there you will have the options to set the budget to any positive
            number and decide whether the budget will be weekly or monthly
                        
            """);

    private static final Label REPORT = new Label("""
            To see the transactions from a certain point in time
            At the menubar select Report and then select Transaction History, there the main menu will have out the
            starting date of where your looking and the ending date of where you will want to stop looking
            
            """);

    private static final Label WHAT_IF = new Label("""
            To use What if
            at the menubar select reports and scroll to "What if" from there up to 5 possible transactions can occur
            where you can select the category, duration, and the amount. once all are selected, press enable to see what
            the budget would be with that transaction""");

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor.
     */
    public UserHelp() {
        super("Money Manager Help", "Exit Help");
    }

    /**
     * Implements the content to for the "Help" info.
     */
    @Override
    void initContent() {
        content.addRow(0, new Label(" "));
        content.addRow(1, CREATE_TRANSACTION);
        content.addRow(2, CREATE_CATEGORY);
        content.addRow(3, SETTING_BUDGET);
        content.addRow(4, REPORT);
        content.addRow(5, WHAT_IF);
    }

}
