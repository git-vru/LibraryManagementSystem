package view;

import controller.Controller;
import model.BookCopy;
import model.Customer;

import java.util.Comparator;
import java.util.List;

public class BorrowingMenu extends View {
    private final List<String> options1;

    public BorrowingMenu(Controller controller, View previous) {
        super(controller, previous);

        this.name = "Borrowing Menu";

        this.options1 = List.of("Create a new borrowing","Return a book");
    }

    public void show() {
        char inputChar = super.prompt(options1, true);

        if (inputChar == '0') {
            System.out.print("Please enter a customer id: ");
            String customerId = controller.getScanner().next();

            List<Customer> customerList = controller.searchCustomer(c -> c.getId().equals(customerId), Comparator.comparing(Customer::getLastName));

            if (customerList.isEmpty() || customerList.get(0) == null) {
                System.out.println("---\nNo customer with this id has been found!\n");
                prev.show();
            }

            System.out.print("Please enter a book copy id: ");
            String bookCopyId = controller.getScanner().next();
            List<BookCopy> bookCopyList = controller.searchBookCopy(bc -> bc.getId().equals(bookCopyId), Comparator.comparing(BookCopy::getId));

            if (bookCopyList.isEmpty() || bookCopyList.get(0) == null) {
                System.out.println("---\nNo book with this id has been found!\n");
                prev.show();
            }

            try {
                controller.borrowBookCopy(customerList.get(0), bookCopyList.get(0));
                super.promptAndExit("Book was successfully borrowed!");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }
        }
        else if (inputChar == '1') {
            System.out.print("Please enter a book copy id: ");
            String bookCopyId = controller.getScanner().next();
            List<BookCopy> bookCopyList = controller.searchBookCopy(bc -> bc.getId().equals(bookCopyId), Comparator.comparing(BookCopy::getId));

            if (bookCopyList.isEmpty() || bookCopyList.get(0) == null) {
                System.out.println("---\nNo book with this is has been found!\n");
                prev.show();
            }

            try {
                controller.returnBookCopy(controller.searchCustomer(c -> c.getBorrowedList().contains(bookCopyList.get(0)), Comparator.comparing(Customer::getId)).get(0), bookCopyList.get(0));
                super.promptAndExit("Book was successfully returned!");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }
        }
            prev.show();
    }
}
