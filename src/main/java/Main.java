import controller.Controller;
import model.Book;
import model.BookCopy;
import model.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.getCustomers().add(new Customer("Max", "Mustermann", LocalDate.of(2024, 11, 1)));

        Book book1 = new Book("Book One", "Author A", "1234567890", LocalDate.of(2000, 1, 1), "001");
        Book book2 = new Book("Book Two", "Author B", "1234567891", LocalDate.of(2001, 2, 2), "002");
        Book book3 = new Book("Book Three", "Author A", "1234567892", LocalDate.of(2002, 3, 3), "003");
        Book book4 = new Book("Book Four", "Author C", "1234567893", LocalDate.of(2003, 4, 4), "004");
        Book book5 = new Book("Book Five", "Author B", "1234567894", LocalDate.of(2004, 5, 5), "005");
        controller.getBookDatabase().put(book1, new ArrayList<>());
        controller.getBookDatabase().put(book2, new ArrayList<>());
        controller.getBookDatabase().put(book3, new ArrayList<>());
        controller.getBookDatabase().put(book4, new ArrayList<>());
        controller.getBookDatabase().put(book5, new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            controller.getBookDatabase().get(book1).add(new BookCopy(book1));
            controller.getBookDatabase().get(book2).add(new BookCopy(book2));
            controller.getBookDatabase().get(book3).add(new BookCopy(book3));
            controller.getBookDatabase().get(book4).add(new BookCopy(book4));
            controller.getBookDatabase().get(book5).add(new BookCopy(book5));
        }
        controller.getMenu().show();
    }
}