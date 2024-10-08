package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.BookCopy;
import model.Customer;

import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

public class CustomerInfo extends View {
    private Customer customer = null;

    public CustomerInfo(Controller controller, View previous) {
        super(controller, previous);

        this.name = "Customer Info";
    }

    public void show() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.print("Please enter a customer id: ");
        String customerId = controller.getScanner().next();

        List<Customer> customerList = controller.searchCustomer(c -> c.getId().equals(customerId), Comparator.comparing(Customer::getLastName));

        if (customerList.isEmpty() || customerList.get(0) == null) {
            System.out.println("---\nNo customer with this is has been found!\n");
            prev.show();
        }

        this.customer = customerList.get(0);
        this.name = "Customer Id: " + customerId;

        System.out.println(customer);
        if (!customer.getBorrowedList().isEmpty()) {
            System.out.println("Borrowed Book List:");
            customer.getBorrowedList().forEach(System.out::println);
        }

        List<String> options = List.of("Delete the customer : " + customerId, "Borrow a book for customer : " + customerId, "Return a book for customer : " + customerId, "Modify the first name of customer: " + customerId, "Modify the last name of customer: " + customerId, "Modify the Date of Birth of customer: " + customerId);
        char inputChar;
        inputChar = super.prompt(options, false);

        if (inputChar == 'q') {
            prev.show();
        }
        else if (inputChar == '0') {
            try {
                controller.deleteCustomer(customerId);
                System.out.println("Customer with the id: " + customerId + " has been successfully deleted!");
            } catch (BorrowingNotNullException e) {
                System.out.println(e.getMessage());
            }
        }
        else if (inputChar == '1') {
            System.out.println("Please enter book copy id: ");
            String input = controller.getScanner().next();
            List<BookCopy> bookCopyList = controller.searchBookCopy(bc -> bc.getId().equals(input), Comparator.comparing(BookCopy::getId));

            if (bookCopyList.isEmpty() || bookCopyList.get(0) == null) {
                System.out.println("---\nNo book with this is has been found!\n");
                prev.show();
            }

            try {
                controller.borrowBookCopy(customer, controller.searchBookCopy(copy -> copy.getId().equals(input), Comparator.comparing(BookCopy::getId)).get(0));
                super.promptAndExit("Book was successfully borrowed!");
            }
            catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }

            this.show();
        }
        else if (inputChar == '2') {
            System.out.println("Please enter book copy id: ");
            String input = controller.getScanner().next();
            List<BookCopy> bookCopyList = controller.searchBookCopy(bc -> bc.getId().equals(input), Comparator.comparing(BookCopy::getId));

            if (bookCopyList.isEmpty() || bookCopyList.get(0) == null) {
                System.out.println("---\nNo book with this is has been found!\n");
                prev.show();
            }

            try {
                controller.returnBookCopy(customer, controller.searchBookCopy(copy -> copy.getId().equals(input), Comparator.comparing(BookCopy::getId)).get(0));
                super.promptAndExit("Book was successfully returned!");
            } catch (IllegalArgumentException illegalArgumentException) {
                super.promptAndExit(illegalArgumentException.getMessage());
            }

            this.show();
        }
        else if (inputChar == '3') {
            System.out.println("Please enter a new first name: ");

            try {
                controller.modifyCustomer(customer, controller.getScanner().next(), customer.getLastName(), customer.getDateOfBirth().format(Controller.DATE_FORMAT));
                super.promptAndExit("First name was successfully changed!");
            }
            catch (IllegalArgumentException a) {
                System.out.println((a.getMessage()));

            }
            this.show();
        }
        else if (inputChar == '4') {
            System.out.println("Please enter a new last name: ");

            try {
                controller.modifyCustomer(customer, customer.getFirstName(), controller.getScanner().next(), customer.getDateOfBirth().format(Controller.DATE_FORMAT));
                super.promptAndExit("Last name was successfully changed!");
            }
            catch (IllegalArgumentException a) {
                System.out.println((a.getMessage()));
            }


            this.show();
        }
        else if (inputChar == '5') {
            System.out.println("Please enter dob <YYYY-MM-DD>: ");
            try {
                controller.modifyCustomer(customer, customer.getFirstName(), customer.getLastName(), controller.getScanner().next());
                super.promptAndExit("Dob was successfully changed!");
            } catch (DateTimeParseException dateTimeParseException) {
                super.promptAndExit("Please pass a valid value!");
            }

            this.show();
        }

        this.show();
    }
}
