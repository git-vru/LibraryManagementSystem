package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.BookCopy;
import model.Customer;

import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;

public class CustomerInfo extends View {
    private char inputChar;
    private Customer customer = null;

    public CustomerInfo(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Customer Info";
    }

    public void show() {
        while (customer == null) {
            System.out.print("Please enter a customer id: ");
            String customerId = controller.getScanner().next();

            customer = controller.searchCustomer(c -> c.getId().equals(customerId), Comparator.comparing(Customer::getLastName)).get(0);

            if (customer == null) {
                System.out.println("---\nPlease enter a valid customer ID!\n");
                continue;
            }

            this.name = "Customer Id: " + customerId;

            System.out.println(customer);

            List<String> options = List.of("Delete the customer : " + customerId, "Borrow a book for customer : "+customerId, "Return a book for customer : " + customerId, "Modify the first name of customer: "+ customerId, "Modify the last name of customer: "+ customerId, "Modify the Date of Birth of customer: "+ customerId);

            inputChar = super.promptOptions(options);

            if (inputChar == '0') {
                try  {
                    controller.deleteCustomer(customerId);
                    System.out.println("Customer with the id: " + customerId + " has been successfully deleted!");
                } catch (BorrowingNotNullException e) {
                    System.out.println(e.getMessage());
                }
            } else if (inputChar == '1') {
                System.out.println("Please enter book id: ");

                try {
                    controller.borrowBookCopy(customer, controller.searchBookCopy(copy -> copy.getId().equals(controller.getScanner().next()), Comparator.comparing(BookCopy::getId)).get(0));
                    super.promptAndExit("Book was successfully borrowed!");
                }   catch (IllegalArgumentException illegalArgumentException) {
                    super.promptAndExit(illegalArgumentException.getMessage());
                }

                this.show();


            } else if (inputChar == '2') {
                System.out.println("Please enter book id: ");

                try {
                    controller.returnBookCopy(customer, controller.searchBookCopy(copy -> copy.getId().equals(controller.getScanner().next()), Comparator.comparing(BookCopy::getId)).get(0));
                    super.promptAndExit("Book was successfully returned!");
                }   catch (IllegalArgumentException illegalArgumentException) {
                    super.promptAndExit(illegalArgumentException.getMessage());
                }

                this.show();
            }
            else if (inputChar == '3') {
                System.out.println("Please enter a new first name: ");

                controller.modifyCustomer(customer, controller.getScanner().next(), customer.getLastName(), customer.getDateOfBirth().toString());
                super.promptAndExit("First name was successfully changed!");

                this.show();
            }
            else if (inputChar == '4') {
                System.out.println("Please enter a new last name: ");

                controller.modifyCustomer(customer, customer.getFirstName(), controller.getScanner().next(), customer.getDateOfBirth().toString());
                super.promptAndExit("Last name was successfully changed!");

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
            } else if (inputChar == 'q') {
                break;
            }
        }
        prev.show();
    }
}
