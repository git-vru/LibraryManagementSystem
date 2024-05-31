package view;

import controller.Controller;
import model.Book;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookMenu extends View {
    private final List<String> options;
    private final List<String> options2;
    private CSVreader reader;

    public BookMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Book Menu";
        this.reader = new CSVreader();
        this.options = new ArrayList<>();
        this.options.add("Search for a book");
        this.options.add("Add a new book");
        this.options2 = new ArrayList<>();
        this.options2.add("Add Single Book");
        this.options2.add("Import Books from CSV file");

    }

    public void show() {
        String input = super.promptMenu(options);

        if (input.charAt(0) == '0') {
            controller.setMenu(new BookSearch(controller, this));
        }
        else if (input.charAt(0) == '1') {
            //controller.setMenu(new ImportBook(controller,this));
            input = super.promptOptions(options2);
            if (input.charAt(0) == '0'){
                addNewBook();
            } else if (input.charAt(0) == '1') {
                importBooksFromCSV();
            }
            else {
                prev.show();
            }
            //controller.getBooks().put(new Book("Les Fleurs du Mal", "Charles Baudelaire", "isbn", LocalDate.of(1857, 6, 21), "BAU01"), new ArrayList<>());
            //super.promptAndExit("Book with new id XYZ was successfully added!");
            //this.show();
        }
        else {
            prev.show();
        }
    }
    private void importBooksFromCSV() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the path to the CSV file:");
        String filePath = scanner.nextLine().trim();

        try {
            List<Book> importedBooks = reader.makeBooks(filePath);
            System.out.println("Imported Books:");
            for (Book book : importedBooks) {
                System.out.println(book);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
    private void addNewBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type : 'create <ISBN>,<Title>,<Author>,<PublicationYear(YYYY-MM-DD)>'");

        String input = scanner.nextLine().trim();
        if (input.startsWith("create ")) {
            String bookDetails = input.substring(7).trim();
            String[] parts = bookDetails.split(",", 4);
            if (parts.length == 4) {
                String isbn = parts[0].trim();
                String title = parts[1].trim();
                String author = parts[2].trim();
                LocalDate publicationYear = LocalDate.parse(parts[3].trim());

                Book book = new Book(title, author, isbn, publicationYear, "supa_digga");
                //importedBooks.add(book);
                System.out.println("Book added successfully:");
                System.out.println(book);
            } else {
                System.out.println("Invalid input format. Please try again.");
            }
        } else {
            System.out.println("Invalid command. Please use the 'create' command.");
        }
    }
}
