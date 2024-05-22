package view;

import controller.Controller;
import model.Customer;

public class CustomerInfo extends View {

    public CustomerInfo(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Customer Info";
    }

    public void show() {
        System.out.print("Please enter a customer id:");

        String customerId = controller.getScanner().next();

        Customer c = controller.searchCustomer(customerId);

        this.name = "Customer Id: " + c.getId();

        super.promptAndExit(c.toString());
        prev.show();
    }
}
