package controller;

import model.Book;
import model.Customer;
import model.PhysicalBook;
import view.MainMenu;
import view.View;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Controller {
    private View menu;
    private Scanner sc;
    private final List<Customer> customers = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    public Controller() {
        sc = new Scanner(System.in);
        this.menu = new MainMenu(this);

        this.customers.add(new Customer("Max", "Musterman", LocalDate.of(2024, 11, 1)));
        this.books.add(new Book("Les Fleurs du Mal", "Charles Baudelaire", "isbn", LocalDate.of(1857, 6, 21), "BAU01", 5));
        //this.menu.show();
    }

    public <T> T search(List<T> list, String query)  {
        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    public Book searchBook(String isbn) {
        Optional<Book> optionalBook = this.books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
        return optionalBook.map(book -> this.books.get(this.books.indexOf(book))).orElse(null);
    }

    public boolean borrowBook(String customerId, String bookId) {
        Customer customer = searchCustomer(customerId);
        PhysicalBook physicalBook = getBooks().get(0).getBookList().get(0);

        if (customer == null || physicalBook == null) return false;

        customer.getBorrowedList().add(physicalBook);
        physicalBook.setBorrower(customer);

        return true;
    }

    public boolean returnBook(String customerId, String bookId) {
        Customer customer = searchCustomer(customerId);
        PhysicalBook physicalBook = getBooks().get(0).getBookList().get(0);

        if (physicalBook.getFee() == 0) {
            customer.getBorrowedList().remove(physicalBook);
            // physicalBook.setReturnedDate(some date);
            physicalBook.setBorrower(null);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteBook(String isbn) {
        Optional<Book> optionalBook = this.books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
        return optionalBook.map(this.books::remove).orElse(false);
    }

    public Customer searchCustomer(String id) {
        Optional<Customer> optionalCustomer = this.customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
        return optionalCustomer.map(customer -> this.customers.get(this.customers.indexOf(customer))).orElse(null);
    }

    public boolean createCustomer(String firstName, String lastName, String dob) {
        LocalDate dt = LocalDate.parse(dob);
        Customer customer = new Customer(firstName, lastName, dt);

        return this.customers.add(customer);
    }

    public boolean deleteCustomer(String id) {
        Optional<Customer> optionalCustomer = this.customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();

       if (!optionalCustomer.get().getBorrowedList().isEmpty()) {
           return false;
       }

       return this.customers.remove(optionalCustomer.get());
    }

    public void setMenu(View menu) {
        this.menu = menu;
        this.menu.show();
    }

    public Scanner getScanner() {
        return this.sc;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public View getMenu() {
        return menu;
    }

    public Scanner getSc() {
        return sc;
    }

    public void setSc(Scanner sc) {
        this.sc = sc;
    }

    public List<Book> getBooks() {
        return books;
    }
}
