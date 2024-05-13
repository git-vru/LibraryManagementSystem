package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import controller.Controller;

public class MainMenu implements View{
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[0m";

    private List<String> menuOptions = new ArrayList<>();
    private Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;

        menuOptions.add("Book Menu");
        menuOptions.add("Customer Menu");
        menuOptions.add("Reporting Menu");
    }

    public void show() {
        System.out.println("Welcome to the Library Management System!\nPlease select an option and press enter.");

        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.printf("(%d) - %s\n", i, menuOptions.get(i));
        }
        System.out.println("Press 'q' to quit.");
        System.out.print("> ");

        String input = controller.getScanner().next();
        while (input.length() != 1 || (input.charAt(0) != 'q' && (input.charAt(0) < '0' || input.charAt(0) >= '0' + menuOptions.size()))) {
            System.out.println("Please only enter a number from 0 to " + (menuOptions.size() - 1));
            System.out.print("> ");
            input = controller.getScanner().next();
        }

        if (input.charAt(0) == '0') {
            controller.setMenu(new BookMenu(controller, this));
        }
        else if (input.charAt(0) == '1') {
            controller.setMenu(new CustomerMenu(controller, this));
        }
    }
}
