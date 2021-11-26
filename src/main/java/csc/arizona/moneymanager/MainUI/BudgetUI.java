package csc.arizona.moneymanager.MainUI;
//
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.Arrays;


/**
 * This class represents the Edit Budget UI.
 *
 *
 * @author Kris Rangel
 */
public class BudgetUI extends ServicesView {

    // Label spacing
    private final double HGAP = 20.0;
    private final double VGAP = 20.0;

    private double budget;
    private String duration;

    // UI Elements
    private Label currentBudgetAmountLabel;
    private Label currentBudgetDurationLabel;
    private TextField newBudgetTF;

    // Possible duration values
    private final String DURATION_1 = "Monthly";
    private final String DURATION_2 = "Weekly";

    /**
     * Constructor.
     *
     * Supplies the title to the super constructor and saves the budget and duration values.
     * Sets the duration to a default value, if null.
     * @param budget the current budget amount
     * @param duration the current budget duration string
     */
    public BudgetUI(double budget, String duration){
        super("Edit Budget", "Cancel");

        this.budget = budget;
        this.duration = duration;
        if(this.duration == null) { this.duration = DURATION_1; }  // In the event no previous duration was set, setting to default value


        // updating current budget labels
        currentBudgetAmountLabel.setText(budgetToString(budget));
        currentBudgetDurationLabel.setText(this.duration);

    }

    /**
     * Implements the content to for the UI.
     */
    @Override
    void initContent() {

        int contentRowGap = 5;

        // Current Budget Amount
        Label currentBudgetLabel = new Label ("Current Budget Amount:");
        currentBudgetAmountLabel = new Label();
        // Current Budget Duration
        Label currentDurationLabel = new Label ("Current Budget Duration: ");
        currentBudgetDurationLabel = new Label();

        // New Budget Amount
        Label newBudgetLabel = new Label("Enter New Budget:");
        newBudgetTF = new TextField();
        Label durationLabel = new Label("Budget duration");

        // New Budget Duration
        ComboBox<String> durationBox = new ComboBox<>(FXCollections.observableArrayList(Arrays.asList(DURATION_1, DURATION_2)));
        durationBox.setEditable(false);
        // Setting Default duration value
        durationBox.getSelectionModel().selectFirst();
        duration = durationBox.getValue();
        // OnActions to update duration
        newBudgetTF.setOnKeyTyped(e-> duration = durationBox.getValue()); // if user enters value for default duration
        durationBox.setOnAction(e-> duration = durationBox.getValue()); // if user changes the duration, update value

        // Adding content to pane
        content.addRow(contentRowGap + 0, currentBudgetLabel, currentBudgetAmountLabel);
        content.addRow(contentRowGap + 1, currentDurationLabel, currentBudgetDurationLabel);
        content.addRow(contentRowGap + 2, newBudgetLabel, newBudgetTF);
        content.addRow(contentRowGap + 3, durationLabel, durationBox);

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
     * Returns the budget duration selected by the user.
     * @return the budget duration string.
     */
    public String getDuration(){
        System.out.printf("here %s\n", duration);
        return duration;
    }

    /**
     * Converts the stored budget into a string value.
     * @return the String representation of the budget amount.
     */
    public static String budgetToString(double budget){
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
