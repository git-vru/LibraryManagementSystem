package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import model.Customer;

import java.util.*;
import java.util.function.Predicate;

public class ReportingMenu extends View {
    private final List<String> options;

    public ReportingMenu(Controller controller, View prev) {
        super(controller, prev);

        this.name = "Reporting Menu";

        this.options = new ArrayList<>();
        this.options.add("Output all books");
        this.options.add("Output all borrowed book copies");
        this.options.add("Output all NON-borrowed book copies");
        this.options.add("Output all customers");
        this.options.add("Output all borrowed book copies from a specific customer");
        this.options.add("Output number of book copies per publisher");
    }

    @Override
    public void show() {
        char input = super.promptMenu(name, this.options);

        switch (input) {
            case '0':
                List<String[]> bookList = controller.searchBook(book -> true, Comparator.comparing(Book::getTitle)).stream().map(book -> book.toCsv().split(";")).toList();
                printTable("List of all books", bookList, Book.FORMAT, Book.COLUMN_NAMES);
                break;
            case '1':
                List<String[]> borrewedBookList = controller.searchBookCopy(BookCopy::isBorrowed, Comparator.comparing(BookCopy::getId)).stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all borrowed book copies", borrewedBookList, BookCopy.FORMAT, BookCopy.COLUMN_NAMES);
                break;
            case '2':
                List<String[]> notBorrewedBookList = controller.searchBookCopy(Predicate.not(BookCopy::isBorrowed), Comparator.comparing(BookCopy::getId)).stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all available book copies", notBorrewedBookList, BookCopy.FORMAT, BookCopy.COLUMN_NAMES);
                break;
            case '3':
                printTable("List of all customers", controller.getCustomers().stream().map(customer -> customer.toCsv().split(";")).toList(), Customer.FORMAT, Customer.COLUMN_NAMES);
                break;
            case '4':
                System.out.println("Please enter a customer Id:");
                String customerId = controller.getScanner().next();

                List<String[]> borrowedBookListFromCustomer = controller.searchCustomer(c -> c.getId().equals(customerId), Comparator.comparing(Customer::getId)).get(0).getBorrowedList().stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all borrowed book copies for customer n°" + customerId, borrowedBookListFromCustomer, BookCopy.FORMAT, BookCopy.COLUMN_NAMES);
                break;
            case '5':
                List<String[]> data = new ArrayList<>();
                List<String> publishers = controller.getBookDatabase().keySet().stream().map(Book::getPublisher).distinct().sorted().toList();
                int bookCopyNb = controller.searchBookCopy(c -> true, Comparator.comparing(BookCopy::getId)).size();

                for (String p : publishers) {
                    int occurrences = controller.searchBookCopy(copy -> copy.getBook().getPublisher().equals(p), Comparator.comparing(BookCopy::getId)).size();
                    data.add(new String[] {p, Integer.toString(occurrences), (float) (occurrences * 100) / bookCopyNb + "%"});
                }
                printTable("Number of book copies per publisher", data, new String[]{"%-35s", "%11s", "%10s"}, new String[]{"PUBLISHER NAME", "# OF COPIES", "PERCENTAGE"});
                break;
        }
        prev.show();
    }

    public void printTable(String name, List<String[]> rows, String[] format, String[] columnNames) {
        int lineSize = Arrays.stream(format).mapToInt(s -> Integer.parseInt(s.replaceAll("%-?([0-9]{1,2})s", "$1"))).sum() + format.length*3 + 1;
        String spaces = " ".repeat((lineSize - name.length()) / 2);

        System.out.printf("%s%s%s%n", spaces, name, spaces);
        System.out.printf("%s%n| ", "-".repeat(lineSize));

        for (int i = 0; i < columnNames.length; i++) {
            System.out.printf(format[i].isEmpty() ? "%-s" : format[i].replaceAll("%-?([0-9]{1,2})s", "%-$1s") + " | ", columnNames[i]);
        }
        System.out.printf("%n%s%n", "-".repeat(lineSize));

        for (String[] row : rows) {
            System.out.print("| ");

            for (int i = 0; i < row.length; i++) {
                int cellSize = Integer.parseInt(format[i].replaceAll("%-?([0-9]{1,2})s", "$1"));
                System.out.printf(format[i] + " | ", row[i].substring(0, Math.min(row[i].length(), cellSize)) + (Math.max(row[i].length(), cellSize) > cellSize ? "..." : ""));
            }
            System.out.print("\n");
        }

        System.out.printf("%s%n", "-".repeat(lineSize));

        super.promptAndExit("");
        this.show();
    }
}
