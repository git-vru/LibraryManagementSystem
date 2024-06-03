package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import utilities.CSVreader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BookMenu extends View {
    private final List<String> options1;
    private final List<String> options2;

    public BookMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Book Menu";
        this.options1 = List.of("Search for a book", "Search for a book copy", "Add a new book", "Import Book Copies from CSV file");
        this.options2 = List.of("Add Single Book", "Import Books from CSV file");
    }

    public void show() {
        char inputChar = super.promptMenu(options1);

        if (inputChar == '0') {
            controller.setMenu(new BookSearch(controller, this));
        }
        else if (inputChar == '1') {

        }
        else if (inputChar == '2') {
            inputChar = super.promptOptions(options2);
            if (inputChar == '0'){
                addNewBook();
            } else if (inputChar == '1') {
                importBooksFromCSV();
            }
        }
        else if(inputChar == '3'){
            importBookCopiesFromCSV();
        }
        prev.show();
    }

    private void importBooksFromCSV() {
        int importedBookCount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        List<String[]> importedBooks = CSVreader.parseFile(filePath);

        if (!importedBooks.isEmpty()) System.out.println("Imported Books:");

        for (String[] data : importedBooks) {
            Book book = controller.addBook(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
            if (book != null) {
                System.out.println(book);
                importedBookCount++;
            }
        }

        if (importedBookCount == 0) {
            System.out.printf("Total %d books imported.%n", importedBookCount);
        }
        else {
            System.out.println("No book was imported !");
        }
    }

    private void addNewBook() {
        Book book = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type :'<ISBN>,<Title>,<Author>,<PublicationYear(YYYY-MM-DD)>,<Shelf Location>'");

        String input = scanner.nextLine().trim();
        String[] parts = input.split(",", 5);

        if (parts.length == 5) {
            book = controller.addBook(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim());
        }
        else {
            System.out.println("Invalid input format. Please try again.");
        }

        if (book != null) {
            System.out.println("Book added successfully !");
            System.out.println(book);
        }
    }

    private void importBookCopiesFromCSV() {
        int importedBookCopyCount = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path to the CSV file: ");
        String filePath = scanner.nextLine().trim();

        List<String[]> importedBookCopies = CSVreader.parseFile(filePath);

        if (!importedBookCopies.isEmpty()) System.out.println("Imported Books:");

        for (String[] data : importedBookCopies) {
            BookCopy bookCopy = controller.addBookCopy(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim());

            if (bookCopy != null) {
                System.out.println(bookCopy);
                importedBookCopyCount++;
            }
        }

        if (importedBookCopyCount == 0) {
            System.out.printf("Total %d book copies imported.%n", importedBookCopyCount);
        }
        else {
            System.out.println("No book was imported !");
        }
    }
    // if book copies book doesnt exist
    //if book copy is already borowwed by a customer

    private void addNewBookCopy() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type: '<ISBN>'");

        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            Controller controller = new Controller();
            Book book = controller.searchBookViaIsbn(input);

            if (book != null) {
                // Assuming you want to add a new book copy with status false by default
                BookCopy bookCopy = new BookCopy(book, false);
                // Add the book copy to your collection here
                controller.getBookCopies(book).add(bookCopy);
                System.out.println("Book copy added successfully:");
                System.out.println(bookCopy);
            }
            else {
                System.out.println("Book not found for ISBN: " + input);
            }
        }
        else {
            System.out.println("ISBN cannot be empty. Please try again.");
        }
    }
}
