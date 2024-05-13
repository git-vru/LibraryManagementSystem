package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String PURPLE = "\u001B[35m";
    private static final String WHITE = "\u001B[0m";

    static Scanner sc = new Scanner(System.in);
    static List<String> menuOptions = new ArrayList<>();
    static int menuIndex = 0;


    public static void main(String[] args) throws IOException {
        menuOptions.add("Book Menu");
        menuOptions.add("Customer Menu");
        menuOptions.add("Reporting Menu");

        System.out.println("Welcome to the Library Management System!\nPlease select an option and press enter.");

        for (int i = 0; i < menuOptions.size(); i++) {
            System.out.printf("(%d) - %s\n", i, menuOptions.get(i));
        }
        System.out.print("> ");

        String input = sc.next();
        while (input.length() != 1 || input.charAt(0) < 48 || input.charAt(0) >= 48 + menuOptions.size()) {
            System.out.println("Please only enter a number from 0 to " + (menuOptions.size() - 1));
            System.out.print("> ");
            input = sc.next();
        }

        if (input.charAt(0) == '0') {
            showBookMenu();
        }
        else if (input.charAt(0) == '1') {
            showCustomerMenu();
        }
    }

    public static void showBookMenu() {
        System.out.println("**Book Menu**\n(0) - Search for a book\n(1) - Add a new book\n(9) - Return");
        System.out.print("> ");

        String input = sc.next();
        while (input.length() != 1 || (input.charAt(0) != 48 && input.charAt(0) != 49 && input.charAt(0) != 57)) {
            System.out.println("Please only enter 0, 1 or 9");
            System.out.print("> ");
            input = sc.next();
        }
    }

    public static void showCustomerMenu() {
        System.out.println("**Customer Menu**\n(0) - Search for a customer\n(1) - Add a new customer");
        System.out.print("> ");

        String input = sc.next();
        while (input.length() != 1 || input.charAt(0) < 48 || input.charAt(0) > 49) {
            System.out.println("Please only enter 0 or 1");
            System.out.print("> ");
            input = sc.next();
        }
    }
}