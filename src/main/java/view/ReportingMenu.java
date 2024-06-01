package view;

import controller.Controller;
import model.Book;
import model.BookCopy;
import model.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    }

    @Override
    public void show() {
        String input = super.promptMenu(this.options);

        switch (input.charAt(0)) {
            case '0':
                List<String[]> bookList = controller.searchBook(book -> true, Comparator.comparing(Book::getTitle)).stream().map(book -> book.toCsv().split(";")).toList();
                printTable("List of all books", new String[]{"%-50s", "%-30s", "%-7s", "%17s"}, new String[]{"TITLE", "AUTHOR", "DATE", "ISBN"}, bookList, 117);
                break;
            case '1':
                List<String[]> borrewedBookList = controller.searchBookCopy(BookCopy::isBorrowed, Comparator.comparing(bookCopy -> bookCopy.getBorrower().getId())).stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all borrowed book copies", new String[]{"%-50s", "%-30s", "%-7s", "%17s", "%11s", "%-10s", "%11s", "%-5s"}, new String[]{"TITLE", "AUTHOR", "DATE", "ISBN", "Borrower Id", "Start Date", "Return Date", "Fees"}, borrewedBookList, 166);
                break;
            case '2':
                List<String[]> notBorrewedBookList = controller.searchBookCopy(bookCopy -> !bookCopy.isBorrowed(), Comparator.comparing(bookCopy -> bookCopy.getBorrower().getId())).stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all available book copies", new String[]{"%-50s", "%-30s", "%-7s", "%17s", "%11s", "%-10s", "%11s", "%-5s"}, new String[]{"TITLE", "AUTHOR", "DATE", "ISBN", "Borrower Id", "Start Date", "Return Date", "Fees"}, notBorrewedBookList, 166);
                break;
            case '3':
                printTable("List of all customers", new String[]{"%8s", "%-30s", "%-30s", "%13s", "%17s", "%-20s"}, new String[]{"Id", "First Name", "Last Name", "Date of Birth", "Subscription Date", "Nb of borrowed books"}, controller.getCustomers().stream().map(customer -> customer.toCsv().split(";")).toList(), 137);
                break;
            case '4':
                System.out.println("Please enter a customer Id:");
                String customerId = controller.getScanner().next();

                List<String[]> borrowedBookListFromCustomer = controller.searchCustomer(c -> c.getId().equals(customerId), Comparator.comparing(Customer::getId)).get(0).getBorrowedList().stream().map(bookCopy -> bookCopy.toCsv().split(";")).toList();
                printTable("List of all borrowed book copies for customer n°" + customerId, new String[]{"%-50s", "%-30s", "%-7s", "%17s", "%11s", "%-10s", "%11s", "%-5s"}, new String[]{"TITLE", "AUTHOR", "DATE", "ISBN", "Borrower Id", "Start Date", "Return Date", "Fees"}, borrowedBookListFromCustomer, 166);
                break;
            default:
                break;
        }
    }

    public void printTable(String name, String[] format, String[] columnNames, List<String[]> columns, int lineSize) {
        String spaces = " ".repeat((lineSize - name.length()) / 2);
        System.out.printf("%s%s%s%n", spaces, name, spaces);
        System.out.printf("%s%n| ", "-".repeat(lineSize));

        for (int i = 0; i < format.length; i++) {
            System.out.printf(format[i] + " | ", columnNames[i]);
        }
        System.out.printf("%n%s%n", "-".repeat(lineSize));

        for (String[] column : columns) {
            System.out.print("| ");
            for (int i = 0; i < format.length; i++) {
                System.out.printf(format[i] + " | ", column[i]);
            }
            System.out.print("\n");
        }

        System.out.printf("%s%n", "-".repeat(lineSize));
    }
}
