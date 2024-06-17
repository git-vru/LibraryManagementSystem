package view;

import controller.Controller;
import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import utilities.CSVreader;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BookMenu extends View {
    private final List<String> options1;
    private final List<String> options2;
    private final List<String> options3;

    public BookMenu(Controller controller, View previous) {
        super(controller, previous);

        this.name = "Book Menu";
        this.options1 = List.of("Search for a book", "Search for a book copy", "Add a new book", "Import Book Copies from CSV file");
        this.options2 = List.of("Add Single Book", "Import Books from CSV file");
        this.options3 = List.of("Delete this book copy");
    }

    public void show() {
        char inputChar = super.promptMenu(name, options1);

        if (inputChar == '0') {
            controller.setMenu(new BookSearch(controller, this));
        }
        else if (inputChar == '1') {
            System.out.print("Please enter a book copy id: ");
            String bookCopyId = controller.getScanner().next();
            List<BookCopy> bookCopyList = controller.searchBookCopy(bc -> bc.getId().equals(bookCopyId), Comparator.comparing(BookCopy::getId));

            if (bookCopyList.isEmpty() || bookCopyList.get(0) == null) {
                System.out.println("---\nNo book with this is has been found!\n");
                this.show();
            }

            System.out.println(bookCopyList.get(0));
            inputChar = super.promptOptions(options3);

            if (inputChar == '0') {
                try {
                    controller.deleteBookCopy(bookCopyList.get(0).getId());
                    System.out.println("Book copy with the ID : " + bookCopyList.get(0).getId() + " has been successfully deleted!");
                }
                catch (BorrowingNotNullException e) {
                    System.out.println(e.getMessage());
                }
            }
            this.show();
        }
        else if (inputChar == '2') {
            inputChar = super.promptMenu("Add a book", options2);
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
            Book book = controller.addBook(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim(), 1);
            if (book != null) {
                System.out.println(book);
                importedBookCount++;
            }
        }

        if (importedBookCount > 0) {
            System.out.printf("Total %d books imported.%n", importedBookCount);
        }
        else {
            System.out.println("\nNo book was imported !");
        }
    }

    private void addNewBook() {
        Book book = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type :'<ISBN>,<Title>,<Author>,<Publisher>,<PublicationYear(YYYY-MM-DD)>,<Shelf Location>'");

        String input = scanner.nextLine().trim();
        String[] parts = input.split(",", 5);

        if (parts.length == 6) {
            try {
                book = controller.addBook(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(), parts[4].trim(), parts[5].trim(), 1);
            }
            catch (IllegalArgumentException a) {
                System.out.println("Book could not be added due to wrong argument");
            }
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

        if (!importedBookCopies.isEmpty()) System.out.println("Imported Book Copies:");

        BookCopy bookCopy = null;
        for (String[] data : importedBookCopies) {
            switch (data.length) {
                case 1 -> bookCopy = controller.addBookCopy("", data[0].trim());
                case 2 -> bookCopy = controller.addBookCopy(data[0].trim(), data[1].trim());
                case 5 -> bookCopy = controller.addBorrowedBookCopy("", data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim());
                case 6 -> bookCopy = controller.addBorrowedBookCopy(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim(), data[5].trim());
                default -> System.out.println("Error while parsing data. Bad Arguments number");
            }

            if (bookCopy != null) {
                System.out.println(bookCopy);
                importedBookCopyCount++;
            }
        }

        if (importedBookCopyCount > 0) {
            System.out.printf("Total %d book copies imported.%n", importedBookCopyCount);
        }
        else {
            System.out.println("\nNo book copies was imported !");
        }
    }
}
