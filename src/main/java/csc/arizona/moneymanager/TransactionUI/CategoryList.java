package csc.arizona.moneymanager.TransactionUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CategoryList {
    List<String> categories;

    public CategoryList(String filename) {
        // load in default files
        categories = new ArrayList<String>();
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while(scanner.hasNextLine()) {
            String category = scanner.nextLine();
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

    public List<String> getCategories() {
        return categories;
    }



}
