package csc.arizona.moneymanager.TransactionUI;
//
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryList implements Serializable {

    private List<String> defaultCategories;
    private List<String> categories;

    public CategoryList(String filename) {
        // load in default files
        defaultCategories = new ArrayList<String>();
        categories = new ArrayList<String>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNextLine()) {
            String category = scanner.nextLine();
            defaultCategories.add(category);
            categories.add(category);
        }

    }

    // add from database. probably from userSettings class.
    public void addCategories(List<String> other) {
        categories.addAll(other);
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public List<String> getDefaultCategories() { return defaultCategories; }
    public List<String> getCategories() {
        return categories;
    }



}
