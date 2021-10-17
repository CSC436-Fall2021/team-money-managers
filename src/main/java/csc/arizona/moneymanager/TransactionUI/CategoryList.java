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

    // add from database not sure exactly how we are doing this.
    public void addCategories(String filename) {

    }

    public List<String> getCategories() {
        return categories;
    }


}
