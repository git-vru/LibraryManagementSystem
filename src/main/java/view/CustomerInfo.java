package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Customer;

import java.util.List;

public class CustomerInfo extends View {

    public CustomerInfo(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Customer Info";
    }

    public void show() {
        Customer customer = null;
        while (customer == null) {
            System.out.print("Please enter a customer id:");
            String customerId = controller.getScanner().next();

            customer = controller.searchCustomer(customerId);

            if (customer == null) {
                System.out.println("---\nPlease enter a valid customer ID!\n");
                continue;
            }

            this.name = "Customer Id: " + customerId;

            System.out.printf("\t** %s **\n", customerId);
            System.out.println(customer);

            List<String> options = List.of("Delete the customer : " + customerId, "Borrow a book for customer : "+customerId, "Return a book for customer : " + customerId);


            String input = super.promptOptions(options);

            if (input.charAt(0) == '0') {
                try  {
                    controller.deleteCustomer(customerId);
                    System.out.println("Customer with the id : " + customerId + " has been successfully deleted!");
                } catch (BorrowingNotNullException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.charAt(0) == '1') {
                System.out.println("Please enter book id: ");

                try {
                    controller.borrowBookCopy(customer, controller.searchbookCopy(controller.getScanner().next()));
                    super.promptAndExit("Book was successfully borrowed!");
                }   catch (IllegalArgumentException illegalArgumentException) {
                    super.promptAndExit(illegalArgumentException.getMessage());
                }

                this.show();


            } else if (input.charAt(0) == '2') {
                System.out.println("Please enter book id: ");

                try {
                    controller.returnBookCopy(customer, controller.searchbookCopy(controller.getScanner().next()));
                    super.promptAndExit("Book was successfully returned!");
                }   catch (IllegalArgumentException illegalArgumentException) {
                    super.promptAndExit(illegalArgumentException.getMessage());
                }

                this.show();
            } else if (input.charAt(0) == 'q') {
                break;
            }
        }
        prev.show();
    }
}
