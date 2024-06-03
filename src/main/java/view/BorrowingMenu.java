package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import utilities.CSVreader;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BorrowingMenu extends View {
    private final List<String> options1;
    private final List<String> options2;

    public BorrowingMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Borrowing Menu";

        this.options1 = List.of("Search for a borrowing","Add a new borrowing");
//        this.options.add("Search for a borrowing");
//        this.options.add("Add a new borrowing");
        this.options2 = List.of("Add Single Book", "Import Books from CSV file");
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
                    controller.getBookCopys(book).add(bookCopy);
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


    public void show() {
        char inputChar = super.promptMenu(options1);

        if (inputChar == '0') {
            controller.setMenu(new BorrowingInfo(controller, this));
        }
        else if (inputChar == '1') {
            inputChar = super.promptOptions(options2);
            if (inputChar == '0') {
                addNewBookCopy();
            } else if (inputChar == '1') {
                importBookCopiesFromCSV();
            }
        }
            prev.show();
    }
}
