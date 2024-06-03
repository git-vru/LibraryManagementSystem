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
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    int bookListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01");
    Book bladeRunner = new Book("Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", LocalDate.of(1968,5,1), "DIC01");
    BookCopy bookCopy = new BookCopy(book);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));

    @BeforeEach
    void setUp() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getCustomers().add(customer);
        bookListSize = controller.getBookDatabase().size();
    }

    @Test
    void deleteBookSuccessfully() throws BorrowingNotNullException {
        controller.deleteBook("isbn02");
        assertEquals(bookListSize - 1, controller.getBookDatabase().size());
        assertFalse(controller.getBookDatabase().containsKey(book));
    }

    @Test
    void deleteBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.deleteBook("wrong isbn");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));
    }

    @Test
    void deleteBookWhenBorrowingNotNull() {
        bookCopy.setIsBorrowed(true);

        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteBook("isbn02");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));
    }

    @Test
    void addBookSuccessfully() {
        Book book = controller.addBook("Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", "1968-05-01", "DIC01");
        assertEquals(bookListSize + 1, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));

        assertEquals("0-345-40447-5", book.getIsbn());
        assertEquals("Do Androids Dream of Electric Sheep?", book.getTitle());
        assertEquals("Philip K. Dick", book.getAuthor());
        assertEquals("0-345-40447-5", book.getIsbn());
        assertEquals(LocalDate.of(1968, 5, 1), book.getPublicationDate());
        assertEquals("DIC01", book.getClassificationNumber());
    }

    @Test
    void addBookUnsuccessful() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("", "author","isbn", "2024-05-23", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "","isbn", "2024-05-23", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","", "2024-05-23", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","isbn", "24-24-24", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","isbn", "2024-05-23", "");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertTrue(controller.getBookDatabase().containsKey(book));
    }

    @Test
    void modifyBookSuccessfully() {
        assertTrue(controller.modifyBook(book,"abc","def", "22/5/2024","12"));

        assertEquals("abc", book.getTitle());
        assertEquals("def",book.getAuthor());
        assertEquals("ghi",book.getIsbn());
        assertEquals(LocalDate.of(2024,5,22),book.getPublicationDate());
        assertEquals("12",book.getClassificationNumber());
    }

    @Test
    void modifyBookUnsuccessful() {
        Book modifiedBook = book;
        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook, "", "b", "c", "d");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook,"a", "", "c", "d");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook,"a", "b", "", "d");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook,"a", "b", "c", "");
        });

        assertEquals(bookListSize, controller.getBookDatabase().size());
        assertEquals(book.getTitle(), modifiedBook.getTitle());
        assertEquals(book.getAuthor(),modifiedBook.getAuthor());
        assertEquals(book.getPublicationDate(), modifiedBook.getPublicationDate());
        assertEquals(book.getClassificationNumber(),modifiedBook.getClassificationNumber());
    }

    @Test
    void searchBookSuccessful() {
        Predicate<Book> predicate= book->book.getAuthor().equals("Candide");
        Comparator<Book> comparator=Comparator.comparing(Book::getAuthor);
        assertEquals(1,controller.searchBook(predicate,comparator).size());
        assertEquals("Candide",controller.searchBook(predicate,comparator).get(0).getAuthor());
    }

    @Test
    void searchBookWithWrongArgument() {
        Predicate<Book> predicate= book->book.getAuthor().equals("Candide");
        Comparator<Book> comparator=Comparator.comparing(Book::getAuthor);
        assertThrows(NoSuchElementException.class, ()->{
            controller.searchBook(predicate,comparator);
        });
    }

    @Test
    void searchBookViaIsbn() {
        assertEquals(book,controller.searchBookViaIsbn("isbn02").getIsbn());
    }

    @Test
    void searchBookViaIsbnWithWrongArgument() {
        assertEquals(null,controller.searchBookViaIsbn("abc").getIsbn());
    }
}
