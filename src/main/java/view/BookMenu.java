package view;

import controller.Controller;
import model.Book;
import model.CSVreader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookMenu extends View {
    private final List<String> options1;
    private final List<String> options2;
    private final CSVreader reader;

    public BookMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Book Menu";
        this.reader = new CSVreader();
        this.options1 = List.of("Search for a book", "Add a new book");
        this.options2 = List.of("Add Single Book", "Import Books from CSV file");
    }

    public void show() {
        char inputChar = super.promptMenu(options1);

        if (inputChar == '0') {
            controller.setMenu(new BookSearch(controller, this));
        }
        else if (inputChar == '1') {
            inputChar = super.promptOptions(options2);
            if (inputChar == '0'){
                addNewBook();
            } else if (inputChar == '1') {
                importBooksFromCSV();
            }
        }
        prev.show();
    }

    private void importBooksFromCSV() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        try {
            List<Book> importedBooks = reader.makeBooks(filePath);
            if (!importedBooks.isEmpty()) System.out.println("Imported Books:");
            for (Book book : importedBooks) {
                System.out.println(book);
            }
            System.out.printf("Total %d books imported.%n", importedBooks.size());
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
