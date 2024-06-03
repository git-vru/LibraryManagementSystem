package view;

import controller.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.max;

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

    public static String addPadding2Text(String text, int maxPipeLength) {
        int boxWidth = maxPipeLength;
        int paddingSize = (boxWidth - text.length()) / 2;
        String padding = " ".repeat(paddingSize);
        String centeredName = padding + text + padding;
        if (centeredName.length() < boxWidth - 2) centeredName += " ";
        if (maxPipeLength % 2 == 0) centeredName += " ";
        return centeredName;
    }

    private void printOptions(List<String> options, boolean isError) {
        int maxOptionLength = max(options.stream().map(String::length).toList()) + 2; // 2 for spaces
        int maxPipeLength = maxOptionLength + 4;

        String leftAlignFormat = "| %-1d | %-" + (maxOptionLength-2) + "s |%n";

        System.out.format("|" + "-".repeat(maxPipeLength) + "|%n");
        System.out.format("|%-" + maxOptionLength + "s|%n", addPadding2Text(this.name, maxPipeLength));
        System.out.format("|---|" + "-".repeat(maxOptionLength) + "|%n");
        for (int i = 0; i < options.size(); i++) {
            System.out.format(leftAlignFormat, i, options.get(i));
        }
        System.out.format("|---|" + "-".repeat(maxOptionLength) + "|%n");

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
        return promptOptions(options);
    }

    public void promptAndExit(String s) {
        System.out.println(s);
        System.out.print(PROMPT_TO_EXIT + CURSOR);
        String input = controller.getScanner().next();

        while (input.length() != 1 || input.charAt(0) != 'q') {
            System.out.print(PROMPT_TO_EXIT + CURSOR);
            input = controller.getScanner().next();
        }
    }
}
