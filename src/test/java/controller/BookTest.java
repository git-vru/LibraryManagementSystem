package controller;

import exceptions.BorrowingNotNullException;
import model.Book;
import model.BookCopy;
import model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    int bookListSize;
    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01","Lorem Ipsum");
    Book bladeRunner = new Book("Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", LocalDate.of(1968,5,1), "DIC01", "Lorem Ipsum");
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
        Book book = controller.addBook("0-345-40447-5", "Do Androids Dream of Electric Sheep?", "Philip K. Dick", "Lorem Ipsum", "1968-05-01", "DIC01");
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
            controller.addBook("", "author","isbn", "Lorem Ipsum", "2024-05-23", "classificationNumber");
        });


        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "","isbn", "Lorem Ipsum", "2024-05-23", "classificationNumber");
        });


        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","", "Lorem Ipsum", "2024-05-23", "classificationNumber");
        });


        assertNull(controller.addBook("title", "author","isbn", "Lorem Ipsum", "24-24-24", "classificationNumber"));


        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","isbn", "Lorem Ipsum", "2024-05-23", "");
        });
        assertEquals(bookListSize, controller.getBookDatabase().size());
    }

    @Test
    void modifyBookSuccessfully() {
        assertTrue(controller.modifyBook(book,"abc","def", "2024-01-04","12"));

        assertEquals("abc", book.getTitle());
        assertEquals("def",book.getAuthor());
        assertEquals(LocalDate.of(2024,1,4),book.getPublicationDate());
        assertEquals("12",book.getClassificationNumber());
    }

    @Test
    void modifyBookUnsuccessful() {
        Book modifiedBook = book;
        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook, "", "b", "2001-01-01", "d");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            controller.modifyBook(modifiedBook,"a", "", "2001-01-01", "d");
        });

        assertThrows(DateTimeParseException.class, () -> {
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
        Comparator<Book> comparator = Comparator.comparing(Book::getTitle);
        Book book1 = new Book("Book One", "Author A", "1234567890", LocalDate.of(2000, 1, 1), "001", "Lorem Ipsum");
        Book book2 = new Book("Book Two", "Author B", "1234567891", LocalDate.of(2001, 2, 2), "002", "Lorem Ipsum");
        Book book3 = new Book("Book Three", "Author A", "1234567892", LocalDate.of(2002, 3, 3), "003", "Lorem Ipsum");
        Book book4 = new Book("Book Four", "Author C", "1234567893", LocalDate.of(2003, 4, 4), "004", "Lorem Ipsum");
        Book book5 = new Book("Book Five", "Author B", "1234567894", LocalDate.of(2004, 5, 5), "005", "Lorem Ipsum");
        controller.getBookDatabase().put(book1, new ArrayList<>());
        controller.getBookDatabase().put(book2, new ArrayList<>());
        controller.getBookDatabase().put(book3, new ArrayList<>());
        controller.getBookDatabase().put(book4, new ArrayList<>());
        controller.getBookDatabase().put(book5, new ArrayList<>());

        //Search by Isbn
        List<Book> results = controller.searchBook(book -> book.getIsbn().equals("isbn02"),comparator);
        assertEquals(1,results.size());
        assertEquals(book,results.get(0));

        //Search by Author
        results = controller.searchBook(book -> book.getAuthor().equals("Voltaire"),comparator);
        assertEquals(1,results.size());
        assertEquals(book,results.get(0));

        //Search by Title
        results = controller.searchBook(book -> book.getTitle().contains("Book"),comparator);
        assertEquals(5,results.size());

        //Sorting Test
        results = controller.searchBook(book -> true, Comparator.comparing(Book::getTitle));
        assertEquals(controller.getBookDatabase().size(), results.size());
        assertEquals("Book Five", results.get(0).getTitle());
        assertEquals("Candide", results.get(results.size() - 1).getTitle());

        results = controller.searchBook(book -> true, Comparator.comparing(Book::getAuthor));
        assertEquals(controller.getBookDatabase().size(), results.size());
        assertEquals("Author C", results.get(4).getAuthor());
        assertEquals("Voltaire", results.get(results.size() - 1).getAuthor());
    }

    @Test
    void searchBookWithWrongArgument() {
        Predicate<Book> predicate= book->book.getAuthor().equals("Candide");
        Comparator<Book> comparator=Comparator.comparing(Book::getTitle);
        assertEquals(0, controller.searchBook(predicate, comparator).size());
    }

    @Test
    void searchBookViaIsbn() {
        assertEquals(book,controller.searchBookViaIsbn("isbn02"));
    }

    @Test
    void searchBookViaIsbnWithWrongArgument() {
        assertNull(controller.searchBookViaIsbn("abc"));
    }
}
