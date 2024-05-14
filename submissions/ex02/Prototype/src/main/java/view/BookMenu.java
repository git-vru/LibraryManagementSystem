package view;

import controller.*;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookMenu extends View {
    private final List<String> options;

    public BookMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Book Menu";

        this.options = new ArrayList<>();
        this.options.add("Search for a book");
        this.options.add("Add a new book");
    }

    public void show() {
        String input = super.prompt(options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new BookInfo(controller, this));
        }
        else if (input.charAt(0) == '1') {
            System.out.println("A prompt to add a book will be displayed. " +
                    "Alternatively a csv file can be imported\n" +
                    "Ex: Please type : 'create <Title>,<Author>,<Date of publication>,<classification number>,<nb of copies>'" +
                    "or 'import <filepath>");

            controller.getBooks().add(new Book("Les Fleurs du Mal", "Charles Baudelaire", LocalDate.of(1857, 6, 21), "BAU01", 5));
            super.promptAndExit("Book with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }
}
