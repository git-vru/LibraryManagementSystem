package view;

import controller.Controller;

import java.util.Scanner;

public class BookMenu implements View {
    private final String name = "Book Menu";
    private Controller controller;
    private View prev;


    public BookMenu(Controller controller, View prev) {
        this.controller = controller;
        this.prev = prev;
    }

    public void show() {
        System.out.println("**Book Menu**\n(0) - Search for a book\n(1) - Add a new book");
        System.out.println("Press 'q' to go back");
        System.out.print("> ");

        String input = controller.getScanner().next();
        while (input.length() != 1 || (input.charAt(0) != 48 && input.charAt(0) != 49 && input.charAt(0) != 'q')) {
            System.out.println("Please only enter 0, 1 or 'q'");
            System.out.print("> ");
            input = controller.getScanner().next();
        }

        if (input.charAt(0) == '0') {
            controller.setMenu(new BookInfo(controller, this));
        }
        else if (input.charAt(0) == '1') {
            //showCustomerMenu();
        }
        else {
            prev.show();
        }
    }
}
