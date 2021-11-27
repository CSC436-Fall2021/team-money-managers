package csc.arizona.moneymanager.MainUI;

public class CategoryBudget extends ServicesView {
    /**
     * Constructor.
     *
     * @param title      the title to set in the services content pane.
     * @param buttonText the text on the "Return" button
     */
    public CategoryBudget(String title, String buttonText) {
        super(title, buttonText);
    }

    public CategoryBudget() {
        super("Set", "set");
    }

    /**
     * Sets up GridPane contents.
     */
    @Override
    void initContent() {

    }
}
