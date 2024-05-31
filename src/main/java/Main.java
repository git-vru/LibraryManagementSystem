import controller.Controller;
import model.Book;
import model.Customer;
import model.BookCopy;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller();
        controller.getCustomers().add(new Customer("Max", "Mustermann", LocalDate.of(2024, 11, 1)));

        Book book = new Book("Les Fleurs du Mal", "Charles Baudelaire", "978-2-290-11507-7", LocalDate.of(1857, 6, 21), "BAU01");

        controller.getBooks().put(book, new ArrayList<>());
        controller.getBooks().put(new Book("Candide ou l'Optimisme", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01"), new ArrayList<>());
        controller.getBooks().put(new Book("Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", LocalDate.of(1968, 5, 1), "DIC01"), new ArrayList<>());

        for (int i = 0; i < 3; i++) {
            controller.getBooks().get(book).add(new BookCopy(book));
        }

        controller.getCustomers().get(0).getBorrowedList().add(controller.getBooks().get(book).get(0));
        controller.getBooks().get(book).get(0).setBorrower(controller.getCustomers().get(0));
        controller.getBooks().get(book).get(0).setBorrowedDate(LocalDate.now());
        controller.getBooks().get(book).get(0).setReturnedDate(LocalDate.now().plusWeeks(2));
        controller.getMenu().show();
    }
}