package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class BookCopyTest {
    int bookCopyListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01", "Lorem Ipsum");
    Book testBook = new Book("Les Fleurs du Mal", "Charles Baudelaire","978-3-16-148410-0", LocalDate.of(1800, 2, 2), "BAU01", "Lorem Ipsum");
    BookCopy bookCopy = new BookCopy(book);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));

    @BeforeEach
    void setUp() {
        controller.getBookDatabase().put(testBook, new ArrayList<>());
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getBookCopies(testBook).add(new BookCopy(testBook));
        controller.getCustomers().add(customer);
        bookCopyListSize = controller.getBookCopies(book).size();
    }


    @Test
    void deleteBookCopySuccessfully() throws BorrowingNotNullException {
        controller.deleteBookCopy("VOL01_1");
        assertEquals(bookCopyListSize - 1, controller.getBookCopies(book).size());
        assertFalse(controller.getBookCopies(book).contains(bookCopy));
    }

    @Test
    void deleteBookCopyUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deleteBookCopy("wrong id");
        });
        assertEquals(bookCopyListSize, controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(book).contains(bookCopy));
    }

    @Test
    void deleteBookCopyWhenBorrowingNotNull() {
        bookCopy.setCustomerId("1");
        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteBookCopy("VOL01_1");
        });
        assertEquals(bookCopyListSize, controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(book).contains(bookCopy));
    }

    @Test
    void addBookCopySuccessfully() {
        controller.getBookCopies(testBook).clear();
        controller.addBookCopy("", "978-3-16-148410-0");
        assertEquals(1, controller.getBookCopies(testBook).size());
        assertTrue(controller.getBookCopies(controller.searchBookViaIsbn("978-3-16-148410-0")).contains(controller.getBookCopies(testBook).get(0)));
    }

    @Test
    void addBookCopyUnsuccessful() {
        controller.getBookCopies(testBook).clear();

        assertThrows(IllegalArgumentException.class, () -> controller.addBookCopy("", "isbndtfzghjbknl03"));
        assertEquals(bookCopyListSize , controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(controller.searchBookViaIsbn("978-3-16-148410-0")).isEmpty());

        assertThrows(IllegalArgumentException.class, () -> controller.addBorrowedBookCopy("", "", "1", "04/06/2024", "04/06/2024", "0.0"));
        assertThrows(IllegalArgumentException.class, () -> controller.addBorrowedBookCopy("", "isbn03", "1", "", "04/06/2024", "0.0"));
        assertThrows(IllegalArgumentException.class, () -> controller.addBorrowedBookCopy("", "isbn03", "1", "04/06/2024", "", "0.0"));
        assertThrows(IllegalArgumentException.class, () -> controller.addBorrowedBookCopy("", "isbn03", "1", "04/06/2024", "04/06/2024", ""));

        assertThrows(IllegalArgumentException.class, () ->controller.addBorrowedBookCopy("", "978-3-16-148410-0", "1", "33", "04/06/2024", "10.0"));
        assertEquals(bookCopyListSize , controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(controller.searchBookViaIsbn("978-3-16-148410-0")).isEmpty());

        assertThrows(IllegalArgumentException.class, () ->controller.addBorrowedBookCopy("", "978-3-16-148410-0", "1", "04/06/2024", "111", "0.0"));
        assertEquals(bookCopyListSize , controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(controller.searchBookViaIsbn("978-3-16-148410-0")).isEmpty());

        assertThrows(IllegalArgumentException.class, () ->controller.addBorrowedBookCopy("", "978-3-16-148410-0", "1", "04/06/2024", "04/06/2024", "a"));
        assertEquals(bookCopyListSize , controller.getBookCopies(book).size());
        assertTrue(controller.getBookCopies(controller.searchBookViaIsbn("978-3-16-148410-0")).isEmpty());
    }

    @Test
    void searchBookCopySuccessful() {
        controller.getBookDatabase().get(book).add(new BookCopy(book));
        Predicate<BookCopy> predicate1= copy->copy.getId().equals("VOL01_1");
        Comparator<BookCopy> comparator=Comparator.comparing(BookCopy::getId);

        assertEquals(1,controller.searchBookCopy(predicate1,comparator).size());
        assertEquals(bookCopy,controller.searchBookCopy(predicate1,comparator).get(0));

        //List of all BookCopy
        List<BookCopy> bookCopyList = new ArrayList<>();
        controller.getBookDatabase().values().forEach(bookCopyList::addAll);

        //Sorting Test
        List<BookCopy> results = controller.searchBookCopy(copy -> true, Comparator.comparing(BookCopy::getId));
        assertEquals(bookCopyList.size(), results.size());
        assertEquals("BAU01_1", results.get(0).getId());
        assertEquals("VOL01_2", results.get(results.size() - 1).getId());

        results = controller.searchBookCopy(copy -> true, Comparator.comparing(copy -> copy.getBook().getIsbn()));
        assertEquals("978-3-16-148410-0", results.get(0).getBook().getIsbn());
        assertEquals("isbn02", results.get(results.size() - 1).getBook().getIsbn());
    }

    @Test
    void searchBookCopyWithWrongArgument() {
        Predicate<BookCopy> predicate1= book->bookCopy.getId().equals("abc");
        Comparator<BookCopy> comparator=Comparator.comparing(BookCopy::getId);

        assertEquals(0, controller.searchBookCopy(predicate1,comparator).size());
    }
}