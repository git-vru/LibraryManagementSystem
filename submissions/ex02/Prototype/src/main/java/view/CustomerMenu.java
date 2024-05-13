package view;

import controller.Controller;

public class CustomerMenu implements View {
    private final String name = "Customer Menu";
    private Controller controller;
    private View prev;


    public CustomerMenu(Controller controller, View prev) {
        this.controller = controller;
        this.prev = prev;
    }

    public void show() {
        System.out.println("**Customer Menu**\n(0) - Search for a customer\n(1) - Add a new customer");
        System.out.println("Press 'q' to go back");
        System.out.print("> ");

        String input = controller.getScanner().next();
        while (input.length() != 1 || (input.charAt(0) != 48 && input.charAt(0) != 49 && input.charAt(0) != 'q')) {
            System.out.println("Please only enter 0, 1 or 'q'");
            System.out.print("> ");
            input = controller.getScanner().next();
        }

        if (input.charAt(0) == '0') {
            controller.setMenu(new CustomerInfo(controller, this));
        }
        else if (input.charAt(0) == '1') {
            //showCustomerMenu();
        }
        else {
            prev.show();
        }
    }
}
