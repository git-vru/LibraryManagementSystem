package view;

import controller.Controller;
import model.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerMenu extends View {
    private final List<String> options;

    public CustomerMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Customer Menu";

        this.options = new ArrayList<>();
        this.options.add("Search for a customer");
        this.options.add("Add a new customer");

    }

    public void show() {
        String input = super.promptMenu(options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new CustomerInfo(controller, this));

        }
        else if (input.charAt(0) == '1') {
            System.out.println("A prompt to add a customer will be displayed. " +
                    "Alternatively a csv file can be imported\n" +
                    "Ex: Please type : 'create <FirstName>,<LastName>,<DOB>'" +
                    "or 'import <filepath>");

            controller.getCustomers().add(new Customer("Add", "Add", LocalDate.now()));
            super.promptAndExit("Customer with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }
}
