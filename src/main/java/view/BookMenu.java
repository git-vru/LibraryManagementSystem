package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import utilities.CSVreader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookMenu extends View {
    private final List<String> options1;
    private final List<String> options2;
    private final List<String> options3;



    public BookMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Book Menu";
        this.options1 = List.of("Search for a book", "Add a new book", "Add a new book copy");
        this.options2 = List.of("Add Single Book", "Import Books from CSV file");
        this.options3 = List.of("Add Single Book Copy", "Import Book Copies from CSV file");
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
        } else if(inputChar == '2'){
            inputChar = super.promptOptions(options3);
            if (inputChar == '0') {
                addNewBookCopy();
            } else if (inputChar == '1') {
                importBookCopiesFromCSV();
            }
        }
        prev.show();
    }

    private void importBooksFromCSV() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        try {
            List<Book> importedBooks = CSVreader.makeBooks(filePath);
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
                System.out.println("Book added successfully:");
                System.out.println(book);
            } else {
                System.out.println("Invalid input format. Please try again.");
            }
        } else {
            System.out.println("Invalid command. Please use the 'create' command.");
        }
    }
    private void importBookCopiesFromCSV() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        try {
            List<BookCopy> importedBookCopies = CSVreader.makeBookCopies(filePath);
            if (!importedBookCopies.isEmpty()) System.out.println("Imported Book Copies:");
            for (BookCopy bookCopy : importedBookCopies) {
                System.out.println(bookCopy);
            }
            System.out.printf("Total %d book copies imported.%n", importedBookCopies.size());
        } catch (IOException e) {
            System.err.println("An error occurred while reading the CSV file: " + e.getMessage());
        }
    }
    // if book copies book doesnt exist
    //if book copy is already borowwed by a customer

    private void addNewBookCopy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type: 'create <ISBN>'");

        String input = scanner.nextLine().trim();
        if (input.startsWith("create ")) {
            String isbn = input.substring(7).trim();
            if (!isbn.isEmpty()) {
                Controller controller = new Controller();
                Book book = controller.searchBookViaIsbn(isbn);

                if (book != null) {
                    // Assuming you want to add a new book copy with status false by default
                    BookCopy bookCopy = new BookCopy(book, false);
                    // Add the book copy to your collection here
                    controller.getBookCopies(book).add(bookCopy);
                    System.out.println("Book copy added successfully:");
                    System.out.println(bookCopy);
                } else {
                    System.out.println("Book not found for ISBN: " + isbn);
                }
            } else {
                System.out.println("ISBN cannot be empty. Please try again.");
            }
        } else {
            System.out.println("Invalid command. Please use the 'create <ISBN>' command.");
        }
    }
}
