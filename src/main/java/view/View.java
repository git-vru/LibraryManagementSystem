package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;

public abstract class View {
    private final String PROMPT_TO_EXIT = "Press 'q' to " + (this instanceof MainMenu ? "quit." : "go back.");
    private final String CURSOR = "\n>  ";

    protected Controller controller;
    protected String name;
    protected View prev;

    public View(Controller controller, View prev) {
        this.controller = controller;
        this.prev = prev;
    }
    public abstract void show();

    private void printOptions(List<String> options, boolean isError) {
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("(%d) - %s\n", i, options.get(i));
        }
        if (isError) {
            System.out.println("Please only enter a number from 0 to " + (options.size() - 1));
        }
        System.out.print(PROMPT_TO_EXIT + CURSOR);
    }

    public char promptOptions(List<String> options) {
        List<Character> validInputs = new ArrayList<>(List.of('q'));
        for (char c = '0'; c < '0' + options.size(); c++) {
            validInputs.add(c);
        }

        printOptions(options, false);

        String input = controller.getScanner().next();
        while (input.length() != 1 || !validInputs.contains(input.charAt(0))) {
            printOptions(options, true);
            input = controller.getScanner().next();
        }

        return input.charAt(0);
    }

    public char promptMenu(List<String> options) {
        System.out.printf("\t** %s **\n", this.name);
        return promptOptions(options);
    }

    public void promptAndExit(String s) {
        System.out.printf("\t** %s **\n", this.name);
        System.out.println(s);
        System.out.print(PROMPT_TO_EXIT + CURSOR);
        String input = controller.getScanner().next();
        while (input.length() != 1 || input.charAt(0) != 'q') {
            System.out.print(PROMPT_TO_EXIT + CURSOR);
            input = controller.getScanner().next();
        }
    }
}
