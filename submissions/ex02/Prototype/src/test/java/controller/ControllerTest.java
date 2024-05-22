package controller;

import model.Book;
import model.Customer;
import model.PhysicalBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    int bookListSize;
    int customerListSize;
    int physicalBookListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01");
    PhysicalBook physicalBook = new PhysicalBook(book);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));

    @BeforeEach
    void setUp() {
        controller.getBooks().put(book, new ArrayList<>());
        controller.getPhysicalBooks(book).add(physicalBook);
        controller.getCustomers().add(customer);
        customerListSize = controller.getCustomers().size();
        bookListSize = controller.getBooks().size();
        physicalBookListSize = controller.getPhysicalBooks(book).size();
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
        assertEquals(physicalBookListSize - 1, controller.getPhysicalBooks(book).size());
        assertFalse(controller.getPhysicalBooks(book).contains(physicalBook));
    }

    @Test
    void deletePhysicalBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deletePhysicalBook("wrong id");
        });
        assertEquals(physicalBookListSize, controller.getPhysicalBooks(book).size());
        assertTrue(controller.getPhysicalBooks(book).contains(physicalBook));
    }

    @Test
    void addBookSuccessfully() {
        assertTrue(controller.addBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationDate(), book.getClassificationNumber()));
        assertEquals(bookListSize + 1, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));
    }

    @Test
    void addBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.addBook(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationDate(), book.getClassificationNumber());
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));
    }

    @Test
    void modifyBookSuccessfully() {
        assertTrue(controller.modifyBook("abc","def", LocalDate.of(2024,5,22),"12"));
        assertEquals("abc", book.getTitle());
        assertEquals("def",book.getAuthor());
        assertEquals("ghi",book.getIsbn());
        assertEquals(LocalDate.of(2024,5,22),book.getPublicationDate());
        assertEquals("12",book.getClassificationNumber());
    }

    @Test
    void modifyBookUnsuccessful() {}

    @Test
    void addCustomerSuccessfully() {
        assertTrue(controller.addCustomer("","Kemmler","2005-08-28"));
        assertEquals(customerListSize+1, controller.getCustomers().size());

        Customer newCustomer = controller.getCustomers().get(controller.getCustomers().size() - 1);
        assertEquals("Samuel", newCustomer.getFirstName());
        assertEquals("Kemmler", newCustomer.getLastName());
        assertEquals(LocalDate.of(2005, 8, 28), newCustomer.getDob());
        assertEquals(LocalDate.now(), newCustomer.getSubscriptionDate());
        assertEquals(0, newCustomer.getBorrowedList().size());
    }

    @Test
    void addCustomerUnsuccessful() {}

    @Test
    void modifyCustomerSuccessfully() {
        assertTrue(controller.modifyCustomer("Unga", "Bunga", LocalDate.of(2001,1,1),LocalDate.of(2024,5,22)));
    }

    @Test
    void modifyCustomerUnsuccessful() {}

    @Test
    void addPhysicalBookSuccessfully() {
        assertTrue(controller.addPhysicalBook(physicalBook.getId(),physicalBook.getBook().toString(),physicalBook.getBorrower().toString(),physicalBook.getBorrowedDate().toString(),physicalBook.getReturnedDate().toString(), String.valueOf(physicalBook.getFee())));
        assertEquals(physicalBookListSize + 1, controller.getPhysicalBooks(book).size());
        assertFalse(controller.getPhysicalBooks(book).contains(physicalBook));
    }

    @Test
    void addPhysicalBookUnsuccessful() {}

    @Test
    void modifyPhysicalBookSuccessfully() {}

    @Test
    void modifyPhysicalBookUnsuccessful() {}

}