import controller.Controller;
import model.Book;
import model.Customer;
import model.BookCopy;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.getCustomers().add(new Customer("Max", "Mustermann", LocalDate.of(2024, 11, 1)));

        Book book = new Book("Les Fleurs du Mal", "Charles Baudelaire", "1", LocalDate.of(1857, 6, 21), "BAU01");
        controller.getBooks().put(book, new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            controller.getBooks().get(book).add(new BookCopy(book));
        }
        controller.getMenu().show();
    }
}