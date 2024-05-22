package controller;

import model.Book;
import model.Borrowing;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    int bookListSize;
    int customerListSize;
    int borrowingListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire", "isbn02", LocalDate.of(1759, 1, 1), "VOL01", 5);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));
    Borrowing borrowing = new Borrowing(book.getBookList().get(0), customer);

    @BeforeEach
    void setUp() {
        controller.getBooks().add(book);
        controller.getCustomers().add(customer);
        controller.getBorrowing().add(borrowing);
        customerListSize = controller.getCustomers().size();
        bookListSize = controller.getBooks().size();
        borrowingListSize = controller.getBorrowing().size();
    }

    @Test
    void deleteBookSuccessfully() {
        assertTrue(controller.deleteBook("isbn02"));
        assertEquals(bookListSize - 1, controller.getBooks().size());
        assertFalse(controller.getBooks().contains(book));
    }

    @Test
    void deleteBookUnsuccessful() {
        assertFalse(controller.deleteBook("wrong isbn"));
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().contains(book));
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
    void deleteBorrowingSuccessfully() {
        assertTrue(controller.deleteBorrowing("Generated consecutively"));
        assertEquals(borrowingListSize - 1, controller.getBorrowing().size());
        assertFalse(controller.getBorrowing().contains(borrowing));
    }

    /*@Test
    void deleteBorrowingUnsuccessful() {
        assertFalse(controller.deleteCustomer("0378184"));
        assertEquals(customerListSize, controller.getCustomers().size());
        assertTrue(controller.getCustomers().contains(customer));
    }*/


}