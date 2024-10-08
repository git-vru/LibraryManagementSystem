package controller;

import model.Book;
import model.BookCopy;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;



public class ControllerTest {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    Controller controller = new Controller();
    Book book = new Book("abc", "bcd", "isbn08", LocalDate.parse("12/12/2001", DATE_FORMAT), "987", "Lorem Ipsum");
    BookCopy bookCopy = new BookCopy(book);
    Customer customer = new Customer("Elon", "Musk", LocalDate.parse("01/01/2000", DATE_FORMAT));

    @BeforeEach
    void setup() {


    }

    @Test
    void borrowBookCopySuccessful() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getCustomers().add(customer);

        controller.borrowBookCopy(customer, bookCopy);
        assertTrue(bookCopy.isBorrowed());
        assertEquals(1, customer.getBorrowedList().size());
        assertEquals(bookCopy, customer.getBorrowedList().get(0));

    }

    @Test
    void borrowBookCopyFail() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getCustomers().add(customer);
        bookCopy.setCustomerId("1");

        assertThrows(IllegalArgumentException.class, () -> {
            controller.borrowBookCopy(customer, bookCopy);
        });

        assertTrue(customer.getBorrowedList().isEmpty());
        assertTrue(bookCopy.isBorrowed());


    }

    @Test
    void returnBookCopySuccessful() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getCustomers().add(customer);
        controller.borrowBookCopy(customer, bookCopy);

        controller.returnBookCopy(customer, bookCopy);
        assertFalse(bookCopy.isBorrowed());
        assertNull(bookCopy.getBorrowedDate());
        assertTrue(customer.getBorrowedList().isEmpty());


    }

    @Test
    void returnBookFail() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getCustomers().add(customer);


        assertThrows(IllegalArgumentException.class, () -> {
            controller.returnBookCopy(customer, bookCopy);
        });
        assertFalse(bookCopy.isBorrowed());
        assertFalse(customer.getBorrowedList().contains(bookCopy));

    }
}
