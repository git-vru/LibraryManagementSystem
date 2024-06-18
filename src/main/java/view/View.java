package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.max;

/**
 * Abstract base class representing a view in the application.
 * This class defines common properties and methods that all views must implement.
 */
public abstract class View {
    private final String PROMPT_TO_EXIT = "Press 'q' to " + (this instanceof MainMenu ? "quit." : "go back.");
    private static final String CURSOR = "\n>  ";

    protected Controller controller;
    protected String name;
    protected View prev;

    /**
     * Constructor for initializing a View.
     *
     * @param controller the controller instance for handling user input and other interactions
     * @param previous   the previous view in the view hierarchy
     */
    public View(Controller controller, View previous) {
        this.controller = controller;
        this.prev = previous;
    }

    /**
     * Abstract method to be implemented by subclasses to display the view.
     */
    public abstract void show();

    /**
     * Utility method to center-align text with padding.
     *
     * @param text          the text to be padded
     * @param maxPipeLength the maximum length of the line to pad within
     * @return the centered and padded text
     */
    public static String addPadding(String text, int maxPipeLength) {
        int paddingSize = (maxPipeLength - text.length()) / 2;
        String padding = " ".repeat(paddingSize);
        String centeredName = padding + text + padding;

        if (centeredName.length() % 2 != 0) centeredName += " ";
        if (centeredName.length() < maxPipeLength) centeredName = " " + centeredName + " ";
        return centeredName;
    }

    /**
     * Validates the user input.
     *
     * @param input the user input
     * @param max   the maximum valid number input
     * @return true if the input is valid, false otherwise
     */
    public static boolean validInput(String input, int max) {
        if (input.length() != 1) {
            return false;
        }

        if (input.charAt(0) == 'q') {
            return true;
        }

        return input.charAt(0) >= '0' && input.charAt(0) < Integer.toString(max).charAt(0);
    }

    /**
     * Displays a list of options and prompts the user for input.
     *
     * @param options the list of options to display
     * @param tabMode flag indicating whether to display options in a table format
     * @return the character input by the user
     */
    public char prompt(List<String> options, boolean tabMode) {
        String input;
        boolean isError = false;

        do {
            // Calculate the maximum line size for formatting
            int maxLineSize = Math.max(name.length() - 1, max(options.stream().map(String::length).toList()));

            // Put option's format in table format if tabMode is true
            String lineFormat = tabMode ? "| %-1d | %-" + (maxLineSize) + "s |%n" : " %-1d - %-" + (maxLineSize) + "s %n";

            System.out.print("\n");

            // Display table's header
            if (tabMode) {
                System.out.format("-".repeat(maxLineSize + 8) + "%n");
                System.out.format("|%-" + maxLineSize + "s|%n", View.addPadding(name, maxLineSize + 6));
                System.out.format("|" + "-".repeat(maxLineSize + 6) + "|%n");
            }

            // Display each option
            for (int i = 0; i < options.size(); i++) {
                System.out.format(lineFormat, i, options.get(i));
            }

            // Display table's footer
            if (tabMode) {
                System.out.format("-".repeat(maxLineSize + 8));
            }

            // Display error message if input is invalid
            if (isError) {
                System.out.println("\nPlease only enter a number from 0 to " + (options.size() - 1));
            }

            // Prompt user for input
            System.out.print("\n" + PROMPT_TO_EXIT + CURSOR);

            input = controller.getScanner().next(); //Read user input
            isError = !validInput(input, options.size()); //Validate user input
        }
        while (isError);

        return input.charAt(0);
    }

    /**
     * Displays a message and waits for the user to press 'q' to exit.
     *
     * @param s the message to display
     */
    public void promptAndExit(String s) {
        String input;

        System.out.println(s);

        do {
            System.out.print(PROMPT_TO_EXIT + CURSOR);
            input = controller.getScanner().next();

        }
        while (input.length() != 1 || input.charAt(0) != 'q');
    }
}
