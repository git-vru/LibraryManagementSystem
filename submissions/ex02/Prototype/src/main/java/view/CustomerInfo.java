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
        Customer c = controller.search(controller.getCustomers(), controller.getScanner().next());

        this.name = "Customer Id: " + c.getId();

        super.promptAndExit(c.toString());
        prev.show();
    }
}
