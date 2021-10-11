package csc.arizona.moneymanager.MainUI;

import java.util.ArrayList;

/**
 * @author Carter Boyd
 *
 * This class is designed to hold the settings of what the specific user would like to have
 * specified to his account settings specifically what kind of custom categories the user may
 * want as well as how long the user would want his budget stored and how much of a budget they
 * would like
 *
 * @implNote data will be reached from the database so the database will have to have these
 * structures and store them
 */
public class UserSetting {

    private int budget;
    private ArrayList<String> customCategory;
    private String budgetDuration;

    /**
     * default constructor for if this is the first time an account has been created and will
     * have to fill out the desired amount
     */
    public UserSetting() {
        customCategory = new ArrayList<>();
    }

    /**
     * constructor for when the user has already made an account and needs the data retreived
     * from the database
     * @param category the custom category list
     */
    public UserSetting(ArrayList<String> category) {
        customCategory = new ArrayList<>();
    }

    /**
     * @return the budget
     */
    public int getBudget() {
        return budget;
    }

    /**
     * sets the budget that the user wants
     * @param budget the desired amount
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * @return the list of categories from the custom category list
     */
    public ArrayList<String> getCustomCategory() {
        return customCategory;
    }

    /**
     * sets the custom amount array list
     * @param customCategory the custom category list that was found in the database
     */
    public void setCustomCategory(ArrayList<String> customCategory) {
        this.customCategory = customCategory;
    }

    /**
     * @return how long the user wants the transferred data stored
     */
    public String getBudgetDuration() {
        return budgetDuration;
    }

    /**
     * user wants to change how long their budget is stored
     * @param budgetDuration the new amount of time they want their transfers stored in the database
     */
    public void setBudgetDuration(String budgetDuration) {
        this.budgetDuration = budgetDuration;
    }

    /**
     * if the user created a category name and wants to rename it
     */
    public void editCategoryName(String oldCategory, String newCategory) {
        //TODO take the value of the old category and move it to the new category and then create
        // new category
    }

    /**
     * removes a custom category from the category list
     */
    public void removeCategoryName(String category) {
        customCategory.remove(category);
    }

    /**
     * adds the custom category to the category list
     */
    public void addCategoryName(String category) {
        customCategory.add(category);
    }
}
