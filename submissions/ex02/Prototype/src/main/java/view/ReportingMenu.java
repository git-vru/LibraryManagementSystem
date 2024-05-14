package view;

import model.Book;
import model.Borrowing;
import model.Customer;
import model.PhysicalBook;
import view.*;
import controller.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportingMenu extends View {
    private final List<String> options;

    public ReportingMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Borrowing Menu";

        this.options = new ArrayList<>();
        this.options.add("Search for a borrowing");
        this.options.add("Add a new borrowing");
    }

    public void show() {
        String input = super.prompt(options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new ReportingInfo(controller, this));
        }
        else if (input.charAt(0) == '1') {
            System.out.println("A prompt to add a borrowing will be displayed. " +
                    "Alternatively a csv file can be imported\n" +
                    "Ex: Please type : 'create <Book Id>,<Customer Id>,<Start Date>,<End Date>'" +
                    "or 'import <filepath>");

            controller.createBorrowing("Book Id", "Customer Id");
            super.promptAndExit("Book with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }
}
