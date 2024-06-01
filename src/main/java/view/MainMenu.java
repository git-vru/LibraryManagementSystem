package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class MainMenu extends View {
    private char inputChar;

    private final List<String> options;

    public MainMenu(Controller controller) {
        super(controller, null);

        this.name = "Main Menu";

        this.options = new ArrayList<>();
        this.options.add("Book Menu");
        this.options.add("Customer Menu");
        this.options.add("Reporting Menu");
        this.options.add("Settings");

        System.out.println("Welcome to the Library Management System!\nPlease type a number or press enter.");
    }

    @Override
    public void show() {inputChar = super.promptMenu(this.options);
        switch (inputChar) {
            case '0' -> controller.setMenu(new BookMenu(controller, this));
            case '1' -> controller.setMenu(new CustomerMenu(controller, this));
            case '2' -> controller.setMenu(new ReportingMenu(controller, this));
            case '3' -> {
                super.promptAndExit("A setting menu will be displayed");
                this.show();
            }
        }
    }
}
