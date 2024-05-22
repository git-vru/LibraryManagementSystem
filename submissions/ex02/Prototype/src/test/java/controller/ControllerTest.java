package controller;

import exceptions.BorrowingNotNullException;
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
        physicalBookListSize = controller.getPhysicalBooks(book).size();
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
        physicalBook.setBorrower(customer);

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
        customer.getBorrowedList().add(physicalBook);

        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deleteCustomer(customer.getId());
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertTrue(controller.getCustomers().contains(customer));
    }

    @Test
    void deletePhysicalBookSuccessfully() throws BorrowingNotNullException {
        controller.deletePhysicalBook("VOL01_01");
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
    void deletePhysicalBookWhenBorrowingNotNull() {
        physicalBook.setBorrower(customer);
        assertThrows(BorrowingNotNullException.class, () -> {
            controller.deletePhysicalBook("VOL01_01");
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
        assertTrue(controller.modifyBook("1234","abc","def", "22/5/2024","12"));
        assertEquals("abc", book.getTitle());
        assertEquals("def",book.getAuthor());
        assertEquals("ghi",book.getIsbn());
        assertEquals(LocalDate.of(2024,5,22),book.getPublicationDate());
        assertEquals("12",book.getClassificationNumber());
    }

    @Test
    void modifyBookUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyBook("1234", "", "b", "c", "d");
        });
        assertEquals("", book.getTitle());

        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyBook("1234","a", "", "c", "d");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));

        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyBook("1234","a", "b", "", "d");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));
        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyBook("1234","a", "b", "c", "");
        });
        assertEquals(bookListSize, controller.getBooks().size());
        assertFalse(controller.getBooks().containsKey(book));

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
        assertThrows(NoSuchElementException.class, () -> {
            controller.addCustomer("", "Smith", "10/10/2010");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(NoSuchElementException.class, () -> {
            controller.addCustomer("Will", "", "10/10/2010");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(NoSuchElementException.class, () -> {
            controller.addCustomer("Will", "Smith", "10/10/10");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

    }

    @Test
    void modifyCustomerSuccessfully() {
        assertTrue(controller.modifyCustomer("123","Unga", "Bunga", "01/01/2001","22/05/2024"));
        assertEquals("Unga",customer.getFirstName());
        assertEquals("Bunga",customer.getLastName());
        assertEquals(LocalDate.of(2001,1,1),customer.getDob());
        assertEquals(LocalDate.of(2024,5,22),customer.getSubscriptionDate());
        ;
    }

    @Test
    void modifyCustomerUnsuccessful() {
        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyBook("","ee", "Smith", "10/10/2010", "656");
        });
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(NoSuchElementException.class, () -> {
            controller.addCustomer("Will", "", "10/10/2010");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

        assertThrows(NoSuchElementException.class, () -> {
            controller.addCustomer("Will", "Smith", "10/10/10");
        });
        assertEquals(customerListSize, controller.getCustomers().size());
        assertFalse(controller.getCustomers().contains(customer));

    }

    @Test
    void addPhysicalBookSuccessfully() {
        assertTrue(controller.addPhysicalBook("123456"));
        assertEquals(physicalBookListSize + 1, controller.getPhysicalBooks(book).size());
        assertTrue(controller.getPhysicalBooks(book).contains(physicalBook));
    }

    @Test
    void addPhysicalBookUnsuccessful() {
        assertFalse(controller.addPhysicalBook("wrong"));
        assertEquals(physicalBookListSize , controller.getPhysicalBooks(book).size());
    }



}