package view;

import controller.Controller;
import model.Book;

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
        char inputChar = super.promptMenu(options);

        if (inputChar == '0') {
            controller.setMenu(new BookSearch(controller, this));
        }
        else if (inputChar == '1') {
            System.out.println("A prompt to add a book will be displayed. " +
                    "Alternatively a csv file can be imported\n" +
                    "Ex: Please type : 'create <Title>,<Author>,<Date of publication>,<classification number>,<nb of copies>'" +
                    "or 'import <filepath>");

            controller.getBookDatabase().put(new Book("Les Fleurs du Mal", "Charles Baudelaire", "isbn", LocalDate.of(1857, 6, 21), "BAU01"), new ArrayList<>());
            super.promptAndExit("Book with new id XYZ was successfully added!");
            this.show();
        }
        else {
            prev.show();
        }
    }
}
