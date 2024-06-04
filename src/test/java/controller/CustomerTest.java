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

class CustomerTest {

    int customerListSize;

    Controller controller = new Controller();
    Book book = new Book("Candide", "Voltaire","isbn02", LocalDate.of(1759, 1, 1), "VOL01");
    BookCopy bookCopy = new BookCopy(book);
    Customer customer = new Customer("Vrushabh", "Jain", LocalDate.of(2004, 10, 30));

    @BeforeEach
    void setUp() {
        controller.getBookDatabase().put(book, new ArrayList<>());
        controller.getBookCopies(book).add(bookCopy);
        controller.getCustomers().add(customer);
        customerListSize = controller.getCustomers().size();
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
    void addCustomerSuccessfully() {
        Customer newCustomer = controller.addCustomer("Samuel","Kemmler","2005-08-28");
        assertEquals(customerListSize+1, controller.getCustomers().size());

        assertEquals("Samuel", newCustomer.getFirstName());
        assertEquals("Kemmler", newCustomer.getLastName());
        assertEquals(LocalDate.of(2005, 8, 28), newCustomer.getDateOfBirth());
        assertEquals(LocalDate.now(), newCustomer.getSubscriptionDate());
        assertEquals(0, newCustomer.getBorrowedList().size());
    }

    @Test
    void addCustomerUnsuccessful() {
        assertThrows(IllegalArgumentException.class, () -> {
            controller.addCustomer("", "Smith", "2001-01-01");
        });
        assertEquals(customerListSize, controller.getCustomers().size());

        assertThrows(IllegalArgumentException.class, () -> {
            controller.addCustomer("Will", "", "2001-01-01");
        });
        assertEquals(customerListSize, controller.getCustomers().size());

        assertThrows(DateTimeParseException.class, () -> {
            controller.addCustomer("Will", "Smith", "10/10/10");
        });
        assertEquals(customerListSize, controller.getCustomers().size());

    }

    @Test
    void modifyCustomerSuccessfully() {
        assertTrue(controller.modifyCustomer(customer,"Unga", "Bunga", "2001-01-01"));

        assertEquals("Unga",customer.getFirstName());
        assertEquals("Bunga",customer.getLastName());
        assertEquals(LocalDate.of(2001,1,1),customer.getDateOfBirth());
    }

    @Test
    void modifyCustomerUnsuccessful() {
        Customer modifiedCustomer = customer;
        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyCustomer(modifiedCustomer,"", "Smith", "2001-01-01");
        });

        assertThrows(NoSuchElementException.class, () -> {
            controller.modifyCustomer(modifiedCustomer, "Will", "", "2001-01-01");
        });

        assertThrows(DateTimeParseException.class, () -> {
            controller.modifyCustomer(modifiedCustomer, "Will", "Smith", "10/10/10");
        });

        assertEquals(customerListSize, controller.getCustomers().size());
        assertEquals(customer.getFirstName(), modifiedCustomer.getFirstName());
        assertEquals(customer.getLastName(), modifiedCustomer.getLastName());
        assertEquals(customer.getDateOfBirth(), modifiedCustomer.getDateOfBirth());
    }

    @Test
    void searchCustomerSuccessful() {
        controller.getCustomers().add(new Customer("Samu", "Kem", LocalDate.of(2024, 06, 03)));
        Predicate<Customer> predicate = customer -> customer.getFirstName().equals("Vrushabh");
        Comparator<Customer> comparator = Comparator.comparing(Customer::getFirstName);
        assertEquals(1, controller.searchCustomer(predicate, comparator).size());
        assertEquals("Vrushabh", controller.searchCustomer(predicate, comparator).get(0).getFirstName());

        //Sorting test
        List<Customer> results = controller.searchCustomer(customer -> true, Comparator.comparing(Customer::getLastName));
        assertEquals(controller.getCustomers().size(), results.size());
        assertEquals("Jain", results.get(0).getLastName());
        assertEquals("Kem", results.get(1).getLastName());
    }

    @Test
    void searchCustomerWithWrongArgument() {
        Predicate<Customer> predicate = customer -> customer.getFirstName().equals("");
        Comparator<Customer> comparator = Comparator.comparing(Customer::getFirstName);

        assertEquals(0, controller.searchCustomer(predicate, comparator).size());
    }
}
