package csc.arizona.moneymanager.MainUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * This class represents the Edit Budget UI.
 *
 *
 * @author Kris Rangel
 */
public class BudgetUI extends ServicesView {

    private final double HGAP = 20.0;
    private final double VGAP = 20.0;
    private double budget;

    private Label currentBudgetAmountLabel;
    private TextField newBudgetTF;

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor.
     */
    public BudgetUI(double budget){
        super("Edit Budget", "Cancel");

        this.budget = budget;

        // updating current budget label ( has to be done after super(); )
        currentBudgetAmountLabel.setText(budgetToString());

    }

    /**
     * Implements the content to for the UI.
     */
    @Override
    void initContent() {

        int contentRowGap = 12;

        //Label titleLabel = new Label("Edit Budget");
        Label currentBudgetLabel = new Label ("Current Budget Amount:");
        currentBudgetAmountLabel = new Label();
        Label newBudgetLabel = new Label("Enter New Budget:");
        newBudgetTF = new TextField();

        /* For debugging, this action updates the budget label immediately
        newBudgetTF.setOnAction(e -> {
            getBudget();
            currentBudgetAmountLabel.setText(budgetToString());
        });
        */

        content.addRow(contentRowGap + 0, currentBudgetLabel, currentBudgetAmountLabel);
        content.addRow(contentRowGap + 1, newBudgetLabel, newBudgetTF);

        // Setting Alignment
        content.setHgap(HGAP);
        content.setVgap(VGAP);
        content.setAlignment(Pos.CENTER);

    }

    /**
     * Returns the budget amount entered by the user.
     * If the user entered a non-double value, the previously stored
     * budget is returned.
     * @return the user-entered budget if formatted as a double, otherwise
     * the previous budget amount.
     */
    public double getBudget(){
        try {
            budget =  Double.valueOf(newBudgetTF.textProperty().get());
        }catch(NumberFormatException e){
            System.out.println("Number format exception thrown converting budget to a double: user entered invalid format.");
        }
        return budget;
    }

    /**
     * Converts the stored budget into a string value.
     * @return the String representation of the budget amount.
     */
    private String budgetToString(){
        double budgetDouble = budget; // double formatted budget
        String budgetString = ".";    // string formatted budget

        // Dollar part of budget
        int dollarPart = (int) budgetDouble; // dollars to integer
        budgetString = String.valueOf(dollarPart) + budgetString;
        budgetDouble -= dollarPart; // subtracting off dollars

        // Cents part of budget
        if(budgetDouble > 0.005){
            budgetDouble *= 100; // converting cents to whole number
            int centsPart = (int) (budgetDouble + .4); // truncating to integer ( adding .4 to allow for lack of precision of double storage but not enough to round up).
            if(centsPart < 10){
                budgetString = budgetString + "0";
            }
            budgetString = budgetString + String.valueOf(centsPart);
        }else{ // there are no cents
            budgetString = budgetString + "00";
        }

        return budgetString;
    }
}
