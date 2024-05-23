package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.Customer;
import model.BookCopy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BookCopyTest {
    int bookCopyListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01");
    BookCopy bookCopy = new BookCopy(book);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));

    @BeforeEach
    void setUp() {
        controller.getBooks().put(book, new ArrayList<>());
        controller.getBookCopys(book).add(bookCopy);
        controller.getCustomers().add(customer);
        bookCopyListSize = controller.getBookCopys(book).size();
    }


    @Test
    void deleteBookCopySuccessfully() throws BorrowingNotNullException {
        controller.deleteBookCopy("VOL01_1");
        assertEquals(bookCopyListSize - 1, controller.getBookCopys(book).size());
        assertFalse(controller.getBookCopys(book).contains(bookCopy));
    }

    @Test
    void deleteBookCopyUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deleteBookCopy("wrong id");
        });
        assertEquals(bookCopyListSize, controller.getBookCopys(book).size());
        assertTrue(controller.getBookCopys(book).contains(bookCopy));
    }

    @Test
    void deleteBookCopyWhenBorrowingNotNull() {
        bookCopy.setBorrower(customer);
        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteBookCopy("VOL01_1");
        });
        assertEquals(bookCopyListSize, controller.getBookCopys(book).size());
        assertTrue(controller.getBookCopys(book).contains(bookCopy));
    }


    @Test
    void addBookCopySuccessfully() {
        assertTrue(controller.addBookCopy("123456"));
        assertEquals(bookCopyListSize + 1, controller.getBookCopys(book).size());
        assertTrue(controller.getBookCopys(book).contains(bookCopy));
    }

    @Test
    void addBookCopyUnsuccessful() {
        assertFalse(controller.addBookCopy("wrong"));
        assertEquals(bookCopyListSize , controller.getBookCopys(book).size());
    }



}