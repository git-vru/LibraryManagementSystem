package view;

import controller.Controller;

import java.util.Scanner;

public class BookInfo implements View {
    private final String name = "Book Info";
    private Controller controller;
    private View prev;

    public BookInfo(Controller controller, View prev) {
        this.controller = controller;
        this.prev = prev;
    }
    
    public void show() {
        System.out.print("Please enter a book id:");
        controller.getScanner().next();
        System.out.println("**Book Id: 2024TUM01**\n---\nTitle:1984\nAuthor:George Orwell\nDate:1948\n---\nPhysical Copy:\n...\n...");
        System.out.println("Press 'q' to go back");

        String input = controller.getScanner().next();
        while (input.length() != 1 || input.charAt(0) != 113) {
            System.out.println("Please only enter 'q'");
            System.out.print("> ");
            input = controller.getScanner().next();
        }

        if (input.charAt(0) == 'q') {
            prev.show();
        }
    }
}
