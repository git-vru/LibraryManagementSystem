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

class ControllerTest {
    int bookListSize;
    int customerListSize;
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
        customerListSize = controller.getCustomers().size();
        bookListSize = controller.getBooks().size();
        bookCopyListSize = controller.getBookCopys(book).size();
    }

    @Test
    void deleteBookSuccessfully() throws BorrowingNotNullException {
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
    void deleteBookWhenBorrowingNotNull() {
        bookCopy.setBorrower(customer);

        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteBook("isbn02");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));
    }

    @Test
    void deleteCustomerSuccessfully() throws BorrowingNotNullException {
        controller.deleteCustomer(customer.getId());
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
    void deleteCustomerBorrowingNotNull() {
        customer.getBorrowedList().add(bookCopy);

        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteCustomer(customer.getId());
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertTrue(controller.getCustomers().contains(customer));
    }

    @Test
    void deleteBookCopySuccessfully() throws BorrowingNotNullException {
        controller.deleteBookCopy("VOL01_01");
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
            controller.deleteBookCopy("VOL01_01");
        });
        assertEquals(bookCopyListSize, controller.getBookCopys(book).size());
        assertTrue(controller.getBookCopys(book).contains(bookCopy));
    }

    @Test
    void addBookSuccessfully() {
        assertTrue(controller.addBook("Do Androids Dream of Electric Sheep?", "Philip K. Dick", "0-345-40447-5", "1968-05-01", "DIC01"));
        assertEquals(bookListSize + 1, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));

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
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "","isbn", "2024-05-23", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","", "2024-05-23", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","isbn", "24-24-24", "classificationNumber");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addBook("title", "author","isbn", "2024-05-23", "");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertTrue(controller.getBooks().containsKey(book));
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

        assertEquals(bookListSize, controller.getBooks().size());
        assertEquals(book.getTitle(), modifiedBook.getTitle());
        assertEquals(book.getAuthor(),modifiedBook.getAuthor());
        assertEquals(book.getPublicationDate(), modifiedBook.getPublicationDate());
        assertEquals(book.getClassificationNumber(),modifiedBook.getClassificationNumber());
    }

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
    void addCustomerUnsuccessful() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addCustomer("", "Smith", "10/10/2010");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addCustomer("Will", "", "10/10/2010");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addCustomer("Will", "Smith", "10/10/10");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

    }

    @Test
    void modifyCustomerSuccessfully() {
        assertTrue(controller.modifyCustomer(customer,"Unga", "Bunga", "01/01/2001"));

        assertEquals("Unga",customer.getFirstName());
        assertEquals("Bunga",customer.getLastName());
        assertEquals(LocalDate.of(2001,1,1),customer.getDob());
    }

    @Test
    void modifyCustomerUnsuccessful() {
        Customer modifiedCustomer = customer;
        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyCustomer(modifiedCustomer,"", "Smith", "10/10/2010");
        });

        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyCustomer(modifiedCustomer, "Will", "", "10/10/2010");
        });

        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyCustomer(modifiedCustomer, "Will", "Smith", "10/10/10");
        });

        assertEquals(customerListSize, controller.getCustomers().size());
        assertEquals(customer.getFirstName(), modifiedCustomer.getFirstName());
        assertEquals(customer.getLastName(), modifiedCustomer.getLastName());
        assertEquals(customer.getDob(), modifiedCustomer.getDob());
    }

    @Test
    void addbookCopySuccessfully() {
        assertTrue(controller.addBookCopy("123456"));
        assertEquals(bookCopyListSize + 1, controller.getBookCopys(book).size());
        assertTrue(controller.getBookCopys(book).contains(bookCopy));
    }

    @Test
    void addbookCopyUnsuccessful() {
        assertFalse(controller.addBookCopy("wrong"));
        assertEquals(bookCopyListSize , controller.getBookCopys(book).size());
    }



}