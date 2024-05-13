package view;

import controller.Controller;
import view.*;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomerInfo implements View {
    private final String name = "Book Info";
    private Controller controller;
    private View prev;

    public CustomerInfo(Controller controller, View prev) {
        this.controller = controller;
        this.prev = prev;
    }

    public void show() {
        System.out.print("Please enter a customer id:");
        controller.getScanner().next();
        Customer c = new Customer("Max", "Musterman", LocalDate.of(2024, 11, 1));
        System.out.println(c);
        System.out.println("Press 'q' to go back");

        String input = controller.getScanner().next();
        while (input.length() != 1 || input.charAt(0) != 113) {
            System.out.println("Please only enter 'q'");
            System.out.print("> ");
            input = controller.getScanner().next();
        }
        prev.show();
    }
}
