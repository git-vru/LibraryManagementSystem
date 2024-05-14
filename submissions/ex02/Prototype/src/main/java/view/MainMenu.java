package view;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;

public class MainMenu extends View{
    private final List<String> options;

    public MainMenu(Controller controller) {
        super(controller, null);

        this.name = "Main Menu";

        this.options = new ArrayList<>();
        this.options.add("Book Menu");
        this.options.add("Customer Menu");
        this.options.add("Reporting Menu");
    }

    @Override
    public void show() {
        System.out.println("Welcome to the Library Management System!\nPlease type a number or press enter.");
        String input = super.prompt(this.options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new BookMenu(controller, this));
        }
        else if (input.charAt(0) == '1') {
            controller.setMenu(new CustomerMenu(controller, this));
        }
        else if (input.charAt(0) == '2') {
            controller.setMenu(new ReportingMenu(controller, this));
        }
    }
}
