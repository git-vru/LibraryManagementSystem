package controller;

import model.Book;
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
        physicalBookList = controller.getPhysicalBooks(book).size();
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
            controller.addBook(book.getTitle(), book.getAuthor(),book.getIsbn(), book.getPublicationDate(), book.getClassificationNumber());
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));
    }

    @Test
    void modifyBookSuccessfully() {
        assertTrue(controller.modifyBook("ugiztr", "wer",book.getIsbn(), book.getPublicationDate(), book.getClassificationNumber()));
        assertEquals("ugiztr", "wer", book.getTitle());
    }

    @Test
    void modifyBookUnsuccessful() {}

    @Test
    void addCustomerSuccessfully() {
        assertTrue(controller.addCustomer(customer.getFirstName(),customer.getLastName(),customer.getDob().toString()));
        assertEquals(customerListSize+1, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));
    }

    @Test
    void addCustomerUnsuccessful() {}

    @Test
    void modifyCustomerSuccessfully() {}

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