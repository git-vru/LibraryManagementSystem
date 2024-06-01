package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class BorrowingMenu extends View {
    private final List<String> options;

    public BorrowingMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Borrowing Menu";

        this.options = new ArrayList<>();
        this.options.add("Search for a borrowing");
        this.options.add("Add a new borrowing");
    }

    public void show() {
        char input = super.promptMenu(options);

        if (input == '0') {
            controller.setMenu(new BorrowingInfo(controller, this));
        }
        else if (input == '1') {
            System.out.println("A prompt to add a borrowing will be displayed. " +
                    "Alternatively a csv file can be imported\n" +
                    "Ex: Please type : 'create <Book Id>,<Customer Id>,<Start Date>,<End Date>'" +
                    "or 'import <filepath>");

            super.promptAndExit("Book with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }
}
