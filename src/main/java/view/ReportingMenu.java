package view;

import controller.Controller;

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
        this.options.add("Return a borrowing");
    }

    public void show() {
        String input = super.promptMenu(options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new ReportingInfo(controller, this));
        }
        else if (input.charAt(0) == '1') {
            System.out.println("Please enter customer id and book id: ");
            String customerId = controller.getScanner().next();
            String bookCopyId = controller.getScanner().next();

            try {
                controller.borrowBookCopy(controller.searchCustomer(customerId),controller.searchbookCopy(controller.searchBook("1"),bookCopyId));
                super.promptAndExit("Book was successfully borrowed!");
            }   catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }

            this.show();
        }
        else if (input.charAt(0) == '2') {
            System.out.println("Please enter customer id and book id: ");

            try {
                controller.returnBookCopy(controller.searchCustomer(controller.getScanner().next()), controller.searchbookCopy(controller.searchBook("1"),controller.getScanner().next()));
                super.promptAndExit("Book was successfully returned!");
            }   catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }

            this.show();
        }
        else {
            prev.show();
        }
    }
}
