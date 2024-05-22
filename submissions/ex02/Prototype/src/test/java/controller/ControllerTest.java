package controller;

import model.Book;
import model.Borrowing;
import model.Customer;
import model.PhysicalBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    int bookListSize;
    int customerListSize;
    int borrowingListSize;
    int physicalBookList;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn", LocalDate.of(1759, 1, 1), "VOL01");
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));
    PhysicalBook physicalBook = new PhysicalBook(book);

    @BeforeEach
    void setUp() {
        //controller.getPhysicalBooks(book).add(physicalBook);
        controller.getBooks().put(book, new ArrayList<>());
        controller.getCustomers().add(customer);
        customerListSize = controller.getCustomers().size();
        bookListSize = controller.getBooks().size();
        borrowingListSize = controller.getBorrowing().size();
    }

    @Test
    void deleteBookSuccessfully() {
        controller.deleteBook("isbn02");
        assertEquals(bookListSize - 1, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));
    }

    @Test
    void deleteBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deleteBook("wrong isbn");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));
    }

    @Test
    void deleteCustomerSuccessfully() {
        assertTrue(controller.deleteCustomer(customer.getId()));
        assertEquals(customerListSize - 1, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));
    }

    @Test
    void deleteCustomerUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deleteCustomer("wrong id");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertTrue(controller.getCustomers().contains(customer));
    }

    @Test
    void deletePhysicalBookSuccessfully() {
        controller.deletePhysicalBook("Generated consecutively");
        assertEquals(physicalBookList - 1, controller.getPhysicalBooks().size());
        assertFalse(controller.getPhysicalBooks().contains(physicalBook));
    }

    @Test
    void deletePhysicalBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deletePhysicalBook("wrong id");
        });
        assertEquals(physicalBookList, controller.getPhysicalBooks().size());
        assertTrue(controller.getPhysicalBooks().contains(physicalBook));
    }

    @Test
    void addBookSuccessfully() {
        assertTrue(controller.addBook(book.getTitle(), book.getAuthor(), book.getPublicationDate(), book.getClassificationNumber()));
        assertEquals(bookListSize + 1, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));
    }

    @Test
    void addBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deletePhysicalBook("wrong id");
        });
        assertEquals(physicalBookList, controller.getPhysicalBooks().size());
        assertTrue(controller.getPhysicalBooks().contains(physicalBook));
    }

    @Test
    void modifyBookSuccessfully() {}

    @Test
    void modifyBookUnsuccessful() {}

    @Test
    void addCustomerSuccessfully() {
        assertTrue(controller.addBook(book.getTitle(), book.getAuthor(), book.getPublicationDate(), book.getClassificationNumber()));
        assertEquals(bookListSize + 1, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));
    }

    @Test
    void addCustomerUnsuccessful() {}

    @Test
    void modifyCustomerSuccessfully() {}

    @Test
    void modifyCustomerUnsuccessful() {}

    @Test
    void addPhysicalBookSuccessfully() {}

    @Test
    void addPhysicalBookUnsuccessful() {}

    @Test
    void modifyPhysicalBookSuccessfully() {}

    @Test
    void modifyPhysicalBookUnsuccessful() {}

}