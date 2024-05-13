package command;

import java.util.List;

public class Book {
    private static final String name = "Book Menu";
    private static List<String> commands;

    Book() {
        commands.add("Search");
        commands.add("Add");
    }
}
